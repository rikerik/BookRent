--
-- PostgreSQL database dump
--

-- Dumped from database version 16rc1
-- Dumped by pg_dump version 16.0

-- Started on 2024-03-06 22:22:23

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
-- TOC entry 224 (class 1259 OID 139618)
-- Name: book_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_user (
    book_id integer NOT NULL,
    user_id integer NOT NULL,
    due_date character varying(255)
);


ALTER TABLE public.book_user OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 139616)
-- Name: book_user_book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_user_book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_user_book_id_seq OWNER TO postgres;

--
-- TOC entry 4802 (class 0 OID 0)
-- Dependencies: 222
-- Name: book_user_book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_user_book_id_seq OWNED BY public.book_user.book_id;


--
-- TOC entry 223 (class 1259 OID 139617)
-- Name: book_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_user_user_id_seq OWNER TO postgres;

--
-- TOC entry 4803 (class 0 OID 0)
-- Dependencies: 223
-- Name: book_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_user_user_id_seq OWNED BY public.book_user.user_id;


--
-- TOC entry 4645 (class 2604 OID 139621)
-- Name: book_user book_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_user ALTER COLUMN book_id SET DEFAULT nextval('public.book_user_book_id_seq'::regclass);


--
-- TOC entry 4646 (class 2604 OID 139622)
-- Name: book_user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_user ALTER COLUMN user_id SET DEFAULT nextval('public.book_user_user_id_seq'::regclass);


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


--
-- TOC entry 4648 (class 2606 OID 139624)
-- Name: book_user book_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_user
    ADD CONSTRAINT book_user_pkey PRIMARY KEY (book_id, user_id);


--
-- TOC entry 4649 (class 2606 OID 139630)
-- Name: book_user fkcf17f0644muvsjeajr9ffvuk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_user
    ADD CONSTRAINT fkcf17f0644muvsjeajr9ffvuk1 FOREIGN KEY (book_id) REFERENCES public.books(book_id);


--
-- TOC entry 4650 (class 2606 OID 139625)
-- Name: book_user fkx32ve2n84xbvvntxlyxsgijv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_user
    ADD CONSTRAINT fkx32ve2n84xbvvntxlyxsgijv FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2024-03-06 22:22:23

--
-- PostgreSQL database dump complete
--

