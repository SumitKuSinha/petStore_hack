package com.hackathon.petstore_cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackathon.petstore_cms.entity.Pet;
import com.hackathon.petstore_cms.repository.PetRepository;

@Controller
public class PetController {

    @Autowired
    private PetRepository petRepository;

    // 1. READ (List all pets)
    // We will map this to "/admin/pets" to keep it secure
    @GetMapping("/admin/pets")
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        // This will look for a file named "admin-pets.html"
        return "admin-pets"; 
    }

    // 2. CREATE (Show the "add new pet" form)
    @GetMapping("/admin/pets/new")
    public String showCreatePetForm(Model model) {
        Pet pet = new Pet();
        model.addAttribute("pet", pet);
        // This will look for a file named "pet-form.html"
        return "pet-form";
    }

    // 3. CREATE (Handle the form submission)
    @PostMapping("/admin/pets/save")
    public String savePet(@ModelAttribute("pet") Pet pet) {
        petRepository.save(pet);
        return "redirect:/admin/pets"; // Go back to the pet list
    }

    // 4. UPDATE (Show the "edit pet" form, pre-filled)
    @GetMapping("/admin/pets/edit/{id}")
    public String showEditPetForm(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findById(id).get();
        model.addAttribute("pet", pet);
        // Re-use the same form: "pet-form.html"
        return "pet-form";
    }

    // 5. DELETE
    @GetMapping("/admin/pets/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
        return "redirect:/admin/pets"; // Go back to the pet list
    }
}
