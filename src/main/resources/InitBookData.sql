INSERT INTO books (title, author, description, available, image_byte, image_base64)
VALUES ('Text', 'Dmitry Glukhovsky', 'asd', TRUE, pg_read_binary_file('BookWave\text.jpg')::bytea, ''),
       ('The Lord of the Ring', 'J.R.R. Tolkien', 'test', TRUE, pg_read_binary_file('BookWave\lotr1.jpg')::bytea, '');


