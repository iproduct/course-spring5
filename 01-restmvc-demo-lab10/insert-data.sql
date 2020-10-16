--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 12.4

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
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.post (id, author, content, created, image_url, modified, title) FROM stdin;
1	Trayan Iiliev	!!! Webflux provides reactive and non-blocking web service implemntation ...	2020-10-15 14:33:26.824	https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg	2020-10-15 14:33:26.824	New in Spring Boot and Spring
2	Trayan Iiliev	!!! Webflux provides reactive and non-blocking web service implemntation ...	2020-10-15 14:33:28.007	https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg	2020-10-15 14:33:28.007	New in Spring Boot and Spring
3	Trayan Iiliev	!!! Webflux provides reactive and non-blocking web service implemntation ...	2020-10-15 14:33:28.513	https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg	2020-10-15 14:33:28.513	New in Spring Boot and Spring
\.


--
-- Data for Name: post_keywords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.post_keywords (post_id, keywords) FROM stdin;
1	Spring
1	WebFlux
2	Spring
2	WebFlux
3	Spring
3	WebFlux
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, active, created, email, first_name, image_url, last_name, modified, password, username) FROM stdin;
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, roles) FROM stdin;
\.


--
-- PostgreSQL database dump complete
--

