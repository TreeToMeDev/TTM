CREATE SEQUENCE public.attachment_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.attachment_id_seq
    OWNER TO postgres;

CREATE TABLE public.attachment
(
    id integer NOT NULL DEFAULT nextval('attachment_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    size bigint NOT NULL,
    contact_id integer NOT NULL DEFAULT '-1'::integer,
    account_id integer NOT NULL DEFAULT '-1'::integer,
    user_id integer NOT NULL DEFAULT '-1'::integer,
    create_timestamp timestamp with time zone NOT NULL DEFAULT now(),
    CONSTRAINT attachment_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.attachment
    OWNER to postgres;