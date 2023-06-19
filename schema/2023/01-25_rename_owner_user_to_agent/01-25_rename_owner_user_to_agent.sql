ALTER TABLE account RENAME COLUMN owner_user_id TO agent_id;
ALTER TABLE contact RENAME COLUMN owner_user_id TO agent_id;
ALTER TABLE deal RENAME COLUMN owner_user_id TO agent_id;
