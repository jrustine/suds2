package net.rustine.suds2.service;

import java.time.LocalDateTime;
import java.util.List;

import net.rustine.suds2.entity.Groomer;
import net.rustine.suds2.entity.Parent;
import net.rustine.suds2.entity.Schedule;

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
 * Interface for the schedule data service.
 */
public interface ScheduleService {

	public Schedule saveSchedule(Schedule schedule);
	public Schedule getScheduleById(Integer id);
	public List<Schedule> getSchedule(LocalDateTime start, LocalDateTime end);
	public List<Schedule> getScheduleForGroomer(Groomer groomer, LocalDateTime start, LocalDateTime end);
	public List<Schedule> getScheduleForParent(Parent parent, LocalDateTime start, LocalDateTime end);
}
