INSERT INTO books (title, author, description, available, image_byte, image_base64)
VALUES ('Text', 'Dmitry Glukhovsky', 'asd', FALSE, pg_read_binary_file('BookWave\text.jpg')::bytea, ''),
       ('The Lord of the Ring', 'J.R.R. Tolkien', 'test', FALSE, pg_read_binary_file('BookWave\lotr1.jpg')::bytea, ''),
       ('Darknet', 'Stefan Mey', 'The digital underworld', FALSE, pg_read_binary_file('BookWave\darknet.jpg')::bytea, ''),
       ('Sapiens', 'Yuval Noah Harari', 'The short story of humanity', FALSE, pg_read_binary_file('BookWave\sapiens.jpg')::bytea, ''),
       ('Necronomicon', 'H.P Lovecraft', 'Existencial horror', FALSE, pg_read_binary_file('BookWave\necronomicon.jpg')::bytea, ''),
       ('Dune', 'Frank Herbert', 'Modern science fiction', FALSE, pg_read_binary_file('BookWave\dune.jpg')::bytea, ''),
       ('Roadside Picnic', 'Arkady and Boris Strugatsky', 'Stalker', FALSE, pg_read_binary_file('BookWave\stalker.jpg')::bytea, ''),
       ('Metro 2033', 'Dmitry Glukhovsky', 'underground', FALSE, pg_read_binary_file('BookWave\metro.jpg')::bytea, '');
