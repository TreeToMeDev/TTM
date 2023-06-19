-- New Contact Fields to Track Source of Contact

ALTER TABLE public.contact
    ADD COLUMN source text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text;
ALTER TABLE public.contact
    ADD COLUMN referral_id integer NOT NULL DEFAULT '-1'::integer;
ALTER TABLE public.contact
    ADD COLUMN file_id integer NOT NULL DEFAULT '-1'::integer;