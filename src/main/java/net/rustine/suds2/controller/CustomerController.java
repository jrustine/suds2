package net.rustine.suds2.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.rustine.suds2.controller.exception.MissingRecordException;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.service.CustomerService;

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
 * Controller for Customer data.
 */
@RestController
@RequestMapping("customer")
public class CustomerController {
	private static final Logger log = LogManager.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	/**
	 * Default path, returns all customers with pets.
	 */
	@GetMapping(value="/", produces="application/json")
	public List<Parent> getAllParents() {
		List<Parent> parents = customerService.getAllParents();
		log.debug("found [" + parents.size() + "] customers");
		return parents;
	}

	/**
	 * Returns specific customer by phone number.
	 * 
	 * @param phoneNumber
	 */
	@GetMapping(value="/{phoneNumber}", produces="application/json")
	public Parent getParent(@PathVariable String phoneNumber) {
		Parent parent = customerService.getParentByPhoneNumber(phoneNumber);
		if (parent == null) {
			throw new MissingRecordException(phoneNumber);
		}
		return parent;
	}
}