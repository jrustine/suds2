INSERT INTO suds.parent(address, first_name, last_name, phone_number)
	VALUES ('{"street":"123 Everest Ave", "city":"Baltimore", "state":"MD", "zipCode":"21212"}',
	'Bartholomew',
	'Perkins',
	'4445556666');
INSERT INTO suds.pet(parent_id, name, notes, type)
	VALUES((SELECT MAX(id) FROM suds.parent),
	'Buster',
	'Trim ears carefully, he really doesn''t like it.',
	'Dog');
INSERT INTO suds.pet(parent_id, name, notes, type)
	VALUES((SELECT MAX(id) FROM suds.parent),
	'Fang',
	'Tail needs to be fluffed.',
	'Cat');

INSERT INTO suds.parent(address, first_name, last_name, phone_number)
	VALUES ('{"street":"678 Montauk Lane", "city":"Baltimore", "state":"MD", "zipCode":"21213"}',
	'Misty',
	'Anderson',
	'4447778888');
INSERT INTO suds.pet(parent_id, name, notes, type)
	VALUES((SELECT MAX(id) FROM suds.parent),
	'Betty',
	'Loves belly rubs, who doesn''t?',
	'Dog');

INSERT INTO suds.groomer(employee_number, first_name, last_name, home_phone_number)
	VALUES ('SUDS100',
	'Linda',
	'Morgan',
	'(222) 333-4444');

INSERT INTO suds.work_schedule(groomer_id, week_day, start_time, end_time)
	VALUES
	((SELECT MAX(id) FROM suds.groomer), 0, '08:00', '17:00'),
	((SELECT MAX(id) FROM suds.groomer), 1, '08:00', '17:00'),
	((SELECT MAX(id) FROM suds.groomer), 2, '08:00', '17:00'),
	((SELECT MAX(id) FROM suds.groomer), 4, '08:00', '17:00'),
	((SELECT MAX(id) FROM suds.groomer), 5, '10:00', '13:00');
	
INSERT INTO suds.groomer(employee_number, first_name, last_name, home_phone_number)
	VALUES ('SUDS200',
	'Georgia',
	'Adamson',
	'(123) 555-7878');

INSERT INTO suds.work_schedule(groomer_id, week_day, start_time, end_time)
	VALUES
	((SELECT MAX(id) FROM suds.groomer), 1, '09:00', '18:00'),
	((SELECT MAX(id) FROM suds.groomer), 2, '09:00', '18:00'),
	((SELECT MAX(id) FROM suds.groomer), 4, '09:00', '18:00'),
	((SELECT MAX(id) FROM suds.groomer), 5, '10:00', '13:00');