CREATE SEQUENCE public.referral_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.referral_id_seq
    OWNER TO postgres;
	
CREATE TABLE public.referral
(
    id integer NOT NULL DEFAULT nextval('referral_id_seq'::regclass),
    referrer_first_name text COLLATE pg_catalog."default",
    referrer_last_name text COLLATE pg_catalog."default",
    referrer_email text COLLATE pg_catalog."default",
    referrer_phone text COLLATE pg_catalog."default",
    first_name text COLLATE pg_catalog."default",
    last_name text COLLATE pg_catalog."default",
    email text COLLATE pg_catalog."default",
    phone text COLLATE pg_catalog."default",
    company_name text COLLATE pg_catalog."default",
    job_title text COLLATE pg_catalog."default",
    notes text COLLATE pg_catalog."default",
    submit_timestamp timestamp without time zone DEFAULT (now() AT TIME ZONE 'utc'::text),
    CONSTRAINT referral_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.referral
    OWNER to postgres;