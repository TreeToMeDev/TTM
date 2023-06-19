-- Table: public.product

-- DROP TABLE public.product;

CREATE SEQUENCE public.product_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.product_id_seq
    OWNER TO postgres;
	
CREATE TABLE public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    product_code text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    quantity_on_hand integer NOT NULL,
    last_user text COLLATE pg_catalog."default" NOT NULL,
    last_timestamp timestamp with time zone NOT NULL DEFAULT now(),
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_code_alt UNIQUE (product_code)
)

TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to postgres;