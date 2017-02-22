# Krusty_Kookies



6. 

# RelationsModel

customers(**customer_name**, address, ~~country~~)

orders(**order_id**, *customer_name*, *cookie_name*, nbr_of_pallets, delivery_date, ~~cost~~)

pallets(**nbr**, *cookie_name*, date, location, *order_id*, is_blocked)

cookies(**cookie_name**, is_blocked)

recipes(*cookie_name*, ingredient_name, quantity, ~~unit~~)

ingredients(ingridient_name, delta_quantity, date, ~~unit~~)




TODO:

1. A section with your names, program/year, and email-addresses

2. A section with an introduction (what the project is about, etc.).

3. A section with notes about requirements that you fulfill or don’t fulfill.

4. An outline of your system (which database manager you use, which programs you have written, how the programs communicate with the database, etc.).

5. An E/R diagram (using UML-notation) which describes your system.

6. Relations. Indicate primary keys, possibly secondary keys, and foreign keys. You must show that the relations are normalized according to your chosen normal form (if a relation "obviously" is in BCNF you may say so, provided that you justify your statement). If a relation is in a lower normal form than BCNF, you must justify this choice.

7. SQL statements to create all tables, views, stored procedures, and other database elements. (Don’t include statements to create the initial contents of the database.)

8. A user’s manual (not necessary if everything in the program is self-explanatory).
