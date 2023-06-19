ALTER TABLE IF EXISTS public.product
    ADD COLUMN measurement text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text;
ALTER TABLE IF EXISTS public.product
    ADD COLUMN measurement_amount numeric NOT NULL DEFAULT 0.0;