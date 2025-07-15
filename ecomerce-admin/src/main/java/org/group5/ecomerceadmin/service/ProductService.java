package org.group5.ecomerceadmin.service;

import org.group5.ecomerceadmin.dto.ProductCreateDTO;
import org.group5.ecomerceadmin.dto.ProductDTO;
import org.group5.ecomerceadmin.dto.ProductDetailDTO;
import org.group5.ecomerceadmin.entity.Brand;
import org.group5.ecomerceadmin.entity.Category;
import org.group5.ecomerceadmin.entity.Product;
import org.group5.ecomerceadmin.payload.request.ProductRequest;
import org.group5.ecomerceadmin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileService fileService;

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(Product::isActive)
                .map(this::convertToDTO)
                .toList();
    }
    public ProductDetailDTO findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        if (!product.isActive()) {
            throw new RuntimeException("Product is inactive");
        }
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl("http://localhost:8080/file/"+product.getImage());
        dto.setBrand(product.getBrand().getName());
        dto.setCategory(product.getCategory().getName());
        return dto;
    }
    public ProductCreateDTO findProductCreateDTOById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        if (!product.isActive()) {
            throw new RuntimeException("Product is inactive");
        }
        ProductCreateDTO dto = new ProductCreateDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setFile("http://localhost:8080/file/"+product.getImage());
        dto.setBrandId(product.getBrand().getId());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
    @Transactional
    public ProductDTO saveProduct( ProductRequest request) {
        Product product= new Product();

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            fileService.saveFile(request.getFile());
            product.setImage(request.getFile().getOriginalFilename());
        }
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());

        Brand brand = new Brand();
        brand.setId(request.getBrandId());
        product.setBrand(brand);

        Category category = new Category();
        category.setId(request.getCategoryId());
        product.setCategory(category);

        return convertToDTO(productRepository.save(product));
    }

    public void delete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
    }
    public List<ProductDTO> findByCategory(String categoryId) {
        List<Product> products = productRepository.findByCategory_IdAndIsActiveTrue(categoryId);
        return products.stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<ProductDTO> findByBrand(String brandId) {
        List<Product> products = productRepository.findByBrand_IdAndIsActiveTrue(brandId);
        return products.stream()
                .map(this::convertToDTO)
                .toList();
    }
    public List<ProductDTO> search(String keyword) {
        List<Product> products = productRepository.findAll().stream()
                .filter(product ->
                        product.isActive() &&
                        product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        return products.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setImageUrl("http://localhost:8080/file/"+product.getImage());
        dto.setBrand(product.getBrand().getName());
        dto.setCategory(product.getCategory().getName());
        return dto;
    }
}
