CREATE SEQUENCE public.agent_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.agent_id_seq
    OWNER TO postgres;

-- Table: public.agent

-- DROP TABLE IF EXISTS public.agent;

-- Table: public.agent

-- DROP TABLE IF EXISTS public.agent;

CREATE TABLE IF NOT EXISTS public.agent
(
    id integer NOT NULL DEFAULT nextval('agent_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.agent
    OWNER to postgres;
-- Index: agents_id_idx

-- DROP INDEX IF EXISTS public.agents_id_idx;

CREATE UNIQUE INDEX IF NOT EXISTS agents_id_idx
    ON public.agent USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;

-- SEQUENCE: public.app_user_id_seq

-- DROP SEQUENCE public.app_user_id_seq;

CREATE SEQUENCE public.app_user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.app_user_id_seq
    OWNER TO postgres;
	
-- Table: public.app_user

-- DROP TABLE IF EXISTS public.app_user;

CREATE TABLE IF NOT EXISTS public.app_user
(
    id integer NOT NULL DEFAULT nextval('app_user_id_seq'::regclass),
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.app_user
    OWNER to postgres;
-- Index: users_id_idx

-- DROP INDEX IF EXISTS public.users_id_idx;

CREATE UNIQUE INDEX IF NOT EXISTS users_id_idx
    ON public.app_user USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;
    
-- Table: public.agent_user

-- DROP TABLE IF EXISTS public.agent_user;

CREATE TABLE IF NOT EXISTS public.agent_user
(
    agent_id integer NOT NULL,
    user_id integer NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.agent_user
    OWNER to postgres;
-- Index: agents_users_agent_id_user_id_idx

-- DROP INDEX IF EXISTS public.agents_users_agent_id_user_id_idx;

CREATE UNIQUE INDEX IF NOT EXISTS agents_users_agent_id_user_id_idx
    ON public.agent_user USING btree
    (agent_id ASC NULLS LAST, user_id ASC NULLS LAST)
    TABLESPACE pg_default;