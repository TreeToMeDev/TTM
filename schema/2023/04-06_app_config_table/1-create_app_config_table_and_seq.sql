CREATE SEQUENCE IF NOT EXISTS public.app_config_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.app_config_id_seq
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.app_config
(
    id integer NOT NULL DEFAULT nextval('app_config_id_seq'::regclass),
    category text COLLATE pg_catalog."default" NOT NULL,
    config_option text COLLATE pg_catalog."default" NOT NULL,
    text_value text COLLATE pg_catalog."default" NOT NULL,
    last_user text COLLATE pg_catalog."default" NOT NULL,
    last_timestamp timestamp with time zone NOT NULL DEFAULT now(),
    description text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    CONSTRAINT app_config_pkey PRIMARY KEY (id),
    CONSTRAINT category_option_alt UNIQUE (category, config_option)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.app_config
    OWNER to postgres;