-- Table: public.currency

-- DROP TABLE IF EXISTS public.currency;

CREATE TABLE IF NOT EXISTS public.currency
(
    code text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT currency_pkey PRIMARY KEY (code)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.currency
    OWNER to postgres;