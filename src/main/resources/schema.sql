DROP TABLE IF EXISTS suds.pet;
DROP TABLE IF EXISTS suds.parent;

CREATE TABLE IF NOT EXISTS suds.parent
(
    id serial NOT NULL,
    address jsonb NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT parent_pkey PRIMARY KEY (id),
    CONSTRAINT parent_phone_number_unique UNIQUE (phone_number)
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

ALTER TABLE IF EXISTS suds.parent
    OWNER to postgres;
ALTER TABLE IF EXISTS suds.pet
    OWNER to postgres;