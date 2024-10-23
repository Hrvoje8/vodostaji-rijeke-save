--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- Name: vodostaji; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vodostaji (
    id integer NOT NULL,
    datum date NOT NULL,
    vrijeme time without time zone NOT NULL,
    stanica character varying(100) NOT NULL,
    vodostaj numeric(5,2) NOT NULL,
    temperatura_zraka numeric(4,2),
    vlaznost integer,
    tlak_zraka integer,
    oborine numeric(4,2),
    stanje_vremena character varying(100),
    vjetar json
);


ALTER TABLE public.vodostaji OWNER TO postgres;

--
-- Name: vodostaji_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vodostaji_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vodostaji_id_seq OWNER TO postgres;

--
-- Name: vodostaji_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vodostaji_id_seq OWNED BY public.vodostaji.id;


--
-- Name: vodostaji id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vodostaji ALTER COLUMN id SET DEFAULT nextval('public.vodostaji_id_seq'::regclass);


--
-- Data for Name: vodostaji; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vodostaji (id, datum, vrijeme, stanica, vodostaj, temperatura_zraka, vlaznost, tlak_zraka, oborine, stanje_vremena, vjetar) FROM stdin;
1	2024-10-22	11:00:00	Kobaš	312.00	10.60	76	1029	0.00	Vedro	{"smjer": "Jugozapad", "brzina": 1.00}
2	2024-10-22	14:00:00	Kobaš	313.00	21.00	49	1027	0.00	Vedro	{"smjer": "Istok", "brzina": 0.60}
3	2024-10-22	18:00:00	Kobaš	316.00	18.70	57	1028	0.00	Vedro	{"smjer": "Sjeveroistok", "brzina": 1.10}
4	2024-10-22	14:00:00	Zagreb	-130.00	18.90	70	1027	0.00	Vedro	{"smjer": "Jug", "brzina": 1.00}
5	2024-10-22	18:00:00	Zagreb	-164.00	16.90	84	1027	0.00	Vedro	{"smjer": "Sjever", "brzina": 1.30}
6	2024-10-23	04:00:00	Zagreb	-191.00	9.50	94	1031	0.00	Oblačno	{"smjer": "Sjeveroistok", "brzina": 0.40}
7	2024-10-22	14:00:00	Požega	12.00	20.90	81	1028	0.00	Vedro	{"smjer": "Istok", "brzina": 1.00}
8	2024-10-22	14:00:00	Bjelovar	22.00	21.40	58	1028	0.00	Vedro	{"smjer": "Jug", "brzina": 0.90}
9	2024-10-22	18:00:00	Bjelovar	22.00	19.00	72	1027	0.00	Vedro	{"smjer": "Sjeveroistok", "brzina": 0.30}
10	2024-10-23	11:00:00	Bjelovar	22.00	14.60	82	1032	0.00	Pretežno oblačno	{"smjer": "Sjeveroistok", "brzina": 1.60}
\.


--
-- Name: vodostaji_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vodostaji_id_seq', 1, false);


--
-- Name: vodostaji vodostaji_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vodostaji
    ADD CONSTRAINT vodostaji_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

