INSERT INTO books (title, author, description, available, image_byte, image_base64)
VALUES ('Text', 'Dmitry Glukhovsky', 'asd', TRUE, pg_read_binary_file('BookWave\text.jpg')::bytea, '');


