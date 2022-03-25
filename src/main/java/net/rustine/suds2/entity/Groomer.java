package net.rustine.suds2.entity;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Entity
@Table(name="groomer")
public class Groomer implements Comparable<Groomer> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String employeeNumber;
	private String firstName;
	private String lastName;
	private String homePhoneNumber;
	
	@OneToMany(mappedBy="groomer", cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderBy("week_day")
	@JsonManagedReference
	private Set<WorkSchedule> workSchedule;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public Set<WorkSchedule> getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(Set<WorkSchedule> workSchedule) {
		this.workSchedule = workSchedule;
	}

	/**
	 * Sort Groomers by last name, then first name.
	 */
	@Override
	public int compareTo(Groomer groomer0) {
	    return Comparator.comparing(Groomer::getLastName)
	              .thenComparing(Groomer::getFirstName)
	              .compare(this, groomer0);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
