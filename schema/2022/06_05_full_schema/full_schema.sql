--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.account (
    id integer NOT NULL,
    account_type character varying NOT NULL,
    billing_city character varying NOT NULL,
    billing_country character varying NOT NULL,
    billing_street character varying NOT NULL,
    billing_postal_code character varying NOT NULL,
    billing_province_state character varying NOT NULL,
    currency character varying NOT NULL,
    industry character varying NOT NULL,
    language character varying NOT NULL,
    name character varying NOT NULL,
    shipping_city character varying NOT NULL,
    shipping_country character varying NOT NULL,
    shipping_postal_code character varying NOT NULL,
    shipping_province_state character varying NOT NULL,
    shipping_street character varying NOT NULL,
    web_site character varying NOT NULL
);


ALTER TABLE public.account OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO postgres;

--
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    code text NOT NULL,
    name text NOT NULL,
    address text,
    country text,
    language text NOT NULL,
    agent_code text,
    last_user text,
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_communication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_communication (
    id bigint NOT NULL,
    customer_code text NOT NULL,
    message text NOT NULL,
    last_user text,
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.customer_communication OWNER TO postgres;

--
-- Name: customer_communication_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_communication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_communication_id_seq OWNER TO postgres;

--
-- Name: customer_communication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_communication_id_seq OWNED BY public.customer_communication.id;


--
-- Name: customer_contact; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_contact (
    id integer NOT NULL,
    name text NOT NULL,
    email text,
    phone text,
    customer_code text,
    last_user text,
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL,
    first_name text DEFAULT ''::text NOT NULL,
    last_name text DEFAULT ''::text NOT NULL,
    title text DEFAULT ''::text NOT NULL,
    phone_cell text DEFAULT ''::text NOT NULL,
    department text DEFAULT ''::text NOT NULL,
    street text DEFAULT ''::text NOT NULL,
    city text DEFAULT ''::text NOT NULL,
    province text DEFAULT ''::text NOT NULL,
    pcode text DEFAULT ''::text NOT NULL,
    country text DEFAULT ''::text NOT NULL,
    notes text DEFAULT ''::text NOT NULL,
    account_id integer DEFAULT '-1'::integer NOT NULL
);


ALTER TABLE public.customer_contact OWNER TO postgres;

--
-- Name: customer_contact_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_contact_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_contact_id_seq OWNER TO postgres;

--
-- Name: customer_contact_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_contact_id_seq OWNED BY public.customer_contact.id;


--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_id_seq OWNER TO postgres;

--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- Name: customer_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_product (
    id integer NOT NULL,
    product_code text NOT NULL,
    purchase_date date,
    serial_no text,
    warranty_end_date date,
    last_user text,
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL,
    account_id integer DEFAULT '-1'::integer NOT NULL
);


ALTER TABLE public.customer_product OWNER TO postgres;

--
-- Name: customer_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_product_id_seq OWNER TO postgres;

--
-- Name: customer_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_product_id_seq OWNED BY public.customer_product.id;


--
-- Name: note; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.note (
    id integer NOT NULL,
    account_id integer NOT NULL,
    text character varying NOT NULL,
    "timestamp" timestamp with time zone NOT NULL
);


ALTER TABLE public.note OWNER TO postgres;

--
-- Name: note_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.note_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.note_id_seq OWNER TO postgres;

--
-- Name: note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.note_id_seq OWNED BY public.note.id;


--
-- Name: account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);


--
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- Name: customer_communication id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_communication ALTER COLUMN id SET DEFAULT nextval('public.customer_communication_id_seq'::regclass);


--
-- Name: customer_contact id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_contact ALTER COLUMN id SET DEFAULT nextval('public.customer_contact_id_seq'::regclass);


--
-- Name: customer_product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_product ALTER COLUMN id SET DEFAULT nextval('public.customer_product_id_seq'::regclass);


--
-- Name: note id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.note ALTER COLUMN id SET DEFAULT nextval('public.note_id_seq'::regclass);


--
-- Name: account account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- Name: customer customer_code_alt; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_code_alt UNIQUE (code);


--
-- Name: customer_communication customer_communication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_communication
    ADD CONSTRAINT customer_communication_pkey PRIMARY KEY (id);


--
-- Name: customer_contact customer_contact_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_contact
    ADD CONSTRAINT customer_contact_pkey PRIMARY KEY (id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: customer_product customer_product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_product
    ADD CONSTRAINT customer_product_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

