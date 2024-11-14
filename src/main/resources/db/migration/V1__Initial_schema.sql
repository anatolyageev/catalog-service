CREATE TABLE book ( -- Definition of the book table
id BIGSERIAL PRIMARY KEY NOT NULL, -- Declares the id field as the primary key
author varchar(255) NOT NULL,
isbn varchar(255) UNIQUE NOT NULL, -- Constrains the isbn field to be unique
price float8 NOT NULL,
title varchar(255) NOT NULL,
created_date timestamp NOT NULL,
last_modified_date timestamp NOT NULL,
version integer NOT NULL
);