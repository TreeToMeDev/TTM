-- Table: public.contact_type_list

-- DROP TABLE IF EXISTS public.contact_type_list;

CREATE TABLE IF NOT EXISTS public.contact_type_list
(
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT contact_type_pkey PRIMARY KEY (description)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contact_type_list
    OWNER to postgres;