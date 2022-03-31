package net.rustine.suds2.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Schedule;
import net.rustine.suds2.repository.ScheduleRepository;

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
 * Implementation of schedule data service.
 */
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	/**
	 * Saves out schedule object.
	 * 
	 * @param Schedule
	 * @return schedule (with id if new)
	 */
	@Override
	public Schedule saveSchedule(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	/**
	 * Returns the matching schedule entry by id.
	 * 
	 * @param id
	 * @return matching schedule
	 */
	@Override
	public Schedule getScheduleById(Integer id) {
		return scheduleRepository.getById(id);
	}

	/**
	 * Get all the schedule entries between the specified times.
	 * 
	 * @param start time
	 * @param end time
	 * @return matching Schedules
	 */
	@Override
	public List<Schedule> getSchedule(LocalDateTime start, LocalDateTime end) {
		return scheduleRepository.findByAppointmentTimeBetween(start, end);
	}

	/**
	 * Get the schedule entries for a specific Groomer.
	 * 
	 * @param Groomer
	 * @param start time
	 * @param end time
	 * @return matching Schedules
	 */
	@Override
	public List<Schedule> getScheduleForGroomer(Groomer groomer, LocalDateTime start, LocalDateTime end) {
		return scheduleRepository.findByGroomerAndAppointmentTimeBetween(groomer, start, end);
	}

	/**
	 * Get the schedule entries for a specific Parent.
	 * 
	 * @param Parent
	 * @param start time
	 * @param end time
	 * @return matching Schedules
	 */
	@Override
	public List<Schedule> getScheduleForParent(Parent parent, LocalDateTime start, LocalDateTime end) {
		return scheduleRepository.findByParentAndAppointmentTimeBetween(parent, start, end);
	}
}
