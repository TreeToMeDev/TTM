ALTER TABLE public.referral
    ADD COLUMN consent_to_contact boolean NOT NULL DEFAULT false;