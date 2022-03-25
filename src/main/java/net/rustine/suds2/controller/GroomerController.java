package net.rustine.suds2.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.rustine.suds2.controller.exception.MissingRecordException;
import net.rustine.suds2.entity.Groomer;
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
 * 
 * Controller for Groomer data.
 */
@RestController
@RequestMapping("groomer")
public class GroomerController {
	private static final Logger log = LogManager.getLogger(GroomerController.class);

	@Autowired
	private GroomerService groomerService;

	/**
	 * Default path, returns all groomers.
	 */
	@GetMapping(value="/", produces="application/json")
	public List<Groomer> getAllGroomers() {
		List<Groomer> groomers = groomerService.getAllGroomers();
		log.debug("found [" + groomers.size() + "] groomers");
		return groomers;
	}

	/**
	 * Returns specific groomer by employee number.
	 * 
	 * @param employeeNumber
	 */
	@GetMapping(value="/{employeeNumber}", produces="application/json")
	public Groomer getGroomer(@PathVariable String employeeNumber) {
		Groomer groomer = groomerService.getGroomerByEmployeeNumber(employeeNumber);
		if (groomer == null) {
			throw new MissingRecordException(employeeNumber);
		}
		return groomer;
	}

	/**
	 * Saves out a groomer, the id field is not populated for new groomers,
	 * but required for updates. Sample posted JSON:
	 * 
	 *	{
	 *		"id" : 3, <-- only required for updates
	 *		"employeeNumber": "SUDS004",
	 *		"firstName": "Belmont",
	 *		"lastName": "Oaks",
	 *		"homePhoneNumber": "(410) 555-7777",
	 *		"workSchedule": [
	 *			{
	 *				"weekDay": "MONDAY",
	 *				"startTime": "08:00",
	 *				"endTime": "17:00"
	 *			},
	 *			{
	 *				"weekDay": "WEDNESDAY",
	 *				"startTime": "08:00",
	 *				"endTime": "17:00"
	 *			},
	 *			{
	 *				"weekDay": "FRIDAY",
	 *				"startTime": "08:00",
	 *				"endTime": "17:00"
	 *			}
	 *		]
	 *	}
	 * 
	 * @param groomer
	 */
	@PostMapping(value="/", consumes="application/json")
	public void saveGroomer(@RequestBody Groomer groomer) {
		groomerService.saveGroomer(groomer);
	}	
}
