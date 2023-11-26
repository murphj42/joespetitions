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

    public List<Petition> petitions = new ArrayList<>(); // Your data structure to store petitions

    // Control navigation to /create_petition page
    @GetMapping("/create_petition")
    public String showCreatePetitionForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create_petition";
    }

    /* logic to create unique identifier and add new petition to existing list */
    @PostMapping("/create_petition")
    public String createPetition(@ModelAttribute Petition petition) {
        petition.setId(UUID.randomUUID().toString()); // Generate a unique ID
        petitions.add(petition);
        return "redirect:/view_all";
    }

    /* Control navigation to 'view_all' page */
    @GetMapping("/view_all")
    public String showAllPetitions(Model model) {
        model.addAttribute("petitions", petitions);
        return "view_all";
    }

    /* control navigation to 'view_petition' page - find all petitions and add to request */
    @GetMapping("/view_petition")
    public String viewPetition(@RequestParam("id") String id, Model model) {
        Petition petition = findPetitionById(id);
        if (petition != null) {
            model.addAttribute("petition", petition);
            model.addAttribute("signee", new User()); // For the signee form
            return "view_petition";
        } else {
            // handle case where petition is not found
            return "redirect:/view_all";
        }
    }

    /* logic to allow a user to sign a petition and add them to singee list */
    @PostMapping("sign_petition")
    public String signPetition(@RequestParam("id") String id, @ModelAttribute User signee, Model model) {
        Petition petition = findPetitionById(id);

        if (petition != null) {
            petition.getSignees().add(signee);
        }
        return "redirect:/view_petition?id=" + id;
    }

    /* control navigation to 'view_petition' page */
    @GetMapping("/search_petitions")
    public String searchPetitions(Model model) {

        return "search_petitions";
    }

    /* logic to allow user to enter a search term(s) and find a petition in the list */
    @PostMapping("/search_petitions")
    public String searchPetitions(@RequestParam String searchTerm, Model model) {
        List<Petition> searchResults = searchPetitionsByTerm(searchTerm);
        model.addAttribute("searchResults", searchResults);
        return "search_results";
    }


    // helper method to search petitions by a term
    private List<Petition> searchPetitionsByTerm(String searchTerm) {
        // search by petition name or description
        return petitions.stream()
                .filter(petition ->
                        petition.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                petition.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
                )
                .collect(Collectors.toList());
    }


    // helper method to find a petition by ID
    private Petition findPetitionById(String id) {
        return petitions.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /* implementation of ApplicationRunner
    *       Creates sample data for application and executes when it is first run
    *  */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Petition> samplePetitions = generateSamplePetitions();
        this.petitions.addAll(samplePetitions);
    }

    public List<Petition> generateSamplePetitions() {
        List<Petition> samplePetitions = new ArrayList<>();

        // Create Petition object and add to ArrayList
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

        // Create User object and ass them as a singee for petitions we have created
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
