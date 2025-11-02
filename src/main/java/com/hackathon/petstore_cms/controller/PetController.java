package com.hackathon.petstore_cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import com.hackathon.petstore_cms.entity.Pet;
import com.hackathon.petstore_cms.repository.PetRepository;

@Controller
public class PetController {


    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploaded_images"; 

    @Autowired
    private PetRepository petRepository;

    // 1. READ (List all pets)
    @GetMapping("/admin/pets")
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "admin-pets";  
    }

    // 2. CREATE (Show the "add new pet" form)
    @GetMapping("/admin/pets/new")
    public String showCreatePetForm(Model model) {
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        return "pet-form"; 
    }

    // 3. CREATE/UPDATE (Handle the form submission with file)
    @PostMapping("/admin/pets/save")
    public String savePet(@ModelAttribute("pet") Pet pet,
                          @RequestParam("petImage") MultipartFile file) throws IOException { // <-- Added @RequestParam
        
        // --- IMAGE UPLOAD LOGIC ---
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIRECTORY, fileName);
            
            // Ensure the external folder exists
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            
            // Save the file
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            // Set the web-accessible path (mapped via WebConfig to /uploaded_images)
            pet.setImageUrl("/images/" + fileName); 
            
        } else if (pet.getId() != null) {
            // Edit scenario: If no new file is uploaded, keep the old image URL
            Pet existingPet = petRepository.findById(pet.getId()).orElse(null);
            if (existingPet != null) {
                pet.setImageUrl(existingPet.getImageUrl());
            }
        }
        // --- END IMAGE LOGIC ---

        petRepository.save(pet);
        return "redirect:/admin/pets"; // Go back to the pet list
    }

    // 4. UPDATE (Show the "edit pet" form, pre-filled)
    @GetMapping("/admin/pets/edit/{id}")
    public String showEditPetForm(@PathVariable Long id, Model model) {
        // Use orElseThrow() for robust error handling if ID is bad
        Pet pet = petRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Pet with ID " + id + " not found.")
        );
        model.addAttribute("pet", pet);
        return "pet-form";
    }

    // 5. DELETE
    @GetMapping("/admin/pets/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
        return "redirect:/admin/pets"; // Go back to the pet list
    }
}