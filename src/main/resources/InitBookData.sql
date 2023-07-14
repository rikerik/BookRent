INSERT INTO books (title, author, description, rented, image_byte, image_base64, user_id)
VALUES ('Text', 'Dmitry Glukhovsky', 'asd', FALSE, pg_read_binary_file('BookWave\text.jpg')::bytea, '', 2),
       ('The Lord of the Ring', 'J.R.R. Tolkien', 'test', FALSE, pg_read_binary_file('BookWave\lotr1.jpg')::bytea, '', 2),
       ('Darknet', 'Stefan Mey', 'The digital underworld', FALSE, pg_read_binary_file('BookWave\darknet.jpg')::bytea, '', 2),
       ('Sapiens', 'Yuval Noah Harari', 'The short story of humanity', FALSE, pg_read_binary_file('BookWave\sapiens.jpg')::bytea, '', 2),
       ('Necronomicon', 'H.P Lovecraft', 'Existencial horror', FALSE, pg_read_binary_file('BookWave\necronomicon.jpg')::bytea, '', 2),
       ('Dune', 'Frank Herbert', 'Modern science fiction', FALSE, pg_read_binary_file('BookWave\dune.jpg')::bytea, '', 2),
       ('Roadside Picnic', 'Arkady and Boris Strugatsky', 'Stalker', FALSE, pg_read_binary_file('BookWave\stalker.jpg')::bytea, '', 2),
       ('Metro 2033', 'Dmitry Glukhovsky', 'underground', FALSE, pg_read_binary_file('BookWave\metro.jpg')::bytea, '', 2);
