INSERT INTO users (id, nickname, realname, password, username, role)
VALUES (1, '진격의거인', '에렌', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'eren', 'CUSTOMER'),
       (2, '초대형거인', '아르민', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'armin', 'CUSTOMER'),
       (3, '상회회장', '디모리브스', '$2a$10$lb5Qtp1X7NA8azxkigB0E.AvNoL1sN0QgxSaJlTzrldCq5duv03Ay', 'DimoReeves', 'ADMIN');
ALTER TABLE users AUTO_INCREMENT = 4;

INSERT INTO pickup_options (id, name, description)
VALUES (1, '차량 픽업', '차에서 받을게요'),
       (2, '매장 이용', '매장에서 먹을게요'),
       (3, 'to_go', '밖으로 가져갈게요'),
       (4, '딜리버스', '배달로 받을게요');
ALTER TABLE pickup_options AUTO_INCREMENT = 5;

INSERT INTO stores (id, name, address, contact_number, open_at, close_at, user_id, is_open, image_url)
VALUES (1, '대전유천DT', '대전광역시 중구 계백로1604(유천동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (2, '대전대사DT', '대전광역시 중구 계룡로 949(대사동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (3, '서대전네거리', '대전광역시 중구 계룡로 878(오류동), 1층', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (4, '대전태평', '대전광역시 중구 태평로 71(태평동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (5, '대전복수', '대전광역시 서구 복수남로 28(복수동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (6, '대전목동DT', '대전광역시 중구 동서대로 1387(목동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (7, '대전중앙로R', '대전광역시 중구 대종로486 (은행동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'),
       (8, '은행동', '대전광역시 중구 중앙로164 (은행동)', '1522-3232', '07:00', '22:00', 3, true, 'https://images.unsplash.com/photo-1601813913455-118810e79277?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
ALTER TABLE stores AUTO_INCREMENT = 2;

INSERT INTO store_pickup_options (id, store_id, pickup_option_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 2, 2),
       (6, 2, 3),
       (7, 2, 4),
       (8, 3, 1),
       (9, 3, 2),
       (10, 3, 4),
       (11, 4, 1),
       (12, 4, 2),
       (13, 4, 3),
       (14, 5, 1),
       (15, 5, 2),
       (16, 5, 3),
       (17, 5, 4),
       (18, 6, 1),
       (19, 6, 2),
       (20, 6, 3),
       (21, 6, 4),
       (22, 7, 1),
       (23, 7, 2),
       (24, 7, 3),
       (25, 7, 4),
       (26, 8, 1),
       (27, 8, 2),
       (28, 8, 3),
       (29, 8, 4);
ALTER TABLE store_pickup_options AUTO_INCREMENT = 5;

INSERT INTO types (id, title, description, category)
VALUES (1, 'NEW', '', 'BEVERAGE'),
       (2, '추천', 'Recommend', 'BEVERAGE'),
       (3, '에스프레소', 'Espresso', 'BEVERAGE'),
       (4, 'NEW', '', 'FOOD'),
       (5, '추천', 'Recommend', 'FOOD'),
       (6, '샌드위치', 'Sandwich', 'FOOD'),
       (7, 'NEW', '', 'MERCHANDISE'),
       (8, '추천', 'Recommend', 'MERCHANDISE'),
       (9, '머그/글라스', 'Mug/Glass', 'MERCHANDISE');
ALTER TABLE types AUTO_INCREMENT = 10;

INSERT INTO products (id, kr_name, en_name, description, base_price, image_url, category, product_type)
VALUES (1, '카페 아메리카노(HOT)', 'Caffe Americano(HOT)', '진한 에스프레소와 뜨거운 물을 섞어 스타벅스의 깔끔하고 강렬한 에스프레소를 가장 부드럽게 잘 느낄 수 있는 커피', 4700,
        '아메리카노 이미지', 'BEVERAGE', 'BEVERAGE'),
       (2, '카페 아메리카노(ICE)', 'Caffe Americano(ICE)', '진한 에스프레소와 차가운 물을 섞어 스타벅스의 깔끔하고 강렬한 에스프레소를 가장 부드럽게 잘 느낄 수 있는 커피', 4700,
        '아메리카노 이미지', 'BEVERAGE', 'BEVERAGE'),
       (3, '카페 라떼(HOT)', 'Caffe Latte(HOT)', '풍부하고 진한 에스프레소가 신선한 스팀 밀크를 만나 부드러워진 커피 위에 우유 거품을 살짝 얹은 대표적인 커피 라떼', 5200,
        '카페라떼 이미지', 'BEVERAGE', 'BEVERAGE'),
       (4, '카페 라떼(ICE)', 'Caffe Latte(ICE)', '풍부하고 진한 에스프레소가 신선한 스팀 밀크를 만나 부드러워진 커피 위에 우유 거품을 살짝 얹은 대표적인 커피 라떼', 5200,
        '카페라떼 이미지', 'BEVERAGE', 'BEVERAGE'),
       (5, '크랜베리 치킨 샌드위치', 'Cranberry Chicken Sandwich',
        '고소한 호밀 식빵 안에 크랜베리와 호두가 들어간 치킨\n샐러드와 신선한 로메인, 토마토가 어우러진 샌드위치입니다.', 6500,
        '크랜베리 치킨 샌드위치 이미지', 'FOOD', 'FOOD'),
       (6, '멜팅 치즈 베이컨 토스트', 'Melting Cheese Bacon Toast',
        '프렌치토스트 스타일로 구워낸 빵 사이에 에그 스프레드를 샌드하고 빵 위에 치즈와 베이컨을 얹어 구운 토스트입니다.', 5700,
        '멜팅 치즈 베이컨 토스트 이미지', 'FOOD', 'FOOD'),
       (7, '사이렌 하우스 머그 237ml', 'Siren house mug 237ml', '클래식한 디자인과 사이렌 로고가 매치된 237ml 머그입니다.', 18000,
        '사이렌 하우스 머그 237ml 이미지', 'MERCHANDISE', 'MERCHANDISE'),
       (8, '사이렌 하우스 머그 355ml', 'Siren house mug 355ml', '클래식한 디자인과 사이렌 로고가 매치된 355ml 머그입니다.', 19000,
        '사이렌 하우스 머그 355ml 이미지', 'MERCHANDISE', 'MERCHANDISE');
ALTER TABLE products AUTO_INCREMENT = 9;

INSERT INTO product_types (id, product_id, type_id)
VALUES (1, 1, 3),
       (2, 2, 3),
       (3, 3, 3),
       (4, 4, 3),
       (5, 5, 6),
       (6, 6, 6),
       (7, 7, 9),
       (8, 8, 9);
ALTER TABLE product_types AUTO_INCREMENT = 9;
