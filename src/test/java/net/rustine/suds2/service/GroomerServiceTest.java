package net.rustine.suds2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
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
import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.entity.WorkSchedule;
import net.rustine.suds2.entity.service.GroomerService;

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
public class GroomerServiceTest {

	@Autowired
	private GroomerService groomerService;

	private static final String EMPLOYEE1 = "SUDS001";
	private static final String EMPLOYEE2 = "SUDS002";
	private static final String EMPLOYEE3 = "SUDS003";
	
	private Groomer groomer;
	private Groomer anotherGroomer;
	private Groomer yetAnotherGroomer;
	
	@BeforeEach
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

		Groomer yetAnotherNewGroomer = new Groomer();
		yetAnotherNewGroomer.setEmployeeNumber(EMPLOYEE3);
		yetAnotherNewGroomer.setFirstName("Roger");
		yetAnotherNewGroomer.setLastName("McCheese");
		yetAnotherNewGroomer.setHomePhoneNumber("(443) 555-6666");
		
		Set<WorkSchedule> workSchedule2 = new HashSet<>();
		workSchedule2.add(new WorkSchedule(yetAnotherNewGroomer, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule2.add(new WorkSchedule(yetAnotherNewGroomer, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		workSchedule2.add(new WorkSchedule(yetAnotherNewGroomer, DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(17, 0)));
		yetAnotherNewGroomer.setWorkSchedule(workSchedule2);
		
		yetAnotherGroomer = groomerService.saveGroomer(yetAnotherNewGroomer);
	}

	@Test
	public void testGroomerRetrieves() {
		Groomer result = groomerService.getGroomerById(groomer.getId());
		assertNotNull(result);
		assertEquals(result.getWorkSchedule().size(), 5, "worksSchedule size of " + result.getWorkSchedule().size() + " does not equal 5");
		assertEquals(result.getFirstName(), groomer.getFirstName(), "first name " + result.getFirstName() + " is not " + groomer.getFirstName());
		assertEquals(result.getLastName(), groomer.getLastName(), "last name " + result.getLastName() + " is not " + groomer.getLastName());
		assertEquals(result.getHomePhoneNumber(), groomer.getHomePhoneNumber(), "phone " + result.getHomePhoneNumber() + " is not " + groomer.getHomePhoneNumber());		

		Groomer result2 = groomerService.getGroomerByEmployeeNumber(EMPLOYEE1);
		assertNotNull(result2);
		assertEquals(result2.getWorkSchedule().size(), 5, "worksSchedule size of " + result2.getWorkSchedule().size() + " does not equal 5");
		assertEquals(result2.getFirstName(), groomer.getFirstName(), "first name " + result2.getFirstName() + " is not " + groomer.getFirstName());
		assertEquals(result2.getLastName(), groomer.getLastName(), "last name " + result2.getLastName() + " is not " + groomer.getLastName());
		assertEquals(result2.getHomePhoneNumber(), groomer.getHomePhoneNumber(), "phone " + result2.getHomePhoneNumber() + " is not " + groomer.getHomePhoneNumber());		

		Groomer anotherResult = groomerService.getGroomerById(anotherGroomer.getId());
		assertNotNull(anotherResult);
		assertEquals(anotherResult.getWorkSchedule().size(), 5, "worksSchedule size of " + anotherResult.getWorkSchedule().size() + " does not equal 5");
		assertEquals(anotherResult.getFirstName(), anotherGroomer.getFirstName(), "first name " + anotherResult.getFirstName() + " is not " + anotherGroomer.getFirstName());
		assertEquals(anotherResult.getLastName(), anotherGroomer.getLastName(), "last name " + anotherResult.getLastName() + " is not " + anotherGroomer.getLastName());
		assertEquals(anotherResult.getHomePhoneNumber(), anotherGroomer.getHomePhoneNumber(), "phone " + anotherResult.getHomePhoneNumber() + " is not " + anotherGroomer.getHomePhoneNumber());		

		Groomer anotherResult2 = groomerService.getGroomerByEmployeeNumber(EMPLOYEE2);
		assertNotNull(anotherResult2);
		assertEquals(anotherResult2.getWorkSchedule().size(), 5, "worksSchedule size of " + anotherResult2.getWorkSchedule().size() + " does not equal 5");
		assertEquals(anotherResult2.getFirstName(), anotherGroomer.getFirstName(), "first name " + anotherResult2.getFirstName() + " is not " + anotherGroomer.getFirstName());
		assertEquals(anotherResult2.getLastName(), anotherGroomer.getLastName(), "last name " + anotherResult2.getLastName() + " is not " + anotherGroomer.getLastName());
		assertEquals(anotherResult2.getHomePhoneNumber(), anotherGroomer.getHomePhoneNumber(), "phone " + anotherResult2.getHomePhoneNumber() + " is not " + anotherGroomer.getHomePhoneNumber());		

		Groomer yetAnotherResult = groomerService.getGroomerById(yetAnotherGroomer.getId());
		assertNotNull(yetAnotherResult);
		assertEquals(yetAnotherResult.getWorkSchedule().size(), 3, "worksSchedule size of " + yetAnotherResult.getWorkSchedule().size() + " does not equal 3");
		assertEquals(yetAnotherResult.getFirstName(), yetAnotherGroomer.getFirstName(), "first name " + yetAnotherResult.getFirstName() + " is not " + yetAnotherGroomer.getFirstName());
		assertEquals(yetAnotherResult.getLastName(), yetAnotherGroomer.getLastName(), "last name " + yetAnotherResult.getLastName() + " is not " + yetAnotherGroomer.getLastName());
		assertEquals(yetAnotherResult.getHomePhoneNumber(), yetAnotherGroomer.getHomePhoneNumber(), "phone " + yetAnotherResult.getHomePhoneNumber() + " is not " + yetAnotherGroomer.getHomePhoneNumber());		

		Groomer yetAnotherResult2 = groomerService.getGroomerByEmployeeNumber(EMPLOYEE3);
		assertNotNull(yetAnotherResult2);
		assertEquals(yetAnotherResult2.getWorkSchedule().size(), 3, "worksSchedule size of " + yetAnotherResult2.getWorkSchedule().size() + " does not equal 3");
		assertEquals(yetAnotherResult2.getFirstName(), yetAnotherGroomer.getFirstName(), "first name " + yetAnotherResult2.getFirstName() + " is not " + yetAnotherGroomer.getFirstName());
		assertEquals(yetAnotherResult2.getLastName(), yetAnotherGroomer.getLastName(), "last name " + yetAnotherResult2.getLastName() + " is not " + yetAnotherGroomer.getLastName());
		assertEquals(yetAnotherResult2.getHomePhoneNumber(), yetAnotherGroomer.getHomePhoneNumber(), "phone " + yetAnotherResult2.getHomePhoneNumber() + " is not " + yetAnotherGroomer.getHomePhoneNumber());		
	}
	
	@Test
	public void getAllGroomers() throws Exception {
		List<Groomer> groomers = groomerService.getAllGroomers();
		
		assertNotNull(groomers);
		assertTrue(groomers.size() > 0, "groomer list is empty");
		assertTrue(groomers.stream().anyMatch(item -> "Perkins".equals(item.getLastName())), "groomer list does not contain Perkins");
		assertTrue(groomers.stream().anyMatch(item -> "Arlington".equals(item.getLastName())), "groomer list does not contain Arlington");
		assertTrue(groomers.stream().anyMatch(item -> "McCheese".equals(item.getLastName())), "groomer list does not contain McCheese");
	}
}
