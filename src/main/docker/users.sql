--
-- PostgreSQL database dump
--

-- Dumped from database version 16rc1
-- Dumped by pg_dump version 16.0

-- Started on 2024-03-06 21:09:47

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


-- Completed on 2024-03-06 21:09:47

--
-- PostgreSQL database dump complete
--

