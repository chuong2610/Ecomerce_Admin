package org.group5.ecomerceadmin.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${file.path}")
    private String root;

    public void saveFile(MultipartFile file) {
        try {
            Path rootPath = Path.of(root);
            if(!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }
            Files.copy(file.getInputStream(), rootPath.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            System.out.println("error at saveFileService "+e.getMessage());
        }
    }


    public Resource loadFile(String filename) {
        try {
            Path path = Paths.get(root).resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()){
                return resource;
            }
        }catch (Exception e){
            System.out.println("error at loadFileService "+e.getMessage());
        }
        return null;
    }
}
