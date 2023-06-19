ALTER TABLE public.attachment
    ADD COLUMN storage_id text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text;