-- Column: public.account.phone

-- ALTER TABLE public.account DROP COLUMN phone;

ALTER TABLE public.account
    ADD COLUMN phone text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text;