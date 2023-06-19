CREATE TYPE task_status AS ENUM ('OPEN', 'COMPLETE');

CREATE TABLE task
   (id SERIAL,
	owner_user_id INTEGER NOT NULL DEFAULT -1,
	due_date TIMESTAMP WITH TIME ZONE,
	contact_id INTEGER NOT NULL DEFAULT -1,
	account_id INTEGER NOT NULL DEFAULT -1,
	status task_status NOT NULL DEFAULT 'OPEN',
	description TEXT NOT NULL DEFAULT '',
	notes TEXT NOT NULL DEFAULT '');
	
CREATE INDEX ON task (due_date, id);