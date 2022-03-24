package net.rustine.suds2.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.rustine.suds2.entity.Parent;

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
 * Parent repository interface, functionality is generated by JPA.
 */
public interface ParentRepository extends JpaRepository<Parent,Integer> {

	public Parent getByPhoneNumber(String phoneNumber);
}
