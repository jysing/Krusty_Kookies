# RelationsModel

customers(**customer_name**, address, ~~country~~)

orders(**order_id**, *customer_name*, *cookie_name*, nbr_of_pallets, delivery_date, ~~cost~~)

pallets(**nbr**, *cookie_name*, date, location, *order_id*, is_blocked)

cookies(**cookie_name**, is_blocked)

recipes(*cookie_name*, ingredient_name, quantity, ~~unit~~)

ingredients(ingridient_name, delta_quantity, date, ~~unit~~)
