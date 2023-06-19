DROP TABLE customer_communication;
DROP TABLE customer;
DROP TABLE agent_user;

ALTER TABLE customer_contact RENAME TO contact;

ALTER TABLE customer_product RENAME TO purchase;

ALTER SEQUENCE customer_contact_id_seq RENAME TO contact_id_seq;

ALTER SEQUENCE customer_product_id_seq RENAME TO purchase_id_seq;