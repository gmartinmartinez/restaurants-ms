CREATE SCHEMA apl_restaurants AUTHORIZATION user;

DROP TABLE IF EXISTS apl_restaurants.RESTAURANT CASCADE;
CREATE TABLE apl_restaurants.RESTAURANT
(
    ID VARCHAR2(50) NOT NULL,
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

DROP TABLE IF EXISTS apl_restaurants.booking CASCADE;
CREATE TABLE apl_restaurants.booking
(
    ID VARCHAR2(50) NOT NULL,
    NAME VARCHAR2(100) NOT NULL,
    CREATION_DATE TIMESTAMP NOT NULL,
    BOOKING_DATE TIMESTAMP NOT NULL,
    PEOPLE_NUMBER INT NOT NULL,
    AVERAGE_AMOUNT DOUBLE,
    EMAIL VARCHAR2(150),
    PHONE VARCHAR2(15) NOT NULL,
    COMMENTS VARCHAR2(500),
    USER VARCHAR2(50) NOT NULL DEFAUL 'ADMIN',
    RESTAURANT INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);

DROP TABLE IF EXISTS apl_restaurants.review CASCADE;
CREATE TABLE apl_restaurants.review
(
    ID VARCHAR2(50) NOT NULL,
    CREATION_DATE TIMESTAMP NOT NULL,
    SENDER_NICK VARCHAR2(50) NOT NULL,
    STARS INT NOT NULL,
    TITLE VARCHAR2(150),
    DESCRIPTION VARCHAR2(500),
    RESTAURANT INT NOT NULL,
    USER VARCHAR2(50) NOT NULL DEFAUL 'ADMIN',
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);


DROP TABLE IF EXISTS apl_restaurants.menu CASCADE;
CREATE TABLE apl_restaurants.menu
(
    ID VARCHAR2(50) NOT NULL,
    DESCRIPTION VARCHAR2(100) NOT NULL,
    AVAILABILITY VARCHAR2(50),
    PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS apl_restaurants.dish CASCADE;
CREATE TABLE apl_restaurants.dish
(
    ID VARCHAR2(50) NOT NULL,
    PRICE DOUBLE NOT NULL,
    DESCRIPTION VARCHAR2(200),
    IS_VEGETARIAN BOOLEAN NOT NULL,
    PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS apl_restaurants.MENU_IN_RESTAURANT CASCADE;
CREATE TABLE apl_restaurants.MENU_IN_RESTAURANT
(
    RESTAURANT_ID VARCHAR2(50) NOT NULL,
    MENU_ID VARCHAR2(50) NOT NULL,
    PRIMARY KEY(RESTAURANT_ID, MENU_ID),
    FOREIGN KEY (RESTAURANT_ID) REFERENCES apl_restaurants.RESTAURANT(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);

DROP TABLE IF EXISTS apl_restaurants.DISH_IN_MENU CASCADE;
CREATE TABLE apl_restaurants.DISH_IN_MENU
(
    MENU_ID VARCHAR2(50) NOT NULL,
    DISH_ID VARCHAR2(50) NOT NULL,
    PRIMARY KEY(DISH_ID, MENU_ID),
    FOREIGN KEY (DISH_ID) REFERENCES apl_restaurants.DISH(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);


insert into apl_restaurants.RESTAURANT (ID, name, address)
    values ('e51e8252-ac48-4c4e-8c03-d1803fe4b1a2', 'Restaurant Cool', 'Local Street, 44');


insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a2', 3.0, 'Draft Beer', yes);
insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a3', 2.0, 'Zero Beer', yes);
insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a4', 2.5, 'Coke', yes);

insert into apl_restaurants.menu (ID, description)
    values ('e51e8252-ac48-4c4e-8a32-d1803fe4bsa2', 'Beverages');

insert into apl_restaurants.MENU_IN_RESTAURANT (RESTAURANT_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8c03-d1803fe4b1a2', 'e51e8252-ac48-4c4e-8a32-d1803fe4bsa2');


insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a2', 'e51e8252-ac48-4c4e-8a32-d1803fe4bsa2');
insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a3', 'e51e8252-ac48-4c4e-8a32-d1803fe4bsa2');
insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a4', 'e51e8252-ac48-4c4e-8a32-d1803fe4bsa2');