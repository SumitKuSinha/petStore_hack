package com.hackathon.petstore_cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.hackathon.petstore_cms.entity.Pet;
import com.hackathon.petstore_cms.entity.Product;
import com.hackathon.petstore_cms.repository.PetRepository;
import com.hackathon.petstore_cms.repository.ProductRepository;

@Controller
public class StorefrontController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Homepage
    @GetMapping("/")
    public String showHomepage(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

  
    @PostMapping("/pay")
    public String processPayment() {
        //  Redirect to the new, consistent URL
        return "redirect:/thankyou"; 
    }
    
   
    @GetMapping("/thankyou")
    public String showThankYouPage() {
        //  Returns the template named "thankyou.html"
        return "thankyou"; 
    }

    // 4. Category Page for Pets
    @GetMapping("/pets/species/{species}")
    public String showPetsBySpecies(@PathVariable("species") String species, Model model) {
        model.addAttribute("pets", petRepository.findBySpecies(species));
        model.addAttribute("pageTitle", species + "s");
        return "category-view";
    }

    // 5. Category Page for Products
    @GetMapping("/products/type/{type}")
    public String showProductsByType(@PathVariable("type") String type, Model model) {
        model.addAttribute("products", productRepository.findByType(type));
        model.addAttribute("pageTitle", type + " Products");
        return "category-view";
    }

    // 6. Search Bar
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Pet> petResults = petRepository.search(keyword);
        List<Product> productResults = productRepository.search(keyword);
        
        model.addAttribute("pets", petResults);
        model.addAttribute("products", productResults);
        model.addAttribute("pageTitle", "Search results for '" + keyword + "'");
        
        return "category-view";
    }
}