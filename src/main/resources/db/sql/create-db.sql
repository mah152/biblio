--DROP TABLE biblio IF EXISTS;

CREATE TABLE biblio (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  author VARCHAR(255),
  title VARCHAR(255),
  year    INTEGER,
  journal VARCHAR(255),
  bibtexkey VARCHAR(255),
  pages VARCHAR(255),
  volume VARCHAR(255),
  number VARCHAR(255)
);
ALTER TABLE biblio ADD UNIQUE (author,title);
ALTER TABLE biblio ADD UNIQUE (bibtexkey);