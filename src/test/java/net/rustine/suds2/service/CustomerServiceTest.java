package net.rustine.suds2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import net.rustine.suds2.Suds2Application;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Pet;
import net.rustine.suds2.service.CustomerService;

/*
 * Copyright (C) 2022 Jay Rustine
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Suds2Application.class)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	
	private static final String PHONE1 = "4101231234";
	private static final String PHONE2 = "4439998888";
	
	private Parent parent;
	private Parent anotherParent;

	@BeforeEach
	public void setup() throws Exception {
		Map<String,String> address = new HashMap<String,String>();
		address.put("street", "123 Main Street");
		address.put("city", "Baltimore");
		address.put("state", "MD");
		address.put("zipCode", "21213");

		Parent newParent = new Parent();
		newParent.setFirstName("Ariana");
		newParent.setLastName("Allbright");
		newParent.setPhoneNumber(PHONE1);
		newParent.setAddress(address);

		Set<Pet> pets = new HashSet<Pet>();
		Pet pet = new Pet();
		pet.setParent(newParent);
		pet.setName("Buddy");
		pet.setType("Dog");
		pet.setNotes("A little bitey in the harness.");
		pets.add(pet);
		
		Pet anotherPet = new Pet();
		anotherPet.setParent(newParent);
		anotherPet.setName("Fluffernutter");
		anotherPet.setType("Dog");
		anotherPet.setNotes("Likes peanut butter treats.");
		pets.add(anotherPet);
		
		newParent.setPets(pets);
		
		parent = customerService.saveParent(newParent);
		
		Map<String,String> anotherAddress = new HashMap<String,String>();
		anotherAddress.put("street", "123 Main Street");
		anotherAddress.put("city", "Crofton");
		anotherAddress.put("state", "MD");
		anotherAddress.put("zipCode", "21114");

		Parent anotherNewParent = new Parent();
		anotherNewParent.setFirstName("Sam");
		anotherNewParent.setLastName("Beckett");
		anotherNewParent.setPhoneNumber(PHONE2);
		anotherNewParent.setAddress(anotherAddress);
		
		Set<Pet> anotherPets = new HashSet<Pet>();
		Pet yetAnotherPet = new Pet();
		yetAnotherPet.setParent(anotherNewParent);
		yetAnotherPet.setName("Sparky");
		yetAnotherPet.setType("Cat");
		yetAnotherPet.setNotes("So mean, why are we washing cats?");
		anotherPets.add(yetAnotherPet);
		
		anotherNewParent.setPets(anotherPets);

		anotherParent = customerService.saveParent(anotherNewParent);
	}
	
	@Test
	public void testParentRetrieves() throws Exception {
		Parent result = customerService.getParentById(parent.getId());

		assertNotNull(result);
		assertEquals(result.getAddress().size(), 4, "address size of " + result.getAddress().size() + " does not equal 4");
		assertEquals(result.getFirstName(), parent.getFirstName(), "first name " + result.getFirstName() + " is not " + parent.getFirstName());
		assertEquals(result.getLastName(), parent.getLastName(), "last name " + result.getLastName() + " is not " + parent.getLastName());
		assertEquals(result.getPhoneNumber(), parent.getPhoneNumber(), "phone number " + result.getPhoneNumber() + " is not " + parent.getPhoneNumber());
		assertEquals(result.getPets().size(), 2, "pets size of " + result.getPets().size() + " does not equal 2");
		assertTrue(result.getPets().stream().anyMatch(item -> "Buddy".equals(item.getName())), "pet list does not contain Buddy");
		assertTrue(result.getPets().stream().anyMatch(item -> "Fluffernutter".equals(item.getName())), "pet list does not contain Fluffernutter");

		Parent result2 = customerService.getParentByPhoneNumber(PHONE1);

		assertNotNull(result2);
		assertEquals(result2.getAddress().size(), 4, "address size of " + result2.getAddress().size() + " does not equal 4");
		assertEquals(result2.getFirstName(), parent.getFirstName(), "first name " + result2.getFirstName() + " is not " + parent.getFirstName());
		assertEquals(result2.getLastName(), parent.getLastName(), "last name " + result2.getLastName() + " is not " + parent.getLastName());
		assertEquals(result2.getPhoneNumber(), parent.getPhoneNumber(), "phone number " + result2.getPhoneNumber() + " is not " + parent.getPhoneNumber());
		assertEquals(result2.getPets().size(), 2, "pets size of " + result2.getPets().size() + " does not equal 2");
		assertTrue(result2.getPets().stream().anyMatch(item -> "Buddy".equals(item.getName())), "pet list does not contain Buddy");
		assertTrue(result2.getPets().stream().anyMatch(item -> "Fluffernutter".equals(item.getName())), "pet list does not contain Fluffernutter");

		Parent anotherResult = customerService.getParentById(anotherParent.getId());
		
		assertNotNull(anotherResult);
		assertEquals(anotherResult.getAddress().size(), 4, "address size of " + anotherResult.getAddress().size() + " does not equal 4");
		assertEquals(anotherResult.getFirstName(), anotherParent.getFirstName(), "first name " + anotherResult.getFirstName() + " is not " + anotherParent.getFirstName());
		assertEquals(anotherResult.getLastName(), anotherParent.getLastName(), "last name " + anotherResult.getLastName() + " is not " + anotherParent.getLastName());
		assertEquals(anotherResult.getPhoneNumber(), anotherParent.getPhoneNumber(), "phone number " + anotherResult.getPhoneNumber() + " is not " + anotherParent.getPhoneNumber());
		assertEquals(anotherResult.getPets().size(), 1, "pets size of " + anotherResult.getPets().size() + " does not equal 1");
		assertTrue(anotherResult.getPets().stream().anyMatch(item -> "Sparky".equals(item.getName())), "pet list does not contain Sparky");

		Parent anotherResult2 = customerService.getParentByPhoneNumber(PHONE2);
		
		assertNotNull(anotherResult2);
		assertEquals(anotherResult2.getAddress().size(), 4, "address size of " + anotherResult2.getAddress().size() + " does not equal 4");
		assertEquals(anotherResult2.getFirstName(), anotherParent.getFirstName(), "first name " + anotherResult2.getFirstName() + " is not " + anotherParent.getFirstName());
		assertEquals(anotherResult2.getLastName(), anotherParent.getLastName(), "last name " + anotherResult2.getLastName() + " is not " + anotherParent.getLastName());
		assertEquals(anotherResult2.getPhoneNumber(), anotherParent.getPhoneNumber(), "phone number " + anotherResult2.getPhoneNumber() + " is not " + anotherParent.getPhoneNumber());
		assertEquals(anotherResult2.getPets().size(), 1, "pets size of " + anotherResult2.getPets().size() + " does not equal 1");
		assertTrue(anotherResult2.getPets().stream().anyMatch(item -> "Sparky".equals(item.getName())), "pet list does not contain Sparky");
	}
	
	@Test
	public void testGetAlls() throws Exception {
		List<Parent> parents = customerService.getAllParents();

		assertNotNull(parents);
		assertTrue(parents.size() > 0, "parent list is empty");
		assertTrue(parents.stream().anyMatch(item -> "Allbright".equals(item.getLastName())), "parent list does not contain Allbright");
		assertTrue(parents.stream().anyMatch(item -> "Beckett".equals(item.getLastName())), "parent list does not contain Beckett");
		
		List<Pet> pets = customerService.getAllPets();
		assertNotNull(pets);
		assertTrue(pets.size() > 0, "pet list is empty");
		assertTrue(pets.stream().anyMatch(item -> "Buddy".equals(item.getName())), "pet list does not contain Buddy");
		assertTrue(pets.stream().anyMatch(item -> "Fluffernutter".equals(item.getName())), "pet list does not contain Fluffernutter");
		assertTrue(pets.stream().anyMatch(item -> "Sparky".equals(item.getName())), "pet list does not contain Sparky");
	}
}
