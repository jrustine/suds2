package net.rustine.suds2.entity.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Pet;
import net.rustine.suds2.entity.repository.ParentRepository;
import net.rustine.suds2.entity.repository.PetRepository;

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
 * Implementation of customer data service.
 */
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private PetRepository petRepository;

	/**
	 * Saves out parent object.
	 * 
	 * @param parent
	 * @return parent (with id if new)
	 */
	@Override
	public Parent saveParent(Parent parent) {
		
		// Clean phone number just in case.
		parent.setPhoneNumber(StringUtils.getDigits(parent.getPhoneNumber()));
		
		return parentRepository.save(parent);
	}

	/**
	 * Retrieves parent by record id.
	 * 
	 * @param id
	 * @return parent
	 */
	@Override
	public Parent getParentById(Integer id) {
		return parentRepository.getById(id);
	}

	/**
	 * Retrieves parent by phone number.
	 * 
	 * @param phone number
	 * @return parent
	 */
	@Override
	public Parent getParentByPhoneNumber(String phoneNumber) {
		return parentRepository.getByPhoneNumber(phoneNumber);
	}

	/**
	 * Returns sorted list of all parents.
	 * 
	 * @return parents
	 */
	@Override
	public List<Parent> getAllParents() {
		List<Parent> results = parentRepository.findAll();
		Collections.sort(results);
		return results;
	}

	/**
	 * Returns sorted list of all pets.
	 */
	@Override
	public List<Pet> getAllPets() {
		List<Pet> results = petRepository.findAll();
		Collections.sort(results);
		return results;
	}
}
