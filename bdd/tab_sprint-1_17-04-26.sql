

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
-- Name: demandes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.demandes (
    id bigint NOT NULL,
    a_fourni_attestation_emploi boolean NOT NULL,
    a_fourni_autorisation_emploi boolean NOT NULL,
    a_fourni_carte_fiscale boolean NOT NULL,
    a_fourni_certificat_residence boolean NOT NULL,
    a_fourni_copie_carte_resident boolean NOT NULL,
    a_fourni_copie_passeport boolean NOT NULL,
    a_fourni_copie_visa boolean NOT NULL,
    a_fourni_demande_ministre boolean NOT NULL,
    a_fourni_extrait_casier_judiciaire boolean NOT NULL,
    a_fourni_extraitrc boolean NOT NULL,
    a_fourni_notice_renseignement boolean NOT NULL,
    a_fourni_photos boolean NOT NULL,
    a_fourni_statut_societe boolean NOT NULL,
    categorie_visa character varying(255),
    date_demande date,
    type_demande character varying(255),
    etat_civil_id bigint,
    visa_transformable_id bigint,
    CONSTRAINT demandes_categorie_visa_check CHECK (((categorie_visa)::text = ANY ((ARRAY['INVESTISSEUR'::character varying, 'TRAVAILLEUR'::character varying, 'ETUDIANT'::character varying, 'MISSIONNAIRE'::character varying])::text[]))),
    CONSTRAINT demandes_type_demande_check CHECK (((type_demande)::text = ANY ((ARRAY['NOUVEAU_TITRE'::character varying, 'RENOUVELLEMENT'::character varying, 'DUPLICATA'::character varying, 'TRANSFERT_VISA'::character varying])::text[])))
);


ALTER TABLE public.demandes OWNER TO postgres;

--
-- Name: demandes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.demandes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.demandes_id_seq OWNER TO postgres;

--
-- Name: demandes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.demandes_id_seq OWNED BY public.demandes.id;


--
-- Name: etat_civil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.etat_civil (
    id bigint NOT NULL,
    adresse_employeur character varying(255),
    date_naissance date,
    domicile_habituel character varying(255),
    employeur character varying(255),
    nationalite character varying(255),
    nom character varying(255),
    nom_jeune_fille character varying(255),
    prenoms character varying(255),
    profession character varying(255),
    situation_famille character varying(255)
);


ALTER TABLE public.etat_civil OWNER TO postgres;

--
-- Name: etat_civil_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.etat_civil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.etat_civil_id_seq OWNER TO postgres;

--
-- Name: etat_civil_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.etat_civil_id_seq OWNED BY public.etat_civil.id;


--
-- Name: visa_transformable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.visa_transformable (
    id bigint NOT NULL,
    date_delivrance date,
    date_expiration date,
    numero_visa character varying(255)
);


ALTER TABLE public.visa_transformable OWNER TO postgres;

--
-- Name: visa_transformable_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.visa_transformable_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.visa_transformable_id_seq OWNER TO postgres;

--
-- Name: visa_transformable_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.visa_transformable_id_seq OWNED BY public.visa_transformable.id;


--
-- Name: demandes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes ALTER COLUMN id SET DEFAULT nextval('public.demandes_id_seq'::regclass);


--
-- Name: etat_civil id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etat_civil ALTER COLUMN id SET DEFAULT nextval('public.etat_civil_id_seq'::regclass);


--
-- Name: visa_transformable id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visa_transformable ALTER COLUMN id SET DEFAULT nextval('public.visa_transformable_id_seq'::regclass);


--
-- Name: demandes demandes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes
    ADD CONSTRAINT demandes_pkey PRIMARY KEY (id);


--
-- Name: etat_civil etat_civil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.etat_civil
    ADD CONSTRAINT etat_civil_pkey PRIMARY KEY (id);


--
-- Name: demandes uk_6c6mw4g7odqhm7qbxsjci50wn; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes
    ADD CONSTRAINT uk_6c6mw4g7odqhm7qbxsjci50wn UNIQUE (visa_transformable_id);


--
-- Name: demandes uk_9lkksdawbj6lehrfok1gshjp6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes
    ADD CONSTRAINT uk_9lkksdawbj6lehrfok1gshjp6 UNIQUE (etat_civil_id);


--
-- Name: visa_transformable visa_transformable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.visa_transformable
    ADD CONSTRAINT visa_transformable_pkey PRIMARY KEY (id);


--
-- Name: demandes fkali88rxqda9ftng4yodsk9907; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes
    ADD CONSTRAINT fkali88rxqda9ftng4yodsk9907 FOREIGN KEY (etat_civil_id) REFERENCES public.etat_civil(id);


--
-- Name: demandes fkiacle55ffig8nfysqfyq0j4t4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.demandes
    ADD CONSTRAINT fkiacle55ffig8nfysqfyq0j4t4 FOREIGN KEY (visa_transformable_id) REFERENCES public.visa_transformable(id);


--
-- PostgreSQL database dump complete
--

