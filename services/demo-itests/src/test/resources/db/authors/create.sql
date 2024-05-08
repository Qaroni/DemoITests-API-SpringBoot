CREATE SEQUENCE IF NOT EXISTS authors_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS authors
(
    id bigint DEFAULT nextval('authors_id_seq'::regclass) NOT NULL,
    document_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    full_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authors_pkey PRIMARY KEY (id),
    CONSTRAINT uk_t4l3vhw95ibgcy2lwqo71jwyo UNIQUE (document_id)
);

CREATE TABLE IF NOT EXISTS public.book_author
(
    author_id bigint NOT NULL,
    book_id bigint NOT NULL,
    CONSTRAINT fka4wtxvct1y3awj9fdi83oxnci FOREIGN KEY (book_id)
        REFERENCES public.authors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkklw4jlsodeh7e7uks1d468b1j FOREIGN KEY (author_id)
        REFERENCES public.books (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);