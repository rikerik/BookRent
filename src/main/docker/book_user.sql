--
-- PostgreSQL database dump
--

-- Dumped from database version 16rc1
-- Dumped by pg_dump version 16.0

-- Started on 2024-03-06 21:09:30

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
-- TOC entry 4796 (class 0 OID 139618)
-- Dependencies: 224
-- Data for Name: book_user; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4804 (class 0 OID 0)
-- Dependencies: 222
-- Name: book_user_book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_user_book_id_seq', 1, false);


--
-- TOC entry 4805 (class 0 OID 0)
-- Dependencies: 223
-- Name: book_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_user_user_id_seq', 1, false);


-- Completed on 2024-03-06 21:09:30

--
-- PostgreSQL database dump complete
--

