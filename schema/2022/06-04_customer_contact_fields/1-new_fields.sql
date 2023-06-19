--  New fields to customer_contact table

ALTER TABLE public.customer_contact
    ADD COLUMN first_name text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
	ADD COLUMN last_name text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN title text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN phone_cell text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN department text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN street text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN city text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN province text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN pcode text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN country text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    ADD COLUMN notes text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text;