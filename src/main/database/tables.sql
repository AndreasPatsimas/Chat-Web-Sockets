-- public.authorities definition

-- Drop table

-- DROP TABLE public.authorities;

CREATE TABLE public.authorities (
	id serial4 NOT NULL,
	description varchar(50) NOT NULL,
	"comments" varchar(4000) NULL,
	record_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT authorities__pk PRIMARY KEY (id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id serial4 NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	"password" varchar(100) NOT NULL,
	active int4 NOT NULL,
	"comments" varchar(4000) NULL,
	record_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT users__pk PRIMARY KEY (id),
	CONSTRAINT users_un UNIQUE (email)
);


-- public.messages definition

-- Drop table

-- DROP TABLE public.messages;

CREATE TABLE public.messages (
	id serial4 NOT NULL,
	sender_id int4 NOT NULL,
	receiver_id int4 NOT NULL,
	"content" varchar NULL,
	"comments" varchar(131072) NULL,
	recorddate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pkmessage PRIMARY KEY (id),
	CONSTRAINT fkreceiver FOREIGN KEY (receiver_id) REFERENCES public.users(id) ON DELETE CASCADE,
	CONSTRAINT fksender FOREIGN KEY (sender_id) REFERENCES public.users(id) ON DELETE CASCADE
);


-- public.user_authorities definition

-- Drop table

-- DROP TABLE public.user_authorities;

CREATE TABLE public.user_authorities (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	authority_id int4 NOT NULL,
	"comments" varchar(131072) NULL,
	recorddate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pkusers_authorities PRIMARY KEY (id),
	CONSTRAINT user_authorities_un UNIQUE (user_id, authority_id),
	CONSTRAINT fkauthority FOREIGN KEY (authority_id) REFERENCES public.authorities(id) ON DELETE CASCADE,
	CONSTRAINT fkuser FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);