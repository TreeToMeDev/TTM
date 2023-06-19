-- these columns are derived from the History table rather than being stored
-- directly inside the object

ALTER TABLE contact DROP COLUMN last_timestamp;
ALTER TABLE product DROP COLUMN last_timestamp;
ALTER TABLE purchase DROP COLUMN last_timestamp;

ALTER TABLE contact DROP COLUMN last_user;
ALTER TABLE product DROP COLUMN last_user;
ALTER TABLE purchase DROP COLUMN last_user;