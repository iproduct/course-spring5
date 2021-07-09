DROP TABLE IF EXISTS public.posts;

DROP TABLE IF EXISTS public.users;

-- Table: public.users

CREATE TABLE public.users
(
    id SERIAL PRIMARY KEY,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    active boolean NOT NULL,
    created timestamp without time zone,
    modified timestamp without time zone,
    CONSTRAINT uk_username UNIQUE (username)
)

    TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;


-- Table: public.posts

CREATE TABLE public.posts
(
    id SERIAL PRIMARY KEY,
    title character varying(255) COLLATE pg_catalog."default",
    content text COLLATE pg_catalog."default" NOT NULL,
    author_id bigint NOT NULL,
    created timestamp without time zone,
    modified timestamp without time zone,
    CONSTRAINT fkpostsusersid FOREIGN KEY (author_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
)

    TABLESPACE pg_default;

ALTER TABLE public.posts
    OWNER to postgres;

