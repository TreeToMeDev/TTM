CREATE TABLE deal_item
   (id SERIAL,
    deal_id INTEGER NOT NULL,
	price FLOAT NOT NULL,
    product_id INTEGER NOT NULL,
	quantity INTEGER NOT NULL);
	
CREATE INDEX ON deal_item (deal_id, id);