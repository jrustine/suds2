package net.rustine.suds2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import net.rustine.suds2.Suds2Application;
import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Pet;
import net.rustine.suds2.entity.Schedule;
import net.rustine.suds2.entity.WorkSchedule;

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
public class ScheduleServiceTest {

	@Autowired
	private ScheduleService scheduleService;
	
	private Schedule testSchedule;
	
	@Autowired
	private GroomerService groomerService;

	private static final String EMPLOYEE1 = "SUDS001";
	private static final String EMPLOYEE2 = "SUDS002";

	private Groomer groomer;
	private Groomer anotherGroomer;

	@Autowired
	private CustomerService customerService;
	
	private static final String PHONE1 = "4101231234";
	private static final String PHONE2 = "4439998888";
	
	private Parent parent;
	private Parent anotherParent;

	@BeforeEach
	@Commit
	public void setup() throws Exception {
		Groomer newGroomer = new Groomer();
		newGroomer.setEmployeeNumber(EMPLOYEE1);
		newGroomer.setFirstName("Fiona");
		newGroomer.setLastName("Perkins");
		newGroomer.setHomePhoneNumber("(301) 222-4444");
		
		Set<WorkSchedule> workSchedule = new HashSet<>();
		workSchedule.add(new WorkSchedule(newGroomer, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule.add(new WorkSchedule(newGroomer, DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule.add(new WorkSchedule(newGroomer, DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule.add(new WorkSchedule(newGroomer, DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule.add(new WorkSchedule(newGroomer, DayOfWeek.SATURDAY, LocalTime.of(10, 0), LocalTime.of(14, 0)));
		newGroomer.setWorkSchedule(workSchedule);
		
		groomer = groomerService.saveGroomer(newGroomer);

		Groomer anotherNewGroomer = new Groomer();
		anotherNewGroomer.setEmployeeNumber(EMPLOYEE2);
		anotherNewGroomer.setFirstName("Desdemona");
		anotherNewGroomer.setLastName("Arlington");
		anotherNewGroomer.setHomePhoneNumber("(703) 333-7777");
		
		Set<WorkSchedule> anotherWorkSchedule = new HashSet<>();
		anotherWorkSchedule.add(new WorkSchedule(anotherNewGroomer, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		anotherWorkSchedule.add(new WorkSchedule(anotherNewGroomer, DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		anotherWorkSchedule.add(new WorkSchedule(anotherNewGroomer, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		anotherWorkSchedule.add(new WorkSchedule(anotherNewGroomer, DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		anotherWorkSchedule.add(new WorkSchedule(anotherNewGroomer, DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		anotherNewGroomer.setWorkSchedule(anotherWorkSchedule);
		
		anotherGroomer = groomerService.saveGroomer(anotherNewGroomer);
		
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
		
		Schedule schedule = new Schedule();
		schedule.setAppointmentTime(LocalDateTime.parse("2022-01-03T09:00:00"));
		schedule.setGroomer(groomer);
		schedule.setParent(parent);
		schedule.setPet(pet);

		testSchedule = scheduleService.saveSchedule(schedule);
		
		Schedule schedule2 = new Schedule();
		schedule2.setAppointmentTime(LocalDateTime.parse("2022-01-03T10:00:00"));
		schedule2.setGroomer(anotherGroomer);
		schedule2.setParent(parent);
		schedule2.setPet(anotherPet);

		scheduleService.saveSchedule(schedule2);
		
		Schedule schedule3 = new Schedule();
		schedule3.setAppointmentTime(LocalDateTime.parse("2022-01-04T13:00:00"));
		schedule3.setGroomer(groomer);
		schedule3.setParent(anotherParent);
		schedule3.setPet(yetAnotherPet);

		scheduleService.saveSchedule(schedule3);

		Schedule schedule4 = new Schedule();
		schedule4.setAppointmentTime(LocalDateTime.parse("2022-01-10T09:00:00"));
		schedule4.setGroomer(anotherGroomer);
		schedule4.setParent(parent);
		schedule4.setPet(pet);

		scheduleService.saveSchedule(schedule4);
	}
	
	@Test
	@Commit
	public void testScheduleRetrieves() throws Exception {
		Schedule result = scheduleService.getScheduleById(testSchedule.getId());
		
		assertNotNull(result);
		assertEquals(result.getId(), testSchedule.getId(), "id of " + result.getId() + " does not equal schedule id of " + testSchedule.getId());
		assertEquals(result.getAppointmentTime(), testSchedule.getAppointmentTime(), "time of " + result.getAppointmentTime() + " does not equal schedule time of " + testSchedule.getAppointmentTime());
		assertEquals(result.getGroomer().getId(), testSchedule.getGroomer().getId(), "groomer id of " + result.getGroomer().getId() + " does not equal schedule groomer id of " + testSchedule.getGroomer().getId());
		assertEquals(result.getParent().getId(), testSchedule.getParent().getId(), "parent id of " + result.getParent().getId() + " does not equal schedule parent id of " + testSchedule.getParent().getId());
		assertEquals(result.getPet().getId(), testSchedule.getPet().getId(), "pet id of " + result.getPet().getId() + " does not equal schedule pet id of " + testSchedule.getPet().getId());
		
		List<Schedule> results = scheduleService.getSchedule(
				LocalDateTime.parse("2022-01-03T00:00:00"), 
				LocalDateTime.parse("2022-01-08T23:59:59"));
		
		assertNotNull(results);
		assertEquals(results.size(), 3, "size of " + results.size() + " is not 3");
		assertTrue(results.stream().anyMatch(item -> LocalDateTime.parse("2022-01-03T09:00:00").equals(item.getAppointmentTime())));
		assertTrue(results.stream().anyMatch(item -> LocalDateTime.parse("2022-01-03T10:00:00").equals(item.getAppointmentTime())));
		assertTrue(results.stream().anyMatch(item -> LocalDateTime.parse("2022-01-04T13:00:00").equals(item.getAppointmentTime())));

		List<Schedule> results2 = scheduleService.getScheduleForGroomer(
				groomer,
				LocalDateTime.parse("2022-01-01T00:00:00"), 
				LocalDateTime.parse("2022-01-31T23:59:59"));

		assertNotNull(results2);
		assertEquals(results2.size(), 2, "size of " + results2.size() + " is not 2");
		assertTrue(results2.stream().anyMatch(item -> LocalDateTime.parse("2022-01-03T09:00:00").equals(item.getAppointmentTime())));
		assertTrue(results2.stream().anyMatch(item -> LocalDateTime.parse("2022-01-04T13:00:00").equals(item.getAppointmentTime())));

		List<Schedule> results3 = scheduleService.getScheduleForParent(
				parent,
				LocalDateTime.parse("2022-01-01T00:00:00"), 
				LocalDateTime.parse("2022-01-31T23:59:59"));
		assertNotNull(results3);
		assertEquals(results3.size(), 3, "size of " + results3.size() + " is not 3");
		assertTrue(results3.stream().anyMatch(item -> LocalDateTime.parse("2022-01-03T09:00:00").equals(item.getAppointmentTime())));
		assertTrue(results3.stream().anyMatch(item -> LocalDateTime.parse("2022-01-03T10:00:00").equals(item.getAppointmentTime())));
		assertTrue(results3.stream().anyMatch(item -> LocalDateTime.parse("2022-01-10T09:00:00").equals(item.getAppointmentTime())));
	}
}
