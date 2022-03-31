package net.rustine.suds2.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.rustine.suds2.controller.exception.MissingRecordException;
import net.rustine.suds2.controller.request.ScheduleRequest;
import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Pet;
import net.rustine.suds2.entity.Schedule;
import net.rustine.suds2.service.CustomerService;
import net.rustine.suds2.service.GroomerService;
import net.rustine.suds2.service.ScheduleService;

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
 * Controller for Schedule data.
 */
@RestController
@RequestMapping("schedule")
public class ScheduleController {
	private static final Logger log = LogManager.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private GroomerService groomerService;
	
	@Autowired
	private CustomerService customerService;

	private static DateTimeFormatter appointmentTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * Retrieves schedule entries between the specified start and end dates (inclusive).
	 * 
	 * @param startDate
	 * @param endDate
	 * @return matching entries
	 */
	@GetMapping(value="/{startDate}/{endDate}", produces="application/json")
	public List<Schedule> getScheduleByDateRange(
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) {
		
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23, 59);
		
		List<Schedule> schedules = scheduleService.getSchedule(startDateTime, endDateTime);
		log.debug("found [" + schedules.size() + "] schedule entries");
		
		return declutter(schedules);
	}

	/**
	 * Retrieves schedule for a specific groomer by employee number.
	 * 
	 * @param employeeNumber
	 * @param startDate
	 * @param endDate
	 * @return matching entries
	 */
	@GetMapping(value="/groomer/{employeeNumber}/{startDate}/{endDate}", produces="application/json")
	public List<Schedule> getScheduleByGroomer(
			@PathVariable String employeeNumber,
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) {
		
		Groomer groomer = groomerService.getGroomerByEmployeeNumber(employeeNumber);
		if (groomer == null) {
			throw new MissingRecordException(employeeNumber);
		}
		
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23, 59);
		
		List<Schedule> schedules = scheduleService.getScheduleForGroomer(groomer, startDateTime, endDateTime);
		log.debug("found [" + schedules.size() + "] schedule entries for employee " + employeeNumber);

		return declutter(schedules);
	}
	
	/**
	 * Retrieves schedule for a specific customer by phone number.
	 * 
	 * @param phoneNumber
	 * @param startDate
	 * @param endDate
	 * @return matching entries
	 */
	@GetMapping(value="/customer/{phoneNumber}/{startDate}/{endDate}", produces="application/json")
	public List<Schedule> getScheduleByCustomer(
			@PathVariable String phoneNumber,
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
			@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) {
		
		Parent parent = customerService.getParentByPhoneNumber(phoneNumber);
		if (parent == null) {
			throw new MissingRecordException(phoneNumber);
		}
		
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23, 59);
		
		List<Schedule> schedules = scheduleService.getScheduleForParent(parent, startDateTime, endDateTime);
		log.debug("found [" + schedules.size() + "] schedule entries for customer " + phoneNumber);

		return declutter(schedules);
	}

	/**
	 * Default path, saves schedule request. The id field is not populated for new entries,
	 * but required for updates. Sample posted JSON:
	 * 
	 *	{
	 *		"id" : 3, <-- only required for updates
	 *		"appointmentTime": "2022-01-05 10:30",
	 *		"employeeNumber" : "SUDS001",
	 *		"phoneNumber": "(410) 123-1234",
	 *		"petName" : "Fluffernutter"
	 *	}
	 */
	@PostMapping(value="/", consumes="application/json")
	public void saveSchedule(@RequestBody ScheduleRequest scheduleRequest) {

		Groomer groomer = groomerService.getGroomerByEmployeeNumber(scheduleRequest.getEmployeeNumber());
		if (groomer == null) {
			throw new MissingRecordException(scheduleRequest.getEmployeeNumber());
		}

		Parent parent = customerService.getParentByPhoneNumber(scheduleRequest.getPhoneNumber());
		if (parent == null) {
			throw new MissingRecordException(scheduleRequest.getPhoneNumber());
		}
		
		Pet pet = parent.getPets()
				.stream()
				.filter(p -> scheduleRequest.getPetName().equals(p.getName()))
				.findFirst()
				.orElseThrow(() -> new MissingRecordException(scheduleRequest.getPhoneNumber() + "/" + scheduleRequest.getPetName()));
		
		Schedule schedule = null;
		if (scheduleRequest.getId() != null) {
			schedule = scheduleService.getScheduleById(scheduleRequest.getId());
			if (schedule == null) {
				throw new MissingRecordException("schedule id " + scheduleRequest.getId());
			}
		} else {
			schedule = new Schedule();
		}
		
		schedule.setAppointmentTime(LocalDateTime.parse(scheduleRequest.getAppointmentTime(), appointmentTimeFormatter));
		schedule.setGroomer(groomer);
		schedule.setParent(parent);
		schedule.setPet(pet);
		
		scheduleService.saveSchedule(schedule);
	}

	/**
	 * Declutter the schedule responses by nulling out some unnecessary
	 * fields.
	 * 
	 * @param schedules
	 * @return decluttered schedules
	 */
	private List<Schedule> declutter(List<Schedule> schedules) {

		// De-clutter response by nulling groomer work schedules and parent pets.
		schedules
			.stream()
			.forEach(schedule -> {
				schedule.getGroomer().setWorkSchedule(null);
				schedule.getParent().setPets(null);
			});
		
		return schedules;
	}
}
