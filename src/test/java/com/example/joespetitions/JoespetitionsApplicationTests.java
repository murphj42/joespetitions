package com.example.joespetitions;

import com.example.joespetitions.controllers.PetitionController;
import com.example.joespetitions.model.Petition;
import com.example.joespetitions.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JoespetitionsApplicationTests {

	@Autowired
	public PetitionController petitionController;

	@Test
	public void testGenerateSamplePetitions() {
		// Ensure that the generateSamplePetitions method adds petitions to the list
		petitionController.generateSamplePetitions();
		assertTrue(petitionController.petitions.size() > 0);
	}

	@Test
	public void testViewAllPetitionsWithSampleData() {
		// Generate sample data
		petitionController.generateSamplePetitions();

		// Create a dummy Model object
		Model model = new ExtendedModelMap();

		// Assuming your viewAllPetitions method returns the "view_all" template name
		String templateName = petitionController.showAllPetitions(model);
		assertEquals("view_all", templateName);

		// Ensure that the model contains the "petitions" attribute
		assertTrue(model.containsAttribute("petitions"));

		// Ensure that the "petitions" attribute in the model is not null
		assertNotNull(model.getAttribute("petitions"));

	}

	@Test
	public void testCreatePetition() {
		Petition testPetition = new Petition();
		testPetition.setName("Test Petition");
		testPetition.setDescription("This is a test petition");
		testPetition.setId(UUID.randomUUID().toString());

		String result = petitionController.createPetition(testPetition);

		assertEquals("redirect:/view_all", result);
		assertFalse(petitionController.petitions.isEmpty());
		assertTrue(petitionController.petitions.stream().anyMatch(p -> p.getName().equals("Test Petition")));
	}

	@Test
	public void testViewPetition() {
		Petition testPetition = new Petition();
		testPetition.setName("Test Petition");
		testPetition.setDescription("This is a test petition");
		testPetition.setId(UUID.randomUUID().toString());
		petitionController.petitions.add(testPetition);

		// Create a dummy Model object
		Model model = new ExtendedModelMap();

		String result = petitionController.viewPetition(testPetition.getId(), model);

		assertEquals("view_petition", result);
	}

	@Test
	public void testSignPetition() {
		Petition testPetition = new Petition();
		testPetition.setName("Test Petition");
		testPetition.setDescription("This is a test petition");
		testPetition.setId(UUID.randomUUID().toString());
		petitionController.petitions.add(testPetition);

		User testUser = new User();
		testUser.setName("John Doe");
		testUser.setEmail("john.doe@example.com");

		String result = petitionController.signPetition(testPetition.getId(), testUser, null);

		assertEquals("redirect:/view_petition?id=" + testPetition.getId(), result);
		assertFalse(testPetition.getSignees().isEmpty());
		assertTrue(testPetition.getSignees().stream().anyMatch(s -> s.getName().equals("John Doe")));
	}

	@Test
	void contextLoads() {
	}

}
