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
