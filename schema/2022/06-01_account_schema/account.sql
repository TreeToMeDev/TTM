CREATE TABLE account (
	id SERIAL PRIMARY KEY,	
    account_type VARCHAR NOT NULL,
	billing_city VARCHAR NOT NULL,
    billing_country VARCHAR NOT NULL,
    billing_street VARCHAR NOT NULL,
    billing_postal_code VARCHAR NOT NULL,
    billing_province_state VARCHAR NOT NULL,
    currency VARCHAR NOT NULL,
    industry VARCHAR NOT NULL,
    language VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    shipping_city VARCHAR NOT NULL,
    shipping_country VARCHAR NOT NULL,
    shipping_postal_code VARCHAR NOT NULL,
    shipping_province_state VARCHAR NOT NULL,
    shipping_street VARCHAR NOT NULL,
    web_site VARCHAR NOT NULL
)
  