-- Column: public.product.warranty_duration

-- ALTER TABLE public.product DROP COLUMN warranty_duration;

ALTER TABLE public.product
    ADD COLUMN warranty_duration text COLLATE pg_catalog."default";