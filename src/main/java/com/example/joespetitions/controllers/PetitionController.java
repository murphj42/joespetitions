package com.example.joespetitions.controllers;

import com.example.joespetitions.model.Petition;
import com.example.joespetitions.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PetitionController implements ApplicationRunner {

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Petition> samplePetitions = generateSamplePetitions();
        this.petitions.addAll(samplePetitions);
    }

    private List<Petition> generateSamplePetitions() {
        List<Petition> samplePetitions = new ArrayList<>();
        Petition p1 = new Petition();
        p1.setName("Save the Bees");
        p1.setDescription("Protect the bee population for a sustainable environment.");
        p1.setId(UUID.randomUUID().toString());
        samplePetitions.add(p1);

        Petition p2 = new Petition();
        p2.setName("Plant Trees, Save Earth");
        p2.setDescription("Join us in planting trees to combat climate change.");
        p2.setId(UUID.randomUUID().toString());
        samplePetitions.add(p2);

        User u1 = new User();
        u1.setName("Donald Trump");
        u1.setEmail("TheDonald@whitehouse.com");
        p1.addSignee(u1);

        User u2 = new User();
        u2.setName("Joe Biden");
        u2.setEmail("sleepyjoe@whitehouse.com");
        p2.addSignee(u2);

        User u3 = new User();
        u3.setName("Barack Obama");
        u3.setEmail("bobby@whitehouse.com");
        p2.addSignee(u3);

        User u4 = new User();
        u4.setName("Rishi Sunak");
        u4.setEmail("strongandstable@cabinet.co.uk");
        p1.addSignee(u4);

        User u5 = new User();
        u5.setName("Leo Varadkar");
        u5.setEmail("leotheleak@oireachtas.ie");
        p1.addSignee(u5);

        User u6 = new User();
        u6.setName("Dwayne Johnson");
        u6.setEmail("Rock4Prez@gmail.com");
        p1.addSignee(u6);

        return samplePetitions;
    }

}
