BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE comic_info (
	serial_number SERIAL,
	comic_id INT,
	title varchar(250),
	description varchar(1200),
	issueNumber INT,
	series varchar(250),
	release_date varchar(20),
	pageCount INT,
	imageUrl varchar(200),
	CONSTRAINT PK_serial PRIMARY KEY (serial_number)
);

CREATE TABLE creator (
	creator_id SERIAL,
	name varchar(150) NOT NULL,
	CONSTRAINT PK_creator PRIMARY KEY (creator_id)
);

CREATE TABLE comic_info_creator (
	serial_number INT NOT NULL,
	creator_id INT NOT NULL,
	CONSTRAINT FK_serial FOREIGN KEY (serial_number) REFERENCES comic_info(serial_number),
	CONSTRAINT FK_creator FOREIGN KEY (creator_id) REFERENCES creator(creator_id)
);

CREATE TABLE character (
	character_id SERIAL,
	name varchar(100) NOT NULL,
	CONSTRAINT PK_character PRIMARY KEY (character_id)
);

CREATE TABLE character_comic_info (
	serial_number INT NOT NULL UNIQUE,
	character_id INT NOT NULL UNIQUE,
	CONSTRAINT FK_serial FOREIGN KEY (serial_number) REFERENCES comic_info(serial_number),
	CONSTRAINT FK_character FOREIGN KEY (character_id) REFERENCES character(character_id)
);

CREATE TABLE comic_book (
	comic_id SERIAL,
	serial_number INT NOT NULL,
	owner INT NOT NULL,
	current_holder INT NOT NULL,
	reminder DATE,
	condition INT NOT NULL,
	CONSTRAINT PK_comic PRIMARY KEY (comic_id),
	CONSTRAINT FK_serial FOREIGN KEY (serial_number) REFERENCES comic_info(serial_number),
	CONSTRAINT FK_owner FOREIGN KEY (owner) REFERENCES users(user_id),
	CONSTRAINT FK_holder FOREIGN KEY (current_holder) REFERENCES users(user_id)
);

CREATE TABLE collection (
	collection_id SERIAL,
	user_id INT NOT NULL,
	collection_name varchar(250) NOT NULL,
	isPublic BOOLEAN NOT NULL,
	CONSTRAINT PK_collection PRIMARY KEY (collection_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE collection_comic_info (
	collection_id INT NOT NULL,
	serial_number INT NOT NULL,
	CONSTRAINT FK_collection FOREIGN KEY (collection_id) REFERENCES collection(collection_id),
	CONSTRAINT FK_comic FOREIGN KEY (serial_number) REFERENCES comic_info(serial_number)
);

CREATE TABLE users_comic_info (
	entry_id INT NOT NULL UNIQUE,
	user_id INT NOT NULL,
	serial_number INT NOT NULL,
	rating INT,
	review varchar(500),
	read_status INT NOT NULL,
	CONSTRAINT PK_entry PRIMARY KEY (entry_id),
	CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_serial FOREIGN KEY (serial_number) REFERENCES comic_info(serial_number)
);
	

COMMIT TRANSACTION;
