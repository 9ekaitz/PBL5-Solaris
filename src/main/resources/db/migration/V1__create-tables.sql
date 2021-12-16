-- solaris.privilege definition

CREATE TABLE privilege (
	id bigserial NOT NULL,
	code varchar(64) NOT NULL,
	enabled bool NOT NULL,
	i18n varchar(64) NOT NULL,
	"version" int4 NULL,
	CONSTRAINT privilege_pkey PRIMARY KEY (id)
);


-- solaris."role" definition

CREATE TABLE "role" (
	id bigserial NOT NULL,
	enabled bool NOT NULL,
	"name" varchar(255) NOT NULL,
	"version" int4 NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


-- solaris.privilege_roles definition
CREATE TABLE privilege_roles (
	privilege_id int8 NOT NULL,
	roles_id int8 NOT NULL,
	CONSTRAINT privilege_roles_pkey PRIMARY KEY (privilege_id, roles_id)
);

-- solaris.privilege_roles foreign keys

ALTER TABLE solaris.privilege_roles ADD CONSTRAINT fkccc4pc7ebxwy4j38gx1da29ej FOREIGN KEY (roles_id) REFERENCES "role"(id);
ALTER TABLE solaris.privilege_roles ADD CONSTRAINT fkwgtpsj4utv0tfmpy9yamb2gh FOREIGN KEY (privilege_id) REFERENCES privilege(id);


-- solaris.role_privileges definition

CREATE TABLE role_privileges (
	role_id int8 NOT NULL,
	privileges_id int8 NOT NULL,
	CONSTRAINT role_privileges_pkey PRIMARY KEY (role_id, privileges_id)
);

-- solaris.role_privileges foreign keys

ALTER TABLE solaris.role_privileges ADD CONSTRAINT fkas5s9i1itvr8tgocse4xmtwox FOREIGN KEY (privileges_id) REFERENCES privilege(id);
ALTER TABLE solaris.role_privileges ADD CONSTRAINT fkgelpp2j5e63axp7bcguwaqec5 FOREIGN KEY (role_id) REFERENCES "role"(id);


-- solaris.solar_panel_model definition

CREATE TABLE solar_panel_model (
	id bigserial NOT NULL,
	code varchar(255) NOT NULL,
	i18n varchar(255) NOT NULL,
	"version" int4 NULL,
	CONSTRAINT solar_panel_model_pkey PRIMARY KEY (id)
);


-- solaris."user" definition

CREATE TABLE "user" (
	id bigserial NOT NULL,
	enabled bool NULL,
	first_surname varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	second_surname varchar(255) NULL,
	username varchar(255) NULL,
	"version" int4 NULL,
	role_id int8 NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);

-- solaris."user" foreign keys

ALTER TABLE solaris."user" ADD CONSTRAINT fkdl9dqp078pc03g6kdnxmnlqpc FOREIGN KEY (role_id) REFERENCES "role"(id);


-- solaris.product definition

CREATE TABLE product (
	id bigserial NOT NULL,
	"version" int4 NULL,
	solar_panel_model_id int8 NULL,
	user_id int8 NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);

-- solaris.product foreign keys

ALTER TABLE solaris.product ADD CONSTRAINT fk8woy5eabefvh6ytbe94bntat FOREIGN KEY (user_id) REFERENCES "user"(id);
ALTER TABLE solaris.product ADD CONSTRAINT fk9029m3po4huebt4gqu1jr00fy FOREIGN KEY (solar_panel_model_id) REFERENCES solar_panel_model(id);


-- solaris.solar_panel definition

CREATE TABLE solar_panel (
	id bigserial NOT NULL,
	"version" int4 NULL,
	model_id int8 NULL,
	user_id int8 NULL,
	CONSTRAINT solar_panel_pkey PRIMARY KEY (id)
);

-- solaris.solar_panel foreign keys

ALTER TABLE solaris.solar_panel ADD CONSTRAINT fkc8aduufam50nw6yjd9cotllo2 FOREIGN KEY (user_id) REFERENCES "user"(id);
ALTER TABLE solaris.solar_panel ADD CONSTRAINT fkg543640wtorylcc631j2c2idp FOREIGN KEY (model_id) REFERENCES solar_panel_model(id);
