package com.hackathon.petstore_cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile; // New import

import com.hackathon.petstore_cms.entity.Product;
import com.hackathon.petstore_cms.repository.ProductRepository;

// New imports for file handling
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProductController {

    // Define the folder where images will be saved
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploaded_images";

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/admin/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("lowStockProducts", productRepository.findLowStockProducts());
        model.addAttribute("totalStock", productRepository.getTotalStockCount());
        model.addAttribute("totalValue", productRepository.getTotalInventoryValue());
        
        logger.info("Admin viewed product inventory dashboard");
        return "admin-products";
    }

    @GetMapping("/admin/products/new")
    public String showCreateProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-form";
    }

    // === THIS METHOD IS NOW UPDATED ===
    @PostMapping("/admin/products/save")
    public String saveProduct(@ModelAttribute("product") Product product, 
                              @RequestParam("productImage") MultipartFile file) throws IOException {
        
        // --- This is the new logic to handle the image file ---
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIRECTORY, fileName);
            
            // Create directories if they don't exist
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            // Set the web-accessible path in the database
            product.setImageUrl("/images/" + fileName); 
            
        } else if (product.getId() != null) {
            // This is an edit, but no new file was uploaded.
            // We must get the old image URL from the DB to prevent losing it.
            Product oldProduct = productRepository.findById(product.getId()).get();
            product.setImageUrl(oldProduct.getImageUrl());
        }
        // --- End of image logic ---
        
        productRepository.save(product);
        logger.info("Admin saved product with id: " + product.getId());
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        logger.info("Admin deleted product with id: " + id);
        return "redirect:/admin/products";
    }
}