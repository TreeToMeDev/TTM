-- Column: public.product.price

ALTER TABLE IF EXISTS public.product
    ADD COLUMN price numeric NOT NULL DEFAULT 0.0;