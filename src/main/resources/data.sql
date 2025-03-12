INSERT INTO users (user_id, nickname, realname, password, username, role)
VALUES (1, '진격의거인', '에렌', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'eren', 'CUSTOMER'),
       (2, '초대형거인', '아르민', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'armin', 'CUSTOMER'),
       (3, '상회회장', '디모리브스', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'DimoReeves', 'ADMIN');

INSERT INTO pickup_options (pickup_option_id, name, description)
VALUES (1, '차량 픽업', '차에서 받을게요'),
       (2, '매장 이용', '매장에서 먹을게요'),
       (3, 'to_go', '밖으로 가져갈게요'),
       (4, '딜리버스', '배달로 받을게요');

INSERT INTO stores (store_id, name, address, contact_number, open_at, close_at, user_id, is_open, image_url)
VALUES (1, '대전유천DT', '대전광역시 중구 계백로1604(유천동)', '1522-3232', '07:00', '22:00', 3, true, '이미지1');

INSERT INTO store_pickup_options (store_pickup_option_id, store_id, pickup_option_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4);

INSERT INTO types (type_id, title, description, category)
VALUES (1, "NEW", "", 'BEVERAGE'),
       (2, "추천", "Recommend", 'BEVERAGE'),
       (3, "에스프레소", "Espresso", 'BEVERAGE'),
       (4, "NEW", "", 'FOOD'),
       (5, "추천", "Recommend", 'FOOD'),
       (6, "샌드위치", "Sandwich", 'FOOD'),
       (7, "NEW", "", 'PRODUCT'),
       (8, "추천", "Recommend", 'PRODUCT'),
       (9, "머그/글라스", "Mug/Glass", 'PRODUCT');
