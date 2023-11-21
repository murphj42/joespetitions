package com.example.joespetitions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JoespetitionController {
    // Controller methods for creating, viewing, searching, and signing petitions
    // Example: In-memory list to store petitions
    private List<Petition> petitions;

    // Constructor to initialize data (You might want to use a service or a database in a real application)
    public JoespetitionController() {
        // Initialize petitions with some sample data
        // For simplicity, you can replace this with your preferred data storage mechanism
        petitions = List.of(
                new Petition("Petition 1", "Description 1"),
                new Petition("Petition 2", "Description 2")
                // Add more as needed
        );
    }

    // Endpoint to show the form for creating a petition
    @GetMapping("/create-petition")
    public String showCreateForm() {
        return "create_petition"; // Corresponds to create_petition.html in templates folder
    }

    // Endpoint to handle form submission for creating a petition
    @PostMapping("/create-petition")
    public String createPetition(@ModelAttribute Petition petition) {
        // Logic to save the petition (e.g., add it to the list)
        petitions.add(petition);
        return "redirect:/view-petitions";
    }

    // Endpoint to view all petitions
    @GetMapping("/view-petitions")
    public String viewPetitions(Model model) {
        model.addAttribute("petitions", petitions);
        return "view_petitions"; // Corresponds to view_petitions.html in templates folder
    }

    // Endpoint to search for petitions
    @GetMapping("/search-petitions")
    public String searchPetitions(@RequestParam String search, Model model) {
        // Logic to filter petitions based on the search query
        List<Petition> searchResults = /* Your search logic here */;
        model.addAttribute("searchResults", searchResults);
        return "search_petitions"; // Corresponds to search_petitions.html in templates folder
    }

    // Endpoint to view a specific petition
    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        // Logic to retrieve the petition by ID
        Petition petition = /* Your logic to get the petition by ID */;
        model.addAttribute("petition", petition);
        return "view_petition"; // Corresponds to view_petition.html in templates folder
    }

    // Endpoint to handle form submission for signing a petition
    @PostMapping("/sign-petition")
    public String signPetition(@RequestParam String name, @RequestParam String email, @RequestParam int petitionId) {
        // Logic to sign the petition (e.g., update the petition with signer information)
        Petition petition = /* Your logic to get the petition by ID */;
        petition.addSigner(new Signer(name, email));
        return "redirect:/petition/" + petitionId;
    }

    // Sample Petition class (You can define this as a separate file if needed)
    private static class Petition {
        private String title;
        private String description;
        private List<Signer> signers;

        public Petition(String title, String description) {
            this.title = title;
            this.description = description;
            this.signers = List.of(); // Initialize with an empty list
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public List<Signer> getSigners() {
            return signers;
        }

        public void addSigner(Signer signer) {
            signers.add(signer);
        }
    }

    // Sample Signer class (You can define this as a separate file if needed)
    private static class Signer {
        private String name;
        private String email;

        public Signer(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}

