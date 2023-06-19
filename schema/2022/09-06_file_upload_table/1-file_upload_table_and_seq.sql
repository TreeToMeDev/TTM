CREATE SEQUENCE public.file_upload_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.file_upload_id_seq
    OWNER TO postgres;

CREATE TABLE public.file_upload
(
    id integer NOT NULL DEFAULT nextval('file_upload_id_seq'::regclass),
    file_name text COLLATE pg_catalog."default" NOT NULL,
    file_code text COLLATE pg_catalog."default" NOT NULL,
    status text COLLATE pg_catalog."default" NOT NULL,
    last_user text COLLATE pg_catalog."default" NOT NULL,
    last_timestamp timestamp with time zone NOT NULL DEFAULT now(),
    file_content_type text COLLATE pg_catalog."default" NOT NULL,
    original_file_name text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    CONSTRAINT file_upload_pkey PRIMARY KEY (id),
    CONSTRAINT file_code_alt UNIQUE (file_code)
)

TABLESPACE pg_default;

ALTER TABLE public.file_upload
    OWNER to postgres;