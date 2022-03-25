package net.rustine.suds2.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name="work_schedule")
public class WorkSchedule {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="groomer_id", nullable=false)
	@JsonBackReference
	private Groomer groomer;

	private DayOfWeek weekDay;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public WorkSchedule() { }

	public WorkSchedule(Groomer groomer, DayOfWeek weekDay, LocalTime startTime, LocalTime endTime) {
		super();
		this.groomer = groomer;
		this.weekDay = weekDay;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Groomer getGroomer() {
		return groomer;
	}

	public void setGroomer(Groomer groomer) {
		this.groomer = groomer;
	}

	public DayOfWeek getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(DayOfWeek weekDay) {
		this.weekDay = weekDay;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
