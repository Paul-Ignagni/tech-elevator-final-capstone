BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

-- DELETE LATER
INSERT INTO comic_info (comic_id, title, description, issueNumber,series,release_date, pageCount) VALUES (1, 'title', 'Description 1', 1, 'Series 1', 'Never', 100);
INSERT INTO comic_info (comic_id, title, description, issueNumber,series,release_date, pageCount) VALUES (2, 'title2', 'Description 2', 2, 'Series 2', 'Never', 20);
INSERT INTO comic_info (comic_id, title, description, issueNumber,series,release_date, pageCount) VALUES (3, 'title3', 'Description 3',  3, 'Series 3', 'Never', 150);

INSERT INTO collection (user_id, collection_name, isPublic) VALUES (1, 'Collection 1', false);
INSERT INTO collection (user_id, collection_name, isPublic) VALUES (2, 'Collection 2', true);

INSERT INTO collection_comic_info (collection_id, serial_number) VALUES (1, 2);
INSERT INTO collection_comic_info (collection_id, serial_number) VALUES (2, 1);
INSERT INTO collection_comic_info (collection_id, serial_number) VALUES (2, 3);

INSERT INTO creator (creator_id, name) VALUES (1, 'John');
INSERT INTO creator (creator_id, name) VALUES (2, 'Mike');
INSERT INTO creator (creator_id, name) VALUES (3, 'Sarah');

INSERT INTO comic_info_creator (serial_number, creator_serial) VALUES (1, 1);
INSERT INTO comic_info_creator (serial_number, creator_serial) VALUES (1, 2);
INSERT INTO comic_info_creator (serial_number, creator_serial) VALUES (2, 3);

INSERT INTO character (character_id, name, description) VALUES (1, 'Hulk', 'Hulk Smash');
INSERT INTO character (character_id, name, description) VALUES (2, 'Wolverine', 'Wolverine Smash');
INSERT INTO character (character_id, name, description) VALUES (3, 'Batman', 'Batman Smash');

INSERT INTO character_comic_info (serial_number, character_serial) VALUES (1, 1);
INSERT INTO character_comic_info (serial_number, character_serial) VALUES (1, 2);
INSERT INTO character_comic_info (serial_number, character_serial) VALUES (2, 3);




--

COMMIT TRANSACTION;
