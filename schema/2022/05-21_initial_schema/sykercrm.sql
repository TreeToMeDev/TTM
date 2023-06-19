--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.18
-- Dumped by pg_dump version 9.6.18

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

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

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
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL
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
    customer_code text,
    last_user text,
    last_timestamp timestamp with time zone DEFAULT now() NOT NULL
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
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (id, code, name, address, country, language, agent_code, last_user, last_timestamp) FROM stdin;
\.


--
-- Data for Name: customer_communication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer_communication (id, customer_code, message, last_user, last_timestamp) FROM stdin;
\.


--
-- Name: customer_communication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_communication_id_seq', 1, false);


--
-- Data for Name: customer_contact; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer_contact (id, name, email, phone, customer_code, last_user, last_timestamp) FROM stdin;
\.


--
-- Name: customer_contact_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_contact_id_seq', 1, true);


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_id_seq', 1, true);


--
-- Data for Name: customer_product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer_product (id, product_code, purchase_date, serial_no, warranty_end_date, customer_code, last_user, last_timestamp) FROM stdin;
\.


--
-- Name: customer_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_product_id_seq', 1, true);


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
-- Name: customer_product serial_no_alt; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_product
    ADD CONSTRAINT serial_no_alt UNIQUE (serial_no);


--
-- PostgreSQL database dump complete
--

