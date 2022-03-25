package net.rustine.suds2.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.rustine.suds2.service.CustomerService;
import net.rustine.suds2.service.CustomerServiceImpl;
import net.rustine.suds2.service.GroomerService;
import net.rustine.suds2.service.GroomerServiceImpl;

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
 * Spring configuration for services.
 */
@Configuration
public class ServiceConfig {

	@Bean
	public CustomerService customerService() {
		CustomerServiceImpl customerService = new CustomerServiceImpl();
		return customerService;
	}
	
	@Bean
	public GroomerService groomerService() {
		GroomerServiceImpl groomerService = new GroomerServiceImpl();
		return groomerService;
	}
}
