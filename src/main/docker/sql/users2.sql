--
-- PostgreSQL database dump
--

-- Dumped from database version 16rc1
-- Dumped by pg_dump version 16.0

-- Started on 2024-03-06 22:21:44

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
-- TOC entry 220 (class 1259 OID 49331)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    role character varying(255),
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 49330)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4798 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4645 (class 2604 OID 49334)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4792 (class 0 OID 49331)
-- Dependencies: 220
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, role, email, password, username) VALUES (1, 'USER', 'erik@gmail.com', '$2a$10$rLSnabPWpDDjXQGxvDJUfujAaAny1CyKB6MkaK6ibEuVeAAE6UQUC', 'Erik');
INSERT INTO public.users (id, role, email, password, username) VALUES (2, 'ADMIN', 'admin@gmail.com', '$2a$10$AxUjCR4zcRXjOPTgcSD.fe4HoHq39GNbVMFjUiAX1vOPJE2rzue7C', 'Admin');
INSERT INTO public.users (id, role, email, password, username) VALUES (3, 'USER', 'test@gmail.com', '$2a$10$9aUbftkQD3cahzDHDoH/teTWQ5oe9kQhbP681kLs1fhQKpcKVFXzS', 'Test');
INSERT INTO public.users (id, role, email, password, username) VALUES (6, 'USER', 'asd@gmail.com', '$2a$10$hWslQ.vg9PIAMGACVyRnu.ltNUcX2XDZuuk6R03IJrrLcPdn3CvO6', 'Erik1');


--
-- TOC entry 4799 (class 0 OID 0)
-- Dependencies: 219
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 3, true);


--
-- TOC entry 4647 (class 2606 OID 49338)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2024-03-06 22:21:44

--
-- PostgreSQL database dump complete
--

