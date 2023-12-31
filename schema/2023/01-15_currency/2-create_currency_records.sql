SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3626 (class 0 OID 49216)
-- Dependencies: 238
-- Data for Name: currency; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.currency (code, description) VALUES ('﻿AED', 'UAE Dirham');
INSERT INTO public.currency (code, description) VALUES ('AFN', 'Afghani');
INSERT INTO public.currency (code, description) VALUES ('ALL', 'Lek');
INSERT INTO public.currency (code, description) VALUES ('AMD', 'Armenian Dram');
INSERT INTO public.currency (code, description) VALUES ('ANG', 'Netherlands Antillean Guilder');
INSERT INTO public.currency (code, description) VALUES ('AOA', 'Kwanza');
INSERT INTO public.currency (code, description) VALUES ('ARS', 'Argentine Peso');
INSERT INTO public.currency (code, description) VALUES ('AUD', 'Australian Dollar');
INSERT INTO public.currency (code, description) VALUES ('AWG', 'Aruban Florin');
INSERT INTO public.currency (code, description) VALUES ('AZN', 'Azerbaijan Manat');
INSERT INTO public.currency (code, description) VALUES ('BAM', 'Convertible Mark');
INSERT INTO public.currency (code, description) VALUES ('BBD', 'Barbados Dollar');
INSERT INTO public.currency (code, description) VALUES ('BDT', 'Taka');
INSERT INTO public.currency (code, description) VALUES ('BGN', 'Bulgarian Lev');
INSERT INTO public.currency (code, description) VALUES ('BHD', 'Bahraini Dinar');
INSERT INTO public.currency (code, description) VALUES ('BIF', 'Burundi Franc');
INSERT INTO public.currency (code, description) VALUES ('BMD', 'Bermudian Dollar');
INSERT INTO public.currency (code, description) VALUES ('BND', 'Brunei Dollar');
INSERT INTO public.currency (code, description) VALUES ('BOB', 'Boliviano');
INSERT INTO public.currency (code, description) VALUES ('BOV', 'Mvdol');
INSERT INTO public.currency (code, description) VALUES ('BRL', 'Brazilian Real');
INSERT INTO public.currency (code, description) VALUES ('BSD', 'Bahamian Dollar');
INSERT INTO public.currency (code, description) VALUES ('BTN', 'Ngultrum');
INSERT INTO public.currency (code, description) VALUES ('BWP', 'Pula');
INSERT INTO public.currency (code, description) VALUES ('BYN', 'Belarusian Ruble');
INSERT INTO public.currency (code, description) VALUES ('BZD', 'Belize Dollar');
INSERT INTO public.currency (code, description) VALUES ('CAD', 'Canadian Dollar');
INSERT INTO public.currency (code, description) VALUES ('CDF', 'Congolese Franc');
INSERT INTO public.currency (code, description) VALUES ('CHF', 'Swiss Franc');
INSERT INTO public.currency (code, description) VALUES ('CHW', 'WIR Franc');
INSERT INTO public.currency (code, description) VALUES ('CLF', 'Unidad de Fomento');
INSERT INTO public.currency (code, description) VALUES ('CLP', 'Chilean Peso');
INSERT INTO public.currency (code, description) VALUES ('CNY', 'Yuan Renminbi');
INSERT INTO public.currency (code, description) VALUES ('COP', 'Colombian Peso');
INSERT INTO public.currency (code, description) VALUES ('COU', 'Unidad de Valor Real');
INSERT INTO public.currency (code, description) VALUES ('CRC', 'Costa Rican Colon');
INSERT INTO public.currency (code, description) VALUES ('CUC', 'Peso Convertible');
INSERT INTO public.currency (code, description) VALUES ('CUP', 'Cuban Peso');
INSERT INTO public.currency (code, description) VALUES ('CVE', 'Cabo Verde Escudo');
INSERT INTO public.currency (code, description) VALUES ('CZK', 'Czech Koruna');
INSERT INTO public.currency (code, description) VALUES ('DJF', 'Djibouti Franc');
INSERT INTO public.currency (code, description) VALUES ('DKK', 'Danish Krone');
INSERT INTO public.currency (code, description) VALUES ('DOP', 'Dominican Peso');
INSERT INTO public.currency (code, description) VALUES ('DZD', 'Algerian Dinar');
INSERT INTO public.currency (code, description) VALUES ('EGP', 'Egyptian Pound');
INSERT INTO public.currency (code, description) VALUES ('ERN', 'Nakfa');
INSERT INTO public.currency (code, description) VALUES ('ETB', 'Ethiopian Birr');
INSERT INTO public.currency (code, description) VALUES ('EUR', 'Euro');
INSERT INTO public.currency (code, description) VALUES ('FJD', 'Fiji Dollar');
INSERT INTO public.currency (code, description) VALUES ('FKP', 'Falkland Islands Pound');
INSERT INTO public.currency (code, description) VALUES ('GBP', 'Pound Sterling');
INSERT INTO public.currency (code, description) VALUES ('GEL', 'Lari');
INSERT INTO public.currency (code, description) VALUES ('GHS', 'Ghana Cedi');
INSERT INTO public.currency (code, description) VALUES ('GIP', 'Gibraltar Pound');
INSERT INTO public.currency (code, description) VALUES ('GMD', 'Dalasi');
INSERT INTO public.currency (code, description) VALUES ('GNF', 'Guinean Franc');
INSERT INTO public.currency (code, description) VALUES ('GTQ', 'Quetzal');
INSERT INTO public.currency (code, description) VALUES ('GYD', 'Guyana Dollar');
INSERT INTO public.currency (code, description) VALUES ('HKD', 'Hong Kong Dollar');
INSERT INTO public.currency (code, description) VALUES ('HNL', 'Lempira');
INSERT INTO public.currency (code, description) VALUES ('HTG', 'Gourde');
INSERT INTO public.currency (code, description) VALUES ('HUF', 'Forint');
INSERT INTO public.currency (code, description) VALUES ('IDR', 'Rupiah');
INSERT INTO public.currency (code, description) VALUES ('ILS', 'New Israeli Sheqel');
INSERT INTO public.currency (code, description) VALUES ('INR', 'Indian Rupee');
INSERT INTO public.currency (code, description) VALUES ('IQD', 'Iraqi Dinar');
INSERT INTO public.currency (code, description) VALUES ('IRR', 'Iranian Rial');
INSERT INTO public.currency (code, description) VALUES ('ISK', 'Iceland Krona');
INSERT INTO public.currency (code, description) VALUES ('JMD', 'Jamaican Dollar');
INSERT INTO public.currency (code, description) VALUES ('JOD', 'Jordanian Dinar');
INSERT INTO public.currency (code, description) VALUES ('JPY', 'Yen');
INSERT INTO public.currency (code, description) VALUES ('KES', 'Kenyan Shilling');
INSERT INTO public.currency (code, description) VALUES ('KGS', 'Som');
INSERT INTO public.currency (code, description) VALUES ('KHR', 'Riel');
INSERT INTO public.currency (code, description) VALUES ('KMF', 'Comorian Franc ');
INSERT INTO public.currency (code, description) VALUES ('KPW', 'North Korean Won');
INSERT INTO public.currency (code, description) VALUES ('KRW', 'Won');
INSERT INTO public.currency (code, description) VALUES ('KWD', 'Kuwaiti Dinar');
INSERT INTO public.currency (code, description) VALUES ('KYD', 'Cayman Islands Dollar');
INSERT INTO public.currency (code, description) VALUES ('KZT', 'Tenge');
INSERT INTO public.currency (code, description) VALUES ('LAK', 'Lao Kip');
INSERT INTO public.currency (code, description) VALUES ('LBP', 'Lebanese Pound');
INSERT INTO public.currency (code, description) VALUES ('LKR', 'Sri Lanka Rupee');
INSERT INTO public.currency (code, description) VALUES ('LRD', 'Liberian Dollar');
INSERT INTO public.currency (code, description) VALUES ('LSL', 'Loti');
INSERT INTO public.currency (code, description) VALUES ('LYD', 'Libyan Dinar');
INSERT INTO public.currency (code, description) VALUES ('MAD', 'Moroccan Dirham');
INSERT INTO public.currency (code, description) VALUES ('MDL', 'Moldovan Leu');
INSERT INTO public.currency (code, description) VALUES ('MGA', 'Malagasy Ariary');
INSERT INTO public.currency (code, description) VALUES ('MKD', 'Denar');
INSERT INTO public.currency (code, description) VALUES ('MMK', 'Kyat');
INSERT INTO public.currency (code, description) VALUES ('MNT', 'Tugrik');
INSERT INTO public.currency (code, description) VALUES ('MOP', 'Pataca');
INSERT INTO public.currency (code, description) VALUES ('MRU', 'Ouguiya');
INSERT INTO public.currency (code, description) VALUES ('MUR', 'Mauritius Rupee');
INSERT INTO public.currency (code, description) VALUES ('MVR', 'Rufiyaa');
INSERT INTO public.currency (code, description) VALUES ('MWK', 'Malawi Kwacha');
INSERT INTO public.currency (code, description) VALUES ('MXN', 'Mexican Peso');
INSERT INTO public.currency (code, description) VALUES ('MXV', 'Mexican Unidad de Inversion (UDI)');
INSERT INTO public.currency (code, description) VALUES ('MYR', 'Malaysian Ringgit');
INSERT INTO public.currency (code, description) VALUES ('MZN', 'Mozambique Metical');
INSERT INTO public.currency (code, description) VALUES ('NAD', 'Namibia Dollar');
INSERT INTO public.currency (code, description) VALUES ('NGN', 'Naira');
INSERT INTO public.currency (code, description) VALUES ('NIO', 'Cordoba Oro');
INSERT INTO public.currency (code, description) VALUES ('NOK', 'Norwegian Krone');
INSERT INTO public.currency (code, description) VALUES ('NPR', 'Nepalese Rupee');
INSERT INTO public.currency (code, description) VALUES ('NZD', 'New Zealand Dollar');
INSERT INTO public.currency (code, description) VALUES ('OMR', 'Rial Omani');
INSERT INTO public.currency (code, description) VALUES ('PAB', 'Balboa');
INSERT INTO public.currency (code, description) VALUES ('PEN', 'Sol');
INSERT INTO public.currency (code, description) VALUES ('PGK', 'Kina');
INSERT INTO public.currency (code, description) VALUES ('PHP', 'Philippine Peso');
INSERT INTO public.currency (code, description) VALUES ('PKR', 'Pakistan Rupee');
INSERT INTO public.currency (code, description) VALUES ('PLN', 'Zloty');
INSERT INTO public.currency (code, description) VALUES ('PYG', 'Guarani');
INSERT INTO public.currency (code, description) VALUES ('QAR', 'Qatari Rial');
INSERT INTO public.currency (code, description) VALUES ('RON', 'Romanian Leu');
INSERT INTO public.currency (code, description) VALUES ('RSD', 'Serbian Dinar');
INSERT INTO public.currency (code, description) VALUES ('RUB', 'Russian Ruble');
INSERT INTO public.currency (code, description) VALUES ('RWF', 'Rwanda Franc');
INSERT INTO public.currency (code, description) VALUES ('SAR', 'Saudi Riyal');
INSERT INTO public.currency (code, description) VALUES ('SBD', 'Solomon Islands Dollar');
INSERT INTO public.currency (code, description) VALUES ('SCR', 'Seychelles Rupee');
INSERT INTO public.currency (code, description) VALUES ('SDG', 'Sudanese Pound');
INSERT INTO public.currency (code, description) VALUES ('SEK', 'Swedish Krona');
INSERT INTO public.currency (code, description) VALUES ('SGD', 'Singapore Dollar');
INSERT INTO public.currency (code, description) VALUES ('SHP', 'Saint Helena Pound');
INSERT INTO public.currency (code, description) VALUES ('SLE', 'Leone');
INSERT INTO public.currency (code, description) VALUES ('SLL', 'Leone');
INSERT INTO public.currency (code, description) VALUES ('SOS', 'Somali Shilling');
INSERT INTO public.currency (code, description) VALUES ('SRD', 'Surinam Dollar');
INSERT INTO public.currency (code, description) VALUES ('SSP', 'South Sudanese Pound');
INSERT INTO public.currency (code, description) VALUES ('STN', 'Dobra');
INSERT INTO public.currency (code, description) VALUES ('SVC', 'El Salvador Colon');
INSERT INTO public.currency (code, description) VALUES ('SYP', 'Syrian Pound');
INSERT INTO public.currency (code, description) VALUES ('SZL', 'Lilangeni');
INSERT INTO public.currency (code, description) VALUES ('THB', 'Baht');
INSERT INTO public.currency (code, description) VALUES ('TJS', 'Somoni');
INSERT INTO public.currency (code, description) VALUES ('TMT', 'Turkmenistan New Manat');
INSERT INTO public.currency (code, description) VALUES ('TND', 'Tunisian Dinar');
INSERT INTO public.currency (code, description) VALUES ('TOP', 'Pa’anga');
INSERT INTO public.currency (code, description) VALUES ('TRY', 'Turkish Lira');
INSERT INTO public.currency (code, description) VALUES ('TTD', 'Trinidad and Tobago Dollar');
INSERT INTO public.currency (code, description) VALUES ('TWD', 'New Taiwan Dollar');
INSERT INTO public.currency (code, description) VALUES ('TZS', 'Tanzanian Shilling');
INSERT INTO public.currency (code, description) VALUES ('UAH', 'Hryvnia');
INSERT INTO public.currency (code, description) VALUES ('UGX', 'Uganda Shilling');
INSERT INTO public.currency (code, description) VALUES ('USD', 'US Dollar');
INSERT INTO public.currency (code, description) VALUES ('USN', 'US Dollar (Next day)');
INSERT INTO public.currency (code, description) VALUES ('UYI', 'Uruguay Peso en Unidades Indexadas (UI)');
INSERT INTO public.currency (code, description) VALUES ('UYU', 'Peso Uruguayo');
INSERT INTO public.currency (code, description) VALUES ('UYW', 'Unidad Previsional');
INSERT INTO public.currency (code, description) VALUES ('UZS', 'Uzbekistan Sum');
INSERT INTO public.currency (code, description) VALUES ('VED', 'Bolívar Soberano');
INSERT INTO public.currency (code, description) VALUES ('VES', 'Bolívar Soberano');
INSERT INTO public.currency (code, description) VALUES ('VND', 'Dong');
INSERT INTO public.currency (code, description) VALUES ('VUV', 'Vatu');
INSERT INTO public.currency (code, description) VALUES ('WST', 'Tala');
INSERT INTO public.currency (code, description) VALUES ('XAF', 'CFA Franc BEAC');
INSERT INTO public.currency (code, description) VALUES ('XCD', 'East Caribbean Dollar');
INSERT INTO public.currency (code, description) VALUES ('XOF', 'CFA Franc BCEAO');
INSERT INTO public.currency (code, description) VALUES ('XPF', 'CFP Franc');
INSERT INTO public.currency (code, description) VALUES ('YER', 'Yemeni Rial');
INSERT INTO public.currency (code, description) VALUES ('ZAR', 'Rand');
INSERT INTO public.currency (code, description) VALUES ('ZMW', 'Zambian Kwacha');
INSERT INTO public.currency (code, description) VALUES ('ZWL', 'Zimbabwe Dollar');

