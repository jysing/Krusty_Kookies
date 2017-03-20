-- Testing value for database --

INSERT
INTO Order_Bill (order_id, customer_name, delivery_date)
VALUES
(100, "Kalaskakor AB", "2017-04-22"),
(101, "Bjudkakor AB", "2017-05-24"),
(102, "Bjudkakor AB", "2017-04-24"),
(103, "Kalaskakor AB", "2017-03-30"),
(104, "Bjudkakor AB", "2017-04-01"),
(105, "Partykakor AB", "2017-04-24");

INSERT
INTO OrderItems (order_id, cookie_name, nbrPallet)
VALUES
(100, "Berliner", 5),
(100, "Nut cookie", 2),
(100, "Nut ring", 14),
(100, "Tango", 2),
(100, "Amneris", 1),
(100, "Almond delight", 6),
(101, "Berliner", 5),
(101, "Nut cookie", 2),
(101, "Nut ring", 14),
(102, "Tango", 4),
(102, "Amneris", 5),
(102, "Almond delight", 1),
(103, "Berliner", 5),
(104, "Nut cookie", 2),
(104, "Nut ring", 14),
(105, "Tango", 2),
(105, "Amneris", 1),
(105, "Almond delight", 6);
