DROP TABLE IF EXISTS suds.schedule;
DROP TABLE IF EXISTS suds.pet;
DROP TABLE IF EXISTS suds.parent;
DROP TABLE IF EXISTS suds.work_schedule;
DROP TABLE IF EXISTS suds.groomer;

CREATE TABLE IF NOT EXISTS suds.parent
(
    id serial NOT NULL,
    address jsonb NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT parent_pkey PRIMARY KEY (id),
    CONSTRAINT unique_parent_phone_number UNIQUE (phone_number)
);

CREATE INDEX pet_phone_number_idx ON suds.parent (phone_number);

CREATE TABLE IF NOT EXISTS suds.pet
(
    id serial NOT NULL,
    parent_id integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    notes character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pet_pkey PRIMARY KEY (id),
    CONSTRAINT fk_pet_parent_id FOREIGN KEY (parent_id)
        REFERENCES suds.parent (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS suds.groomer
(
    id serial NOT NULL,
    employee_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    home_phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groomer_pkey PRIMARY KEY (id),
    CONSTRAINT groomer_employee_number_unique UNIQUE (employee_number)
);

CREATE TABLE IF NOT EXISTS suds.work_schedule
(
    id serial NOT NULL,
    groomer_id integer NOT NULL,
    week_day integer NOT NULL,
    end_time time without time zone NOT NULL,
    start_time time without time zone NOT NULL,
    CONSTRAINT work_schedule_pkey PRIMARY KEY (id),
    CONSTRAINT fk_work_schedule_groomer FOREIGN KEY (groomer_id)
        REFERENCES suds.groomer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
    

CREATE TABLE IF NOT EXISTS suds.schedule
(
    id serial NOT NULL,
    appointment_time timestamp without time zone,
    groomer_id integer,
    parent_id integer,
    pet_id integer,
    CONSTRAINT schedule_pkey PRIMARY KEY (id),
    CONSTRAINT fk_schedule_groomer FOREIGN KEY (groomer_id)
        REFERENCES suds.groomer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_schedule_parent FOREIGN KEY (parent_id)
        REFERENCES suds.parent (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_schedule_pet FOREIGN KEY (pet_id)
        REFERENCES suds.pet (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT unique_schedule_appointment_groomer UNIQUE (appointment_time,groomer_id)
);

ALTER TABLE IF EXISTS suds.parent
    OWNER to postgres;
ALTER TABLE IF EXISTS suds.pet
    OWNER to postgres;
ALTER TABLE IF EXISTS suds.groomer
    OWNER to postgres;
ALTER TABLE IF EXISTS suds.work_schedule
    OWNER to postgres;
ALTER TABLE IF EXISTS suds.schedule
    OWNER to postgres;
