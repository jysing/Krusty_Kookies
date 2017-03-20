PRAGMA foreign_keys=OFF;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Order_Bill;
DROP TABLE IF EXISTS OrderItems;
DROP TABLE IF EXISTS Pallet;
DROP TABLE IF EXISTS Cookie;
DROP TABLE IF EXISTS RecipeItems;
DROP TABLE IF EXISTS Ingredient;
PRAGMA foreign_keys=ON;

-- Create the tables.
CREATE TABLE Customer (
	customer_name varchar(40) PRIMARY KEY,
	address varchar(40) NOT NULL,
	country varchar(40) NOT NULL DEFAULT 'Sweden'
);

CREATE TABLE Order_Bill (
	order_id INTEGER PRIMARY KEY,
	customer_name varchar(40) REFERENCES Customer(customer_name),
	delivery_date date NOT NULL
);

CREATE TABLE OrderItems (
	order_id int REFERENCES Order_Bill(order_id),
	cookie_name varchar(40) REFERENCES Cookie(cookie_name),
	nbrPallet integer
);

CREATE TABLE Pallet (
	pallet_id INTEGER PRIMARY KEY NOT NULL,
	cookie_name varchar(40) REFERENCES Cookie(cookie_name),
	order_id integer REFERENCES Order_Bill(order_id),
	production_date date NOT NULL,
	location varchar(40) NOT NULL,
	is_blocked int
);

CREATE TABLE Cookie (
	cookie_name varchar(40) PRIMARY KEY
);

CREATE TABLE RecipeItems (
	cookie_name varchar(40) REFERENCES Cookie(cookie_name),
	ingredient_name varchar(40) REFERENCES Ingredient(ingredient_name),
	amount real NOT NULL
);

CREATE TABLE Ingredient (
	ingredient_name varchar(40) PRIMARY KEY,
	amount real NOT NULL,
	unit varchar(2) NOT NULL,
	refill_date date
);

INSERT
INTO Customer (customer_name, address)
VALUES
("Finkakor AB", "Helsingborg"),
("Småbröd AB", "Malmö"),
("Kaffebröd AB", "Landskrona"),
("Bjudkakor AB", "Ystad"),
("Kalaskakor AB", "Trelleborg"),
("Partykakor AB", "Kristianstad"),
("Skånekakor AB", "Perstorp");

INSERT
INTO Cookie (cookie_name)
VALUES
("Nut ring"),
("Nut cookie"),
("Amneris"),
("Tango"),
("Almond delight"),
("Berliner");

INSERT
INTO Ingredient (ingredient_name, amount, unit)
VALUES
("Flour", 0, "g"),
("Butter", 0, "g"),
("Icing sugar", 0, "g"),
("Roasted, chopped nuts", 0, "g"),
("Fine-ground nuts", 0, "g"),
("Ground, roasted nuts", 0, "g"),
("Bread crumbs", 0, "g"),
("Sugar", 0, "g"),
("Egg whites", 0, "dl"),
("Chocolate", 0, "g"),
("Marzipan", 0, "g"),
("Eggs", 0, "g"),
("Potato starch", 0, "g"),
("Wheat flour", 0, "g"),
("Sodium bicarbonate", 0, "g"),
("Vanilla", 0, "g"),
("Chopped almonds", 0, "g"),
("Cinnamon", 0, "g"),
("Vanilla sugar", 0, "g");

INSERT
INTO RecipeItems (cookie_name, ingredient_name, amount)
VALUES
("Nut ring", "Flour", 450),
("Nut ring", "Butter", 450),
("Nut ring", "Icing sugar", 190),
("Nut ring", "Roasted, chopped nuts", 225),
("Nut cookie", "Fine-ground nuts", 750),
("Nut cookie", "Ground, roasted nuts", 625),
("Nut cookie", "Bread crumbs", 125),
("Nut cookie", "Sugar", 375),
("Nut cookie", "Egg whites", 3.5),
("Nut cookie", "Chocolate", 50),
("Amneris", "Marzipan", 750),
("Amneris", "Butter", 250),
("Amneris", "Eggs", 250),
("Amneris", "Potato starch", 25),
("Amneris", "Wheat flour", 25),
("Tango", "Butter", 200),
("Tango", "Sugar", 250),
("Tango", "Flour", 300),
("Tango", "Sodium bicarbonate", 4),
("Tango", "Vanilla", 2),
("Almond delight", "Butter", 400),
("Almond delight", "Sugar", 270),
("Almond delight", "Chopped almonds", 279),
("Almond delight", "Flour", 400),
("Almond delight", "Cinnamon", 10),
("Berliner", "Flour", 350),
("Berliner", "Butter", 250),
("Berliner", "Icing sugar", 100),
("Berliner", "Eggs", 5),
("Berliner", "Vanilla sugar", 5),
("Berliner", "Chocolate", 50);
