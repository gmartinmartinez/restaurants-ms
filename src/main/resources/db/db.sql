DROP TABLE IF EXISTS apl_restaurants.booking;
DROP TABLE IF EXISTS apl_restaurants.review;
DROP TABLE IF EXISTS apl_restaurants.DISH_IN_MENU;
DROP TABLE IF EXISTS apl_restaurants.MENU_IN_RESTAURANT;
DROP TABLE IF EXISTS apl_restaurants.dish;
DROP TABLE IF EXISTS apl_restaurants.menu;

DROP TABLE IF EXISTS apl_restaurants.RESTAURANT;

CREATE TABLE apl_restaurants.RESTAURANT
(
    ID VARCHAR(50) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    ADDRESS VARCHAR(100) NOT NULL,
    ZIPCODE VARCHAR(5),
    CITY VARCHAR(50),
    PROVINCE VARCHAR(50),
    COUNTRY VARCHAR(50),
    EMAIL VARCHAR(100),
    PHONE VARCHAR(15),
    WEBSITE VARCHAR(120),
    DESCRIPTION VARCHAR(200),
    FOOD_TYPE VARCHAR(50),
    AVERAGE_AMOUNT FLOAT,
    USER_ROLE VARCHAR(50) NOT NULL,
    USER_NAME VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID)

);

CREATE TABLE apl_restaurants.booking
(
    ID VARCHAR(50) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    CREATION_DATE DATETIME NOT NULL,
    BOOKING_DATE DATETIME NOT NULL,
    PEOPLE_NUMBER INT NOT NULL,
    AVERAGE_AMOUNT FLOAT,
    EMAIL VARCHAR(150),
    PHONE VARCHAR(15) NOT NULL,
    COMMENTS VARCHAR(500),
    USER_ROLE VARCHAR(50) NOT NULL,
    USER_NAME VARCHAR(50) NOT NULL,
    RESTAURANT VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);

CREATE TABLE apl_restaurants.review
(
    ID VARCHAR(50) NOT NULL,
    CREATION_DATE DATETIME NOT NULL,
    SENDER_NICK VARCHAR(50) NOT NULL,
    STARS INT NOT NULL,
    TITLE VARCHAR(150),
    DESCRIPTION VARCHAR(500),
    RESTAURANT VARCHAR(50) NOT NULL,
    USER_ROLE VARCHAR(50) NOT NULL,
    USER_NAME VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (RESTAURANT) REFERENCES apl_restaurants.RESTAURANT(ID)
);


CREATE TABLE apl_restaurants.menu
(
    ID VARCHAR(50) NOT NULL,
    DESCRIPTION VARCHAR(100) NOT NULL,
    AVAILABILITY VARCHAR(50),
    PRIMARY KEY(ID)
);

CREATE TABLE apl_restaurants.dish
(
    ID VARCHAR(50) NOT NULL,
    PRICE FLOAT NOT NULL,
    DESCRIPTION VARCHAR(200),
    IS_VEGETARIAN BIT NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE apl_restaurants.MENU_IN_RESTAURANT
(
    RESTAURANT_ID VARCHAR(50) NOT NULL,
    MENU_ID VARCHAR(50) NOT NULL,
    PRIMARY KEY(RESTAURANT_ID, MENU_ID),
    FOREIGN KEY (RESTAURANT_ID) REFERENCES apl_restaurants.RESTAURANT(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);

CREATE TABLE apl_restaurants.DISH_IN_MENU
(
    MENU_ID VARCHAR(50) NOT NULL,
    DISH_ID VARCHAR(50) NOT NULL,
    PRIMARY KEY(DISH_ID, MENU_ID),
    FOREIGN KEY (DISH_ID) REFERENCES apl_restaurants.DISH(ID),
    FOREIGN KEY (MENU_ID) REFERENCES apl_restaurants.MENU(ID)
);


insert into apl_restaurants.RESTAURANT (ID, name, address, user_role, user_name)
    values ('e51e8252-ac48-4c4e-8c03-d1803fe4b1a2', 'Restaurant Cool', 'Local Street, 44', 'adminrole', 'admin');


insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a2', 3.0, 'Draft Beer', 1);
insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a3', 2.0, 'Zero Beer', 1);
insert into apl_restaurants.dish (ID, price, description, is_vegetarian)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a4', 2.5, 'Coke', 1);

insert into apl_restaurants.menu (ID, description)
    values ('e51e8252-ac48-4c4e-8a32-d1803fe4baa2', 'Beverages');

insert into apl_restaurants.MENU_IN_RESTAURANT (RESTAURANT_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8c03-d1803fe4b1a2', 'e51e8252-ac48-4c4e-8a32-d1803fe4baa2');


insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a2', 'e51e8252-ac48-4c4e-8a32-d1803fe4baa2');
insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a3', 'e51e8252-ac48-4c4e-8a32-d1803fe4baa2');
insert into apl_restaurants.DISH_IN_MENU (DISH_ID, MENU_ID)
    values ('e51e8252-ac48-4c4e-8abc-d1803fe4b1a4', 'e51e8252-ac48-4c4e-8a32-d1803fe4baa2');
