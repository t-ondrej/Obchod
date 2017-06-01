INSERT INTO Product(`name`, category_id, brand_id, price, description, image_path, quantity)
	 VALUES ('P1', 1, 1, 1, 'desc1', '@../img/1.JPG', 0), 
			('P2', 1, 1, 2, 'desc2', '@../img/2.JPG', 1),
			('P3', 1, 2, 3, 'desc3', '@../img/3.JPG', 2),
            ('P4', 2, 2, 3, 'desc4', '@../img/4.JPG', 3),
            ('P5', 2, 3, 3, 'desc5', '@../img/5.JPG', 4);
            
INSERT INTO Category (`name`) 
	 VALUES ('C1'), 
			('C2'),
			('C3'),
            ('C4'),
            ('C5');
            
INSERT INTO Brand(`name`) 
	 VALUES ('B1'),
			('B2'),
            ('B3'),
            ('B4'),
            ('B5');
     
		
INSERT INTO Bill(account_id, total_price, purchase_date) 
	 VALUES (1, 2, date_add(now(), interval -3 minute)),
			(2, 6, date_add(now(), interval -1 year)), 
            (3, 12, date_add(now(), interval -1 week)), 
            (3, 30, date_add(now(), interval -1 day)), 
            (3, 41, date_add(now(), interval -1 month));           
            
INSERT INTO Bill_Product(bill_id, product_name, category_name, brand_name, quantity, price) 
	 VALUES (1, 'P1', 'C1', 'B1', 2, 1),
			(2, 'P2', 'C1', 'B2', 3, 2),
			(3, 'P3', 'C2', 'B3', 4, 3),
			(4, 'P4', 'C2', 'B3', 5, 4),
			(4, 'P5', 'C3', 'B3', 2, 5),
            (5, 'P3', 'C2', 'B3', 3, 3),
            (5, 'P4', 'C2', 'B3', 3, 4),
            (5, 'P5', 'C3', 'B3', 4, 5);
     

INSERT INTO Person(`name`, surname, city, street, postal_code, email) 
	 VALUES ('Name1', 'Surname1', 'City1', 'Street 1', 1, 'email1@domain.com'),
		    ('Name2', 'Surname2', 'City2', 'Street 2', 2, 'email2@domain.com'),
            ('Name3', 'Surname3', 'City3', 'Street 3', 3, 'email3@domain.com'),
            ('Name4', 'Surname4', 'City4', 'Street 4', 4, 'email4@domain.com'),
            ('Name5', 'Surname5', 'City5', 'Street 5', 5, 'email5@domain.com');

INSERT INTO `Account`(username, password_hash, salt, last_sign_in, is_administrator, id_person)
	 VALUES ('Username1', 'hash1', 'salt1', '2017-5-1 1:00:00', 1, 1),
			('Username2', 'hash2', 'salt2', '2017-5-2 2:00:00', 0, 2),
            ('Username3', 'hash3', 'salt3', '2017-5-2 3:00:00', 0, 3),
            ('Username4', 'hash4', 'salt4', '2017-5-4 4:00:00', 0, 4),
            ('Username5', 'hash5', 'salt5', '2017-5-5 5:00:00', 0, 5);
