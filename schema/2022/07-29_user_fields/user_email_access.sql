ALTER TABLE app_user ADD COLUMN email TEXT NOT NULL DEFAULT '';
ALTER TABLE app_user ADD COLUMN access_users BOOLEAN NOT NULL DEFAULT false;

-- these are the 'owners' of the contact or account, from the user table
-- user ID (from users table) should be setup as FK CONSTRAINT but DD said not to do that, so... don't
ALTER TABLE customer_contact ADD COLUMN owner_user_id INTEGER NOT NULL DEFAULT -1;
ALTER TABLE account ADD COLUMN owner_user_id INTEGER NOT NULL DEFAULT -1;