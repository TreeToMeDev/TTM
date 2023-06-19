-- Column: public.product.available_date

-- ALTER TABLE public.product DROP COLUMN available_date;

ALTER TABLE public.product
    ADD COLUMN available_date timestamp with time zone;