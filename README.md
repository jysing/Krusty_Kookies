# Krusty_Kookies

# 1. **A section with your names, program/year, and email-addresses**
- Kewin Erichsen, E11, rys10ker@student.lu.se
- Henrik Fryklund, E13, kem12hfr@student.lu.se
- Joakim Ysing, D12, lan11jys@student.lu.se

# 2. **A section with an introduction (what the project is about, etc.).**
Lorem Ipsum

# 3. **A section with notes about requirements that you fulfill or don’t fulfill.**
Lorem Ipsum

# 4. **An outline of your system (which database manager you use, which programs you have written, how the programs communicate with the database, etc.).**
The DBMS used is **SQLite** and the programs are written i **Java**. **Jupyter Notebook** was used to create and test SQL-commands.

# 5. **An E/R diagram (using UML-notation) which describes your system.**
E/R-diagram: 
![alt text](https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "UML-notation of databse structure")


# 6. **Relations. Indicate primary keys, possibly secondary keys, and foreign keys. You must show that the relations are normalized according to your chosen normal form (if a relation "obviously" is in BCNF you may say so, provided that you justify your statement). If a relation is in a lower normal form than BCNF, you must justify this choice.**
> **Primary Key - Bold**
>
>*Foreign Key - Italic*
>
>~~Extra feature- Stroked~~
>
>customers(**customer_name**, address, ~~country~~)
>
>orders(**order_id**, *customer_name*, delivery_date)
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

# 7. **SQL statements to create all tables, views, stored procedures, and other database elements.**
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

# 8. **A user’s manual (not necessary if everything in the program is self-explanatory).**

