DROP TABLE IF EXISTS email;

CREATE TABLE email (
	body VARCHAR NOT NULL,
	contact_id INTEGER NOT NULL,
	id SERIAL,
	recipient VARCHAR NOT NULL,
	subject VARCHAR NOT NULL,
	timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
	user_id INTEGER NOT NULL
);

CREATE INDEX ON email (contact_id, timestamp);