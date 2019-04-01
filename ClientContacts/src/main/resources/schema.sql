CREATE TABLE client (
    client_id       INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    company_name    varchar(50) NOT NULL,
    website_url     varchar(50) NOT NULL,    
    phone_number    varchar(20) NOT NULL,
    
    street_address  varchar(50) NOT NULL,
    city            varchar(50) NOT NULL,
    state           varchar(2) NOT NULL,
    zip_code        varchar(5) NOT NULL
);


CREATE TABLE contact (
    contact_id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email_address varchar(50) NOT NULL,
    street_address varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    state varchar(2) NOT NULL,
    zip_code varchar(5) NOT NULL,
    
    client_id integer REFERENCES client(client_id) NULL
);

    
