CREATE SEQUENCE IF NOT EXISTS books_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS books
(
    id bigint DEFAULT nextval('books_id_seq'::regclass) NOT NULL,
    isbn character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT books_pkey PRIMARY KEY (id),
    CONSTRAINT uk_kibbepcitr0a3cpk3rfr7nihn UNIQUE (isbn)
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