(function($){
    $(document).ready(function(){

        $(".side-nav .has-sub > a").click(function(event){
            event.preventDefault();
            $(this).toggleClass("menu-open");
            $(this).next(".submenu").slideToggle();
        });

        $(".toggle-bar").click(function(event){
            event.preventDefault();
            $(".wrapper").toggleClass("sidebar-off");
        });

        // Password Eye Toggle
        function eyeToggle(){
            $(".password-input .icon").click(function(){
                const passwordField = $('#password');
                const type = passwordField.attr('type');

                // Toggle between 'password' and 'text'
                if (type === 'password') {
                    passwordField.attr('type', 'text');
                } else {
                    passwordField.attr('type', 'password');
                }
                $(".password-input").toggleClass("password-show");
            });
        }
        eyeToggle();
    
        // Adding billing address
        function addBillingAddress(){
            $("#addBillingAddress form").submit(function(event){
                event.preventDefault();
                let billingFirstName    = $("#billing_first_name").val(),
                    billingLastName     = $("#billing_last_name").val(),
                    billingAddress1     = $("#billing_address1").val(),
                    billingCity         = $("#billing_city").val(),
                    billingState        = $("#billing_state").val(),
                    billingCountry      = $("#billing_country").val(),
                    billingEmail        = $("#billing_email").val(),
                    billingPhone        = $("#billing_phone").val();

                if( billingFirstName != undefined && billingFirstName != null && billingFirstName.length != 0 ){
                    $("#billing_first_name_preview").text(billingFirstName);
                }
                if( billingLastName != undefined && billingLastName != null && billingLastName.length != 0 ){
                    $("#billing_last_name_preview").text(billingLastName);
                }
                if( billingAddress1 != undefined && billingAddress1 != null && billingAddress1.length != 0 ){
                    $("#billing_address1_preview").text(billingAddress1);
                }
                if( billingCity != undefined && billingCity != null && billingCity.length != 0 ){
                    $("#billing_city_preview").text(billingCity);
                }
                if( billingState != undefined && billingState != null && billingState.length != 0 ){
                    $("#billing_state_preview").text(billingState);
                }
                if( billingCountry != undefined && billingCountry != null && billingCountry.length != 0 ){
                    $("#billing_country_preview").text(billingCountry);
                }
                if( billingEmail != undefined && billingEmail != null && billingEmail.length != 0 ){
                    $("#billing_email_preview").text(billingEmail);
                }
                if( billingPhone != undefined && billingPhone != null && billingPhone.length != 0 ){
                    $("#billing_phone_preview").text(billingPhone);
                }
                $("#addBillingAddress").modal("hide");
            });
        }
        function addShippingAddress(){
            $("#addShippingAddress form").submit(function(event){
                event.preventDefault();
                let shippingFirstName    = $("#shipping_first_name").val(),
                    shippingLastName     = $("#shipping_last_name").val(),
                    shippingAddress1     = $("#shipping_address1").val(),
                    shippingCity         = $("#shipping_city").val(),
                    shippingState        = $("#shipping_state").val(),
                    shippingCountry      = $("#shipping_country").val(),
                    shippingEmail        = $("#shipping_email").val(),
                    shippingPhone        = $("#shipping_phone").val();

                if( shippingFirstName != undefined && shippingFirstName != null && shippingFirstName.length != 0 ){
                    $("#shipping_first_name_preview").text(shippingFirstName);
                }
                if( shippingLastName != undefined && shippingLastName != null && shippingLastName.length != 0 ){
                    $("#shipping_last_name_preview").text(shippingLastName);
                }
                if( shippingAddress1 != undefined && shippingAddress1 != null && shippingAddress1.length != 0 ){
                    $("#shipping_address1_preview").text(shippingAddress1);
                }
                if( shippingCity != undefined && shippingCity != null && shippingCity.length != 0 ){
                    $("#shipping_city_preview").text(shippingCity);
                }
                if( shippingState != undefined && shippingState != null && shippingState.length != 0 ){
                    $("#shipping_state_preview").text(shippingState);
                }
                if( shippingCountry != undefined && shippingCountry != null && shippingCountry.length != 0 ){
                    $("#shipping_country_preview").text(shippingCountry);
                }
                if( shippingEmail != undefined && shippingEmail != null && shippingEmail.length != 0 ){
                    $("#shipping_email_preview").text(shippingEmail);
                }
                if( shippingPhone != undefined && shippingPhone != null && shippingPhone.length != 0 ){
                    $("#shipping_phone_preview").text(shippingPhone);
                }
                $("#addShippingAddress").modal("hide");
            });
        }
        function addProduct(){
            $("#addProduct form").submit(function(event){
                event.preventDefault();
                let productName         = $("#product_name").val(),
                    productQnty         = $("#product_qnty").val(),
                    productPrice        = Math.round(Math.random() * 198),
                    productTotal        = productPrice * productQnty,
                    productRow          = '<tr><td><h4 class="fs-6 fw-bold text-secondary">' + productName +'</h4></td><td><input type="number" placeholder="Qnty" class="form-control w-85" value="' + productQnty + '"></td><td><strong>$' + productPrice + '.00</strong></td><td><strong>$' + productTotal + '.00</strong></td></tr>';

                if( productName != undefined && productName != null && productName.length != 0 && productQnty != undefined && productQnty != null && productQnty.length != 0 ){
                    $(".product-table").append(productRow);
                }
                $("#addProduct").modal("hide");
            });
        }
        addBillingAddress();
        addShippingAddress();
        addProduct();
    });
    
}(jQuery))