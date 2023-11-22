package com.example.joespetitions.controllers;

import com.example.joespetitions.model.Petition;
import com.example.joespetitions.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PetitionController {

    private List<Petition> petitions = new ArrayList<>(); // Your data structure to store petitions

    @GetMapping("/create_petition")
    public String showCreatePetitionForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create_petition";
    }

    @PostMapping("/create_petition")
    public String createPetition(@ModelAttribute Petition petition) {
        petition.setId(UUID.randomUUID().toString()); // Generate a unique ID
        petitions.add(petition);
        return "redirect:/view_all";
    }

    @GetMapping("/view_all")
    public String showAllPetitions(Model model) {
        model.addAttribute("petitions", petitions);
        return "view_all";
    }

    @GetMapping("/view_petition")
    public String viewPetition(@RequestParam("id") String id, Model model) {
        Petition petition = findPetitionById(id);
        if (petition != null) {
            model.addAttribute("petition", petition);
            model.addAttribute("signee", new User()); // For the signee form
            return "view_petition";
        } else {
            // Handle case where petition is not found
            return "redirect:/view_all";
        }
    }

    @PostMapping("sign_petition")
    public String signPetition(@RequestParam("id") String id, @ModelAttribute User signee, Model model) {
        Petition petition = findPetitionById(id);

        if (petition != null) {
            petition.getSignees().add(signee);
        }
        return "redirect:/view_petition?id=" + id;
    }

    @GetMapping("/search_petitions")
    public String searchPetitions(Model model) {

        return "search_petitions";
    }

    @PostMapping("/search_petitions")
    public String searchPetitions(@RequestParam String searchTerm, Model model) {
        List<Petition> searchResults = searchPetitionsByTerm(searchTerm);
        model.addAttribute("searchResults", searchResults);
        return "search_results";
    }


    // Helper method to search petitions by a term
    private List<Petition> searchPetitionsByTerm(String searchTerm) {
        // search by petition name or description
        return petitions.stream()
                .filter(petition ->
                        petition.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                petition.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
                )
                .collect(Collectors.toList());
    }


    // Helper method to find a petition by ID
    private Petition findPetitionById(String id) {
        return petitions.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Additional methods for searching, etc.
}