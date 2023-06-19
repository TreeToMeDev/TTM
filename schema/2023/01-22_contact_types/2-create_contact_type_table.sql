-- Table: public.contact_type

-- DROP TABLE IF EXISTS public.contact_type;

CREATE TABLE IF NOT EXISTS public.contact_type
(
    contact_id integer NOT NULL,
    contact_type_key text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT contact_type_pkey1 PRIMARY KEY (contact_id, contact_type_key)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contact_type
    OWNER to postgres;