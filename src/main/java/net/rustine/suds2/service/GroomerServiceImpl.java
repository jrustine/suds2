package net.rustine.suds2.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.repository.GroomerRepository;

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
 * Implementation of groomer data service.
 */
public class GroomerServiceImpl implements GroomerService {
	
	@Autowired
	private GroomerRepository groomerRepository;

	/**
	 * Saves out groomer object.
	 * 
	 * @param groomer
	 * @return groomer (with id if new)
	 */
	@Override
	public Groomer saveGroomer(Groomer groomer) {
		return groomerRepository.save(groomer);
	}

	/**
	 * Retrieves groomer by record id.
	 * 
	 * @param id
	 * @return groomer
	 */
	@Override
	public Groomer getGroomerById(Integer id) {
		return groomerRepository.getById(id);
	}

	/**
	 * Retrieves groomer by employee number.
	 * 
	 * @param employee number
	 * @return groomer
	 */
	@Override
	public Groomer getGroomerByEmployeeNumber(String employeeNumber) {
		return groomerRepository.getByEmployeeNumber(employeeNumber);
	}

	/**
	 * Returns sorted list of all groomers.
	 * 
	 * @return groomers
	 */
	@Override
	public List<Groomer> getAllGroomers() {
		List<Groomer> groomers = groomerRepository.findAll();
		Collections.sort(groomers);
		return groomers;
	}
}
