package net.rustine.suds2.entity;

import java.time.LocalDateTime;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="schedule")
public class Schedule implements Comparable<Schedule> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private LocalDateTime appointmentTime;

	@ManyToOne
	@JoinColumn(name="groomer_id")
	@JsonManagedReference
	private Groomer groomer;

	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonManagedReference
	private Parent parent;

	@ManyToOne
	@JoinColumn(name="pet_id")
	@JsonManagedReference
	private Pet pet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Groomer getGroomer() {
		return groomer;
	}

	public void setGroomer(Groomer groomer) {
		this.groomer = groomer;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	/**
	 * Sort Schedules by appointment time.
	 */
	@Override
	public int compareTo(Schedule schedule0) {
	    return Comparator.comparing(Schedule::getAppointmentTime)
	              .compare(this, schedule0);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
