CREATE SCHEMA apl_restaurants AUTHORIZATION user;
DROP sequence IF EXISTS apl_restaurants.restaurant_id_seq;
--CREATE sequence apl_restaurants.restaurant_id_seq;

DROP TABLE IF EXISTS apl_restaurants.RESTAURANT CASCADE;
CREATE TABLE apl_restaurants.RESTAURANT
(
    ID int NOT NULL DEFAULT NEXTVAL('apl_restaurants.restaurant_id_seq'),
    NAME VARCHAR2(100) NOT NULL,
    ADDRESS VARCHAR2(100) NOT NULL,
    ZIPCODE VARCHAR2(5),
    CITY VARCHAR2(50),
    PROVINCE VARCHAR2(50),
    COUNTRY VARCHAR2(50),
    EMAIL VARCHAR2(100),
    PHONE VARCHAR2(15),
    WEBSITE VARCHAR2(120),
    DESCRIPTION VARCHAR2(200),
    FOOD_TYPE VARCHAR2(50),
    AVERAGE_AMOUNT DOUBLE,
    PRIMARY KEY(ID)

);
--ALTER sequence apl_restaurants.restaurant_id_seq OWNED BY apl_restaurants.RESTAURANT.ID;

DROP sequence IF EXISTS apl_restaurants.booking_id_seq;
--CREATE sequence apl_restaurants.booking_id_seq;

DROP TABLE IF EXISTS apl_restaurants.booking CASCADE;
CREATE TABLE apl_restaurants.booking
(
    ID int NOT NULL DEFAULT NEXTVAL('apl_restaurants.booking_id_seq'),
    NAME VARCHAR2(100) NOT NULL,
    CREATION_DATE TIMESTAMP NOT NULL,
    BOOKING_DATE TIMESTAMP NOT NULL,
    PEOPLE_NUMBER INT NOT NULL,
    AVERAGE_AMOUNT DOUBLE,
    EMAIL VARCHAR2(150),
    PHONE VARCHAR2(15) NOT NULL,
    COMMENTS VARCHAR2(500),
    RESTAURANT INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);
--ALTER sequence apl_restaurants.booking_id_seq OWNED BY apl_restaurants.booking.ID;

DROP sequence IF EXISTS apl_restaurants.review_id_seq;
--CREATE sequence apl_restaurants.review_id_seq;

DROP TABLE IF EXISTS apl_restaurants.review CASCADE;
CREATE TABLE apl_restaurants.review
(
    ID int NOT NULL DEFAULT NEXTVAL('apl_restaurants.review_id_seq'),
    CREATION_DATE TIMESTAMP NOT NULL,
    SENDER_NICK VARCHAR2(50) NOT NULL,
    STARS INT NOT NULL,
    TITLE VARCHAR2(150),
    DESCRIPTION VARCHAR2(500),
    RESTAURANT INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);
--ALTER sequence apl_restaurants.review_id_seq OWNED BY apl_restaurants.review.ID;

DROP sequence IF EXISTS apl_restaurants.menu_id_seq;
--CREATE sequence apl_restaurants.menu_id_seq;

DROP TABLE IF EXISTS apl_restaurants.menu CASCADE;
CREATE TABLE apl_restaurants.menu
(
    ID int NOT NULL DEFAULT NEXTVAL('apl_restaurants.menu_id_seq'),
    DESCRIPTION VARCHAR2(100) NOT NULL,
    AVAILABILITY VARCHAR2(50),
    PRIMARY KEY(ID)
);
--ALTER sequence apl_restaurants.menu_id_seq OWNED BY apl_restaurants.menu.ID;

DROP sequence IF EXISTS apl_restaurants.dish_id_seq;
--CREATE sequence apl_restaurants.dish_id_seq;

DROP TABLE IF EXISTS apl_restaurants.dish CASCADE;
CREATE TABLE apl_restaurants.dish
(
    ID int NOT NULL DEFAULT NEXTVAL('apl_restaurants.dish_id_seq'),
    PRICE DOUBLE NOT NULL,
    DESCRIPTION VARCHAR2(200),
    IS_VEGETARIAN BOOLEAN NOT NULL,
    PRIMARY KEY(ID)
);
--ALTER sequence apl_restaurants.dish_id_seq OWNED BY apl_restaurants.dish.ID;

DROP TABLE IF EXISTS apl_restaurants.MENU_IN_RESTAURANT CASCADE;
CREATE TABLE apl_restaurants.MENU_IN_RESTAURANT
(
    RESTAURANT_ID int NOT NULL,
    MENU_ID int NOT NULL,
    PRIMARY KEY(RESTAURANT_ID, MENU_ID),
    FOREIGN KEY (RESTAURANT_ID) REFERENCES apl_restaurants.RESTAURANT(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);

DROP TABLE IF EXISTS apl_restaurants.DISH_IN_MENU CASCADE;
CREATE TABLE apl_restaurants.DISH_IN_MENU
(
    MENU_ID int NOT NULL,
    DISH_ID int NOT NULL,
    PRIMARY KEY(DISH_ID, MENU_ID),
    FOREIGN KEY (DISH_ID) REFERENCES apl_restaurants.DISH(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);