ALTER TABLE deal ADD COLUMN account_id INTEGER NOT NULL DEFAULT -1;
ALTER TABLE deal ADD COLUMN contact_id INTEGER NOT NULL DEFAULT -1;
ALTER TABLE deal ADD COLUMN due_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(); 
ALTER TABLE deal ADD COLUMN owner_user_id INTEGER NOT NULL DEFAULT -1;
ALTER TABLE deal ADD COLUMN stage TEXT NOT NULL DEFAULT 'Appointment Scheduled';