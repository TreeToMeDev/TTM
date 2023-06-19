-- Given a userID, return a list of that userID plus all the userID's in the
-- hierarchy that are *below* them. The user who is input as root_id can see any
-- records that have ID's that are in this list.

-- example: Fran is Manager, Mary is Employee, Jane is Mary's employee.
-- if you query 'Fran' you get back 'Fran,Mary,Jane'
-- if you query 'Mary' you get back 'Mary,Jane'
-- if you query 'Jane' you get back 'Jane'

-- based on https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-recursive-query/

-- 2023-02-22: updated function to get parent_id associated with the root_id, if it's -1, 
-- return all ID's. A -1 indicates an admin-type user who has access to everything.

CREATE OR REPLACE FUNCTION get_accessible_ids (root_id INT) 
	RETURNS TABLE (accessible_id INT)
AS $$
DECLARE
	parent_id INT;
BEGIN 
	SELECT app_user.parent_id INTO parent_id FROM app_user WHERE id = root_id;
	IF parent_id = -1 THEN
		RETURN QUERY SELECT id FROM app_user;
	ELSE
		RETURN QUERY SELECT * FROM get_accessible_ids_recursive(root_id);
	END IF;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_accessible_ids_recursive (root_id INT) 
	RETURNS TABLE (accessible_id INT)
AS $$
BEGIN 
	RETURN QUERY
	WITH RECURSIVE agent_list AS 
	(SELECT id FROM app_user
		WHERE id = root_id
		UNION SELECT u.id
		FROM app_user u
		INNER JOIN agent_list al ON al.id = u.parent_id)
	SELECT * FROM agent_list;
END; $$
LANGUAGE 'plpgsql';

-- example usage:
-- '50' is the ID of the APP_USER who is running the query
-- SELECT * FROM contact WHERE agent_id IN (SELECT get_accessible_ids(50));