# Krusty_Kookies

# 1.
- Kewin Erichsen, E11, rys10ker@student.lu.se
- Henrik Fryklund, E13, kem12hfr@student.lu.se
- Joakim Ysing, D12, lan11jys@student.lu.se

# 2.
Krusty Kookies is an open source CMS (Cookie Management System) developed by CoolButton Inc. 
The CMS is developed for our customer Crusty Cookies by demand from the course EDA216 at LTH.

# 3.
We fulfill all requirements given by the customer through the written order. No further contact has been made with the customer since receiving the order. 

# 4.
The CMS uses the DBMS  **SQLite** and the software is written in **Java**. **Jupyter Notebook** was used to create and test SQL-commands.

# 5.
E/R-diagram: 
![alt text](https://github.com/jysing/Krusty_Kookies/blob/master/UML.png "UML-notation of databse structure")


# 6. 
> **Primary Key - Bold**
>
>*Foreign Key - Italic*
>
>~~Extra feature- Stroked~~
>
>customers(**customer_name**, address, ~~country~~)
>
>orderBills(**order_id**, *customer_name*, delivery_date)
>
>orderItems(*order_id*, *cookie_name*, nbrPallet)
>
>pallets(**pallet_id**, *cookie_name*, *order_id*, production_date, location, is_blocked)
>
>cookies(**cookie_name**)
>
>recipeItems(*cookie_name*, *ingredient_name*, amount)
>
>ingredients(**ingridient_name**, amount, unit, refill_date)

# 7.
```SQL
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
```

# 8.
CoolButton Inc. software are by basis self-explanatory.

