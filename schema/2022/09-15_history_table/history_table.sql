CREATE TABLE history (
	id SERIAL,
	account_id INT NOT NULL,
	action TEXT NOT NULL,
	contact_id INT NOT NULL,
	field_name TEXT NOT NULL,
	record_id INT NOT NULL,
	new_value  TEXT NOT NULL,
	old_value TEXT NOT NULL,
	table_name TEXT NOT NULL,
	timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
	user_id TEXT NOT NULL
);

CREATE INDEX ON history (account_id, timestamp DESC);

CREATE INDEX ON history (contact_id, timestamp DESC);

CREATE INDEX ON history (table_name, timestamp DESC);
