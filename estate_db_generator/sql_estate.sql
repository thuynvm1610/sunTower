-- =====================================
-- DATABASE: Estate Management System
-- Author: Thủy
-- =====================================

DROP DATABASE IF EXISTS estate;
CREATE DATABASE estate CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE estate;

-- =============================
-- BẢNG QUẬN / DISTRICT
-- =============================
CREATE TABLE district (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO district (name) VALUES
('Ba Đình'),
('Hoàn Kiếm'),
('Đống Đa'),
('Hai Bà Trưng'),
('Cầu Giấy'),
('Thanh Xuân'),
('Nam Từ Liêm'),
('Tây Hồ'),
('Long Biên'),
('Hà Đông'),
('Bắc Từ Liêm');

-- =============================
-- BẢNG NHÂN VIÊN / STAFF
-- =============================
CREATE TABLE staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(255),
    full_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    image VARCHAR(30),
    role VARCHAR(50),
    created_date DATETIME,
    modified_date DATETIME
);

INSERT INTO staff (username, password, full_name, phone, email, image, role, created_date, modified_date) VALUES
('nvmt1610', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Nguyễn Văn Minh Thủy', '0375577856', 'thuy.nvm@gmail.com', 'staff_001', 'ADMIN', '2018-01-05', '2018-01-05'),
('tmq0102', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Trần Minh Quân', '0902000001', 'quan.tm@gmail.com', 'staff_002', 'STAFF', '2019-02-15', '2024-04-12'),
('lth2105', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Lê Thị Hồng', '0902000002', 'hong.lt@gmail.com', 'staff_003', 'STAFF', '2019-05-10', '2024-03-18'),
('pvd1208', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Phạm Văn Dũng', '0902000003', 'dung.pv@gmail.com', 'staff_004', 'STAFF', '2020-01-08', '2024-05-09'),
('nha0401', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Nguyễn Hoàng Anh', '0902000004', 'anh.nh@gmail.com', 'staff_005', 'STAFF', '2020-07-20', '2024-04-28'),
('vnb2511', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Vũ Ngọc Bích', '0902000005', 'bich.vn@gmail.com', 'staff_006', 'STAFF', '2021-02-11', '2025-03-15'),
('dhc2307', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Đặng Hữu Cường', '0902000006', 'cuong.dh@gmail.com', 'staff_007', 'STAFF', '2021-09-03', '2024-06-10'),
('ltm1905', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Lương Thị Mai', '0902000007', 'mai.lt@gmail.com', 'staff_008', 'STAFF', '2022-03-25', '2025-02-02'),
('nta1212', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Ngô Tuấn Anh', '0902000008', 'anhtn@gmail.com', 'staff_009', 'STAFF', '2022-06-11', '2024-12-30'),
('hdk1311', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Hoàng Đức Khánh', '0902000009', 'khanh.hd@gmail.com', 'staff_010', 'STAFF', '2023-01-05', '2025-01-16'),
('ttt1612', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Trịnh Thu Trang', '0902000010', 'trang.tt@gmail.com', 'staff_011', 'STAFF', '2023-03-12', '2025-02-25'),
('mhc0909', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Hồ Chí Minh', '0902000011', 'minh.hc@gmail.com', 'staff_012', 'ADMIN', '2019-09-09', '2024-12-20'),
('nvt1503', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Nguyễn Văn Tùng', '0902000012', 'tung.nv@gmail.com', 'staff_013', 'STAFF', '2020-03-15', '2024-11-08'),
('pth2807', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Phạm Thị Hạnh', '0902000013', 'hanh.pt@gmail.com', 'staff_014', 'STAFF', '2020-07-28', '2025-01-05'),
('tqd0211', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Trần Quang Đức', '0902000014', 'duc.tq@gmail.com', 'staff_015', 'STAFF', '2021-02-11', '2024-10-19'),
('lmn3006', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Lê Minh Ngọc', '0902000015', 'ngoc.lm@gmail.com', 'staff_016', 'STAFF', '2021-06-30', '2025-02-10'),
('dth1708', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Đỗ Thị Hương', '0902000016', 'huong.dt@gmail.com', 'staff_017', 'STAFF', '2022-08-17', '2024-12-14'),
('nqk0404', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Nguyễn Quốc Khánh', '0902000017', 'khanh.nq@gmail.com', 'staff_018', 'STAFF', '2022-04-04', '2025-01-28'),
('vtl2210', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Vũ Thị Linh', '0902000018', 'linh.vt@gmail.com', 'staff_019', 'STAFF', '2023-10-22', '2025-03-01');

-- =============================
-- BẢNG TÒA NHÀ / BUILDING
-- =============================
CREATE TABLE building (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    district_id BIGINT,
    ward VARCHAR(100),
    street VARCHAR(255),
    number_of_floor INT,
    number_of_basement INT,
    floor_area INT,
    direction VARCHAR(50),
    level VARCHAR(50),
    rent_price DECIMAL(15,2),
    service_fee DECIMAL(15,2),
    car_fee DECIMAL(15,2),
    motorbike_fee DECIMAL(15,2),
    water_fee DECIMAL(15,2),
    electricity_fee DECIMAL(15,2),
    deposit DECIMAL(15,2),
    link_of_building TEXT,
    image TEXT,
    created_date DATETIME,
    modified_date DATETIME,
    FOREIGN KEY (district_id) REFERENCES district(id)
);

INSERT INTO building 
(name, district_id, ward, street, number_of_floor, number_of_basement, floor_area, direction, level, rent_price, service_fee, car_fee, motorbike_fee, water_fee, electricity_fee, deposit, link_of_building, image, created_date, modified_date) VALUES
('Duy Tân Tower', 5, 'Dịch Vọng Hậu', 'Duy Tân', 15, 2, 1800, 'DONG_NAM', 'A', 900000, 1000000, 1500000, 70000, 15000, 4000, 60000000, 'https://duytantower.vn', 'duy-tan-tower.jpg', '2019-05-10', '2024-06-12'),
('Lotte Center', 1, 'Liễu Giai', 'Liễu Giai', 60, 5, 7000, 'TAY_BAC', 'A_PLUS', 1200000, 2500000, 2000000, 120000, 25000, 6000, 200000000, 'https://lottecenter.vn', 'lotte-center.jpg', '2018-11-20', '2025-02-11'),
('Pacific Place', 2, 'Tràng Tiền', 'Lý Thường Kiệt', 20, 3, 3500, 'NAM', 'B', 600000, 2000000, 2500000, 100000, 20000, 5500, 100000000, 'https://pacificplace.vn', 'pacific-place.jpg', '2019-07-18', '2024-05-02'),
('VCCI Tower', 3, 'Láng Hạ', 'Láng Hạ', 20, 2, 4000, 'DONG', 'A', 900000, 1500000, 1800000, 80000, 18000, 5000, 80000000, 'https://vciitower.vn', 'vcii-tower.jpg', '2020-03-09', '2025-01-05'),
('Hòa Bình Green City', 4, 'Vĩnh Tuy', 'Minh Khai', 25, 3, 4200, 'TAY_NAM', 'B_PLUS', 800000, 1200000, 1600000, 70000, 16000, 4500, 70000000, 'https://hoabinhgreencity.vn', 'hoa-binh-green-city.jpg', '2020-09-21', '2025-03-03'),
('HUD Tower', 6, 'Thanh Xuân Bắc', 'Nguyễn Tuân', 28, 3, 5000, 'NAM', 'A', 900000, 1300000, 1700000, 90000, 17000, 5200, 90000000, 'https://hudtower.vn', 'hud-tower.jpg', '2021-01-19', '2025-01-22'),
('Keangnam Landmark 72', 7, 'Mễ Trì', 'Phạm Hùng', 72, 5, 10000, 'TAY_BAC', 'A_PLUS', 1200000, 3000000, 2600000, 150000, 25000, 6000, 250000000, 'https://keangnamtower.vn', 'keangnam-tower.jpg', '2018-08-25', '2024-12-12'),
('FPT Tower', 5, 'Yên Hòa', 'Phạm Văn Bạch', 21, 2, 4200, 'DONG_BAC', 'A', 900000, 1200000, 1600000, 80000, 10000, 4800, 80000000, 'https://fpttower.vn', 'fpt-tower.jpg', '2021-06-15', '2025-02-25'),
('Handico Tower', 7, 'Mễ Trì', 'Phạm Hùng', 39, 3, 5500, 'TAY', 'C_PLUS', 500000, 2000000, 2500000, 120000, 20000, 5500, 120000000, 'https://handico.vn', 'handico-tower.jpg', '2020-08-10', '2025-03-05'),
('Charmvit Tower', 3, 'Trung Hòa', 'Trần Duy Hưng', 27, 3, 4500, 'DONG_NAM', 'A', 900000, 1800000, 2200000, 100000, 18000, 5200, 100000000, 'https://charmvit.vn', 'charmvit-tower.jpg', '2019-03-18', '2024-11-20'),
('Thăng Long Tower', 6, 'Hạ Đình', 'Nguyễn Trãi', 20, 2, 3800, 'NAM', 'B_PLUS', 800000, 1000000, 1300000, 70000, 15000, 4700, 60000000, 'https://thanglongtower.vn', 'thang-long-tower.jpg', '2021-04-22', '2024-10-09'),
('Tràng An Complex', 1, 'Ngọc Khánh', 'Phạm Văn Đồng', 29, 3, 4800, 'DONG_BAC', 'A', 900000, 1400000, 1800000, 90000, 18000, 5000, 90000000, 'https://trangancomplex.vn', 'trang-an-complex.jpg', '2019-08-08', '2025-01-29'),
('Sun Grand City Thụy Khuê', 8, 'Thụy Khuê', 'Thụy Khuê', 25, 3, 4200, 'TAY', 'A_PLUS', 1100000, 2000000, 2200000, 120000, 20000, 5500, 150000000, 'https://sungrandcity.vn', 'sun-grand-city.jpg', '2019-06-12', '2025-02-18'),
('Watermark Hồ Tây', 8, 'Nhật Tân', 'Âu Cơ', 18, 2, 3600, 'TAY_BAC', 'A', 950000, 1500000, 1800000, 90000, 18000, 5200, 90000000, 'https://watermark.vn', 'watermark-tayho.jpg', '2020-03-25', '2024-12-30'),
('Mipec Riverside Office', 9, 'Ngọc Lâm', 'Long Biên', 22, 2, 3800, 'DONG_NAM', 'B_PLUS', 650000, 1200000, 1500000, 80000, 16000, 4800, 70000000, 'https://mipecriverside.vn', 'mipec-riverside.jpg', '2020-07-14', '2024-11-05'),
('Sài Đồng Tower', 9, 'Sài Đồng', 'Nguyễn Văn Linh', 20, 2, 4000, 'NAM', 'B', 600000, 1000000, 1400000, 70000, 15000, 4500, 60000000, 'https://saidongtower.vn', 'sai-dong-tower.jpg', '2021-02-10', '2025-01-12'),
('Văn Phú Victoria', 10, 'Phú La', 'Lê Trọng Tấn', 30, 3, 5000, 'DONG_BAC', 'A', 750000, 1300000, 1600000, 85000, 17000, 5000, 80000000, 'https://vanphuvictoria.vn', 'van-phu-victoria.jpg', '2019-09-19', '2025-03-01'),
('The Pride Tower', 10, 'La Khê', 'Tố Hữu', 35, 3, 5200, 'TAY_NAM', 'B_PLUS', 700000, 1200000, 1500000, 80000, 16000, 4800, 75000000, 'https://thepride.vn', 'the-pride-tower.jpg', '2020-05-28', '2024-10-21'),
('Sunshine City Office', 11, 'Đông Ngạc', 'Phạm Văn Đồng', 32, 3, 5400, 'DONG', 'A', 850000, 1500000, 1800000, 90000, 18000, 5200, 90000000, 'https://sunshinecity.vn', 'sunshine-city-office.jpg', '2020-11-11', '2025-02-08'),
('EcoHome 3 Tower', 11, 'Cổ Nhuế', 'Tân Xuân', 27, 2, 4100, 'BAC', 'B', 650000, 1000000, 1300000, 70000, 15000, 4500, 60000000, 'https://ecohome.vn', 'ecohome-3.jpg', '2021-03-06', '2024-09-14'),
('Daewoo Business Center', 1, 'Ngọc Khánh', 'Kim Mã', 15, 2, 3000, 'DONG_NAM', 'B_PLUS', 750000, 1200000, 1500000, 80000, 16000, 4800, 70000000, 'https://daewoo-bc.vn', 'daewoo-bc.jpg', '2020-06-10', '2024-11-12'),
('Vincom Metropolis Office', 1, 'Ngọc Khánh', 'Liễu Giai', 45, 5, 8000, 'TAY', 'A_PLUS', 1200000, 2800000, 2600000, 150000, 25000, 6000, 220000000, 'https://vincom-metropolis.vn', 'vincom-metropolis.jpg', '2018-10-05', '2025-03-05'),
('TNR Tower Nguyễn Chí Thanh', 3, 'Láng Thượng', 'Nguyễn Chí Thanh', 29, 4, 5500, 'DONG', 'A_PLUS', 1050000, 2200000, 2400000, 130000, 24000, 5800, 160000000, 'https://tnrtower.vn', 'tnr-nguyen-chi-thanh.jpg', '2018-09-09', '2025-03-01'),
('Indochina Plaza Hanoi', 5, 'Yên Hòa', 'Xuân Thủy', 39, 3, 6800, 'DONG_BAC', 'A', 950000, 2000000, 2200000, 120000, 22000, 5600, 150000000, 'https://iph.vn', 'iph-office.jpg', '2019-07-15', '2024-11-28'),
('VNPT Tower', 5, 'Yên Hòa', 'Phạm Hùng', 30, 3, 5000, 'NAM', 'B_PLUS', 780000, 1400000, 1700000, 90000, 18000, 5000, 80000000, 'https://vnpttower.vn', 'vnpt-tower.jpg', '2020-03-04', '2025-01-20'),
('CTM Complex', 5, 'Trung Hòa', 'Trần Duy Hưng', 21, 2, 4200, 'DONG_NAM', 'B_PLUS', 720000, 1300000, 1600000, 85000, 17000, 4800, 70000000, 'https://ctmcomplex.vn', 'ctm-complex.jpg', '2021-06-22', '2024-10-11'),
('Handiresco Tower', 6, 'Thanh Xuân Trung', 'Nguyễn Tuân', 18, 2, 3500, 'NAM', 'B', 650000, 1100000, 1400000, 75000, 15000, 4600, 60000000, 'https://handiresco.vn', 'handiresco.jpg', '2021-01-12', '2025-02-15'),
('CEO Tower', 7, 'Mỹ Đình 1', 'Phạm Hùng', 30, 3, 5200, 'DONG', 'A', 900000, 1700000, 2000000, 100000, 19000, 5400, 100000000, 'https://ceotower.vn', 'ceo-tower.jpg', '2019-03-20', '2025-02-22'),
('Crown Plaza Office', 7, 'Mỹ Đình 2', 'Lê Đức Thọ', 25, 3, 4800, 'TAY_NAM', 'B_PLUS', 780000, 1400000, 1700000, 90000, 17000, 5000, 80000000, 'https://crownplaza.vn', 'crownplaza.jpg', '2020-06-30', '2024-11-18'),
('Golden Palace Tower', 7, 'Mễ Trì', 'Mễ Trì', 35, 4, 6500, 'TAY_BAC', 'A', 950000, 2000000, 2300000, 120000, 23000, 5600, 150000000, 'https://goldenpalace.vn', 'golden-palace.jpg', '2018-11-25', '2025-03-03');

-- =============================
-- BẢNG KHÁCH HÀNG / CUSTOMER
-- =============================
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    full_name VARCHAR(100),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    role VARCHAR(50),
    created_date DATETIME,
    modified_date DATETIME
);

INSERT INTO customer (username, password, full_name, phone, email, role, created_date, modified_date) VALUES
('abcVietNam','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH ABC Việt Nam', '0903000001', 'contact@abc.com.vn', 'CUSTOMER', '2019-06-18', '2024-08-10'),
('solutionIT','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH IT Solution', '0903000002', 'hr@itsolution.vn', 'CUSTOMER', '2020-05-14', '2024-10-03'),
('DHT','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty CP Dược Hà Thành', '0903000003', 'info@duochathanh.vn', 'CUSTOMER', '2018-09-21', '2023-12-02'),
('VietATMCP','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Ngân hàng TMCP Việt Á', '0903000004', 'office@vietabank.vn', 'CUSTOMER', '2019-12-17', '2024-11-20'),
('HoangGiaTM','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH Thương mại Hoàng Gia', '0903000005', 'sales@hoanggia.vn', 'CUSTOMER', '2021-01-09', '2024-09-18'),
('FBViet','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty CP F&B Việt', '0903000006', 'admin@fnbviet.vn', 'CUSTOMER', '2021-07-05', '2025-03-10'),
('CoDienHN','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH Cơ điện Hà Nội', '0903000007', 'info@codienhanoi.vn', 'CUSTOMER', '2022-03-22', '2024-11-04'),
('ThietKeXanh','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH Thiết kế Xanh', '0903000008', 'contact@thietkexanh.vn', 'CUSTOMER', '2023-02-10', '2024-12-15'),
('AnPhatCorp', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty CP Tập đoàn An Phát', '0903000009', 'contact@anphatgroup.vn', 'CUSTOMER', '2019-03-12', '2024-10-18'),
('VietLogistics', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty TNHH Dịch vụ Logistics Việt', '0903000010', 'info@vietlogistics.vn', 'CUSTOMER', '2020-08-26', '2024-11-30'),
('SunEdu', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty CP Giáo dục Mặt Trời', '0903000011', 'admin@sunedu.vn', 'CUSTOMER', '2018-06-04', '2023-12-22'),
('GreenTechVN', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty TNHH Công nghệ Xanh Việt Nam', '0903000012', 'support@greentechvn.vn', 'CUSTOMER', '2021-11-15', '2025-01-12'),
('NamCuongGroup', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty CP Tập đoàn Nam Cường', '0903000013', 'office@namcuonggroup.vn', 'CUSTOMER', '2017-09-28', '2024-08-06'),
('BlueOcean', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty TNHH Blue Ocean Việt Nam', '0903000014', 'contact@blueocean.vn', 'CUSTOMER', '2022-05-09', '2025-02-20'),
('VinaMedia', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty CP Truyền thông Vina Media', '0903000015', 'hr@vinamedia.vn', 'CUSTOMER', '2020-01-17', '2024-09-27'),
('MinhPhatCons', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Công ty TNHH Xây dựng Minh Phát', '0903000016', 'info@minhphatcons.vn', 'CUSTOMER', '2019-10-03', '2024-12-08');

-- =============================
-- BẢNG PASSWORD_RESET
-- =============================
CREATE TABLE password_reset_token (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    token VARCHAR(255) NOT NULL,
    expires_at DATETIME NOT NULL,
    used BOOLEAN DEFAULT FALSE,

    user_type VARCHAR(20) NOT NULL,   -- 'STAFF' / 'CUSTOMER'
    user_id BIGINT NOT NULL,          -- id bên staff / customer

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (token)
);

-- =============================
-- BẢNG RENT AREA
-- =============================
CREATE TABLE rent_area (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT,
    value INT,
    created_date DATETIME,
    modified_date DATETIME,
    FOREIGN KEY (building_id) REFERENCES building(id)
);

INSERT INTO rent_area (building_id, value, created_date, modified_date) VALUES
(1, 150, '2019-05-15', '2024-06-01'),
(1, 200, '2019-06-10', '2024-07-20'),

(2, 300, '2018-12-05', '2025-02-10'),
(2, 250, '2018-12-05', '2025-02-10'),
(2, 350, '2018-12-05', '2025-02-10'),

(3, 250, '2019-08-01', '2024-05-10'),
(3, 100, '2019-08-01', '2024-05-10'),

(4, 400, '2020-03-12', '2024-11-25'),
(4, 300, '2020-03-12', '2024-11-25'),

(5, 350, '2020-09-25', '2025-03-02'),
(5, 200, '2020-09-25', '2025-03-02'),
(5, 150, '2020-09-25', '2025-03-02'),
(5, 450, '2020-09-25', '2025-03-02'),

(6, 420, '2021-02-20', '2024-12-12'),
(6, 200, '2021-02-20', '2024-12-12'),
(6, 150, '2021-02-20', '2024-12-12'),

(7, 600, '2018-09-01', '2024-10-05'),

(8, 320, '2021-06-20', '2025-02-12'),
(8, 500, '2021-06-20', '2025-02-12'),

(9, 450, '2020-08-15', '2025-03-01'),
(9, 200, '2020-08-15', '2025-03-01'),

(10, 500, '2019-04-02', '2024-11-10'),
(10, 180, '2019-04-02', '2024-11-10'),
(10, 360, '2019-04-02', '2024-11-10'),

(11, 280, '2021-04-30', '2024-10-09'),
(11, 350, '2021-04-30', '2024-10-09'),
(11, 170, '2021-04-30', '2024-10-09'),

(12, 350, '2019-08-15', '2025-01-29'),

(13, 150, '2019-06-15', '2025-02-18'),
(13, 280, '2019-06-15', '2025-02-18'),
(13, 450, '2019-06-15', '2025-02-18'),

(14, 120, '2020-04-01', '2024-12-30'),
(14, 220, '2020-04-01', '2024-12-30'),
(14, 350, '2020-04-01', '2024-12-30'),

(15, 150, '2020-08-01', '2024-11-05'),
(15, 300, '2020-08-01', '2024-11-05'),
(15, 480, '2020-08-01', '2024-11-05'),

(16, 180, '2021-03-01', '2025-01-12'),
(16, 320, '2021-03-01', '2025-01-12'),
(16, 500, '2021-03-01', '2025-01-12'),

(17, 200, '2019-10-01', '2025-03-01'),
(17, 350, '2019-10-01', '2025-03-01'),
(17, 600, '2019-10-01', '2025-03-01'),

(18, 220, '2020-06-15', '2024-10-21'),
(18, 400, '2020-06-15', '2024-10-21'),
(18, 650, '2020-06-15', '2024-10-21'),
(18, 800, '2020-06-15', '2024-10-21'),

(19, 200, '2020-12-01', '2025-02-08'),
(19, 380, '2020-12-01', '2025-02-08'),
(19, 600, '2020-12-01', '2025-02-08'),

(20, 150, '2021-04-01', '2024-09-14'),
(20, 280, '2021-04-01', '2024-09-14'),
(20, 450, '2021-04-01', '2024-09-14'),

(21, 120, '2020-07-01', '2024-11-12'),
(21, 220, '2020-07-01', '2024-11-12'),
(21, 350, '2020-07-01', '2024-11-12'),

(22, 300, '2018-11-01', '2025-03-05'),
(22, 500, '2018-11-01', '2025-03-05'),
(22, 750, '2018-11-01', '2025-03-05'),
(22, 1000, '2018-11-01', '2025-03-05'),

(23, 200, '2018-10-01', '2025-03-01'),
(23, 380, '2018-10-01', '2025-03-01'),
(23, 600, '2018-10-01', '2025-03-01'),

(24, 250, '2019-08-01', '2024-11-28'),
(24, 450, '2019-08-01', '2024-11-28'),
(24, 700, '2019-08-01', '2024-11-28'),
(24, 900, '2019-08-01', '2024-11-28'),

(25, 200, '2020-04-01', '2025-01-20'),
(25, 350, '2020-04-01', '2025-01-20'),
(25, 600, '2020-04-01', '2025-01-20'),

(26, 150, '2021-07-01', '2024-10-11'),
(26, 280, '2021-07-01', '2024-10-11'),
(26, 420, '2021-07-01', '2024-10-11'),

(27, 120, '2021-02-01', '2025-02-15'),
(27, 240, '2021-02-01', '2025-02-15'),
(27, 360, '2021-02-01', '2025-02-15'),

(28, 220, '2019-04-01', '2025-02-22'),
(28, 400, '2019-04-01', '2025-02-22'),
(28, 650, '2019-04-01', '2025-02-22'),

(29, 180, '2020-07-15', '2024-11-18'),
(29, 320, '2020-07-15', '2024-11-18'),
(29, 500, '2020-07-15', '2024-11-18'),

(30, 250, '2018-12-15', '2025-03-03'),
(30, 450, '2018-12-15', '2025-03-03'),
(30, 700, '2018-12-15', '2025-03-03'),
(30, 900, '2018-12-15', '2025-03-03');

-- =============================
-- BẢNG PHÂN CÔNG NHÂN VIÊN / BUILDING
-- =============================
CREATE TABLE assignment_building (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT,
    staff_id BIGINT,
    FOREIGN KEY (building_id) REFERENCES building(id),
    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO assignment_building (building_id, staff_id) VALUES
(1, 11), (1, 2),
(2, 3), (2, 4),
(3, 2), (3, 5),
(4, 3), (4, 6),
(5, 5), (5, 7),
(6, 6), (6, 8),
(7, 4), (7, 9),
(8, 2), (8, 10),
(9, 9), (9, 7),
(10, 3), (10, 8), (10, 2),
(11, 5), (11, 10),
(12, 11), (12, 6),
(13, 12), (13, 8), (13, 14),
(14, 9),  (14, 15),
(15, 7),  (15, 16),
(16, 5),  (16, 17),
(17, 6),  (17, 13), (17, 18),
(18, 8),  (18, 14),
(19, 10), (19, 15), (19, 19),
(20, 11), (20, 16),
(21, 2),  (21, 12),
(22, 3),  (22, 14), (22, 12),
(23, 1),  (23, 4), (23, 15),
(24, 6),  (24, 18),
(25, 7),  (25, 16), (25, 13),
(26, 8),  (26, 19),
(27, 4),  (27, 12), (27, 17),
(28, 5),  (28, 15),
(29, 10),
(30, 15);

-- =============================
-- BẢNG PHÂN CÔNG KHÁCH HÀNG / CUSTOMER
-- =============================
CREATE TABLE assignment_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    staff_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO assignment_customer (customer_id, staff_id) VALUES
(1, 11), (1,2), (1, 1),
(3, 5), (3, 2), (3, 3),
(4, 3), (4, 4), (4, 11), (4, 6),
(5, 8), (5, 10), (5, 6),
(6, 9), (6, 7),
(7, 6), (7, 7), (7, 5),
(8, 8),
(9, 2), (9, 14), (9, 15), (9, 4),
(10, 7), (10, 16),
(11, 3), (11, 8), (11, 18),
(12, 6), (12, 13), (12, 3),
(13, 4), (13, 15), (13, 6), (13, 14), (13, 5),
(14, 9), (14, 17), (14, 4),
(15, 10), (15, 5), (15, 15), (15, 8),
(16, 5), (16, 11), (16, 14);

-- =============================
-- BẢNG HỢP ĐỒNG / CONTRACT
-- =============================
CREATE TABLE contract (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_id BIGINT,
    customer_id BIGINT,
    staff_id BIGINT,
    rent_price DECIMAL(15,2),
    rent_area INT,
    start_date DATETIME,
    end_date DATETIME,
    status VARCHAR(50),
    created_date DATETIME,
    modified_date DATETIME,
    FOREIGN KEY (building_id) REFERENCES building(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

INSERT INTO contract 
(building_id, customer_id, staff_id, rent_price, rent_area, start_date, end_date, status, created_date, modified_date) VALUES
('1', '1', '11', '900000.00', '150', '2019-07-01 00:00:00', '2021-07-01 00:00:00', 'EXPIRED', '2019-06-28 09:30:21', '2021-07-02 07:30:01'),
('1', '1', '2', '900000.00', '200', '2022-08-01 00:00:00', '2026-02-01 00:00:00', 'ACTIVE', '2022-07-25 10:23:09', '2024-08-01 10:22:00'),

('2', '4', '3', '1200000.00', '300', '2018-12-01 00:00:00', '2021-10-01 00:00:00', 'EXPIRED', '2018-11-25 14:16:58', '2021-12-15 08:10:40'),
('2', '4', '4', '1200000.00', '350', '2022-01-01 00:00:00', '2026-05-01 00:00:00', 'ACTIVE', '2021-12-29 15:01:00', '2025-01-10 09:10:20'),

('3', '3', '2', '600000.00', '250', '2020-03-01 00:00:00', '2025-12-01 00:00:00', 'ACTIVE', '2020-02-27 07:20:00', '2022-03-02 15:49:41'),
('3', '3', '5', '600000.00', '100', '2022-04-01 00:00:00', '2025-04-01 00:00:00', 'EXPIRED', '2022-03-29 11:27:59', '2024-04-01 14:50:00'),

('4', '4', '3', '900000.00', '400', '2020-05-01 00:00:00', '2023-06-01 00:00:00', 'EXPIRED', '2020-04-25 09:56:21', '2022-05-02 09:00:00'),
('4', '7', '6', '900000.00', '400', '2025-07-01 00:00:00', '2028-02-01 00:00:00', 'ACTIVE', '2025-07-01 10:00:01', '2025-09-16 10:30:08'),

('5', '7', '5', '800000.00', '350', '2021-10-01 00:00:00', '2026-10-01 00:00:00', 'ACTIVE', '2021-09-16 16:10:20', '2024-10-01 16:10:04'),
('5', '7', '7', '800000.00', '150', '2024-01-01 00:00:00', '2026-10-01 00:00:00', 'ACTIVE', '2023-12-27 09:06:01', '2025-10-31 09:58:00'),

('6', '5', '6', '900000.00', '420', '2021-05-01 00:00:00', '2026-08-01 00:00:00', 'ACTIVE', '2021-04-25 08:58:02', '2023-05-11 08:51:10'),
('6', '5', '8', '900000.00', '150', '2024-06-01 00:00:00', '2025-09-01 00:00:00', 'EXPIRED', '2024-05-20 10:47:15', '2025-06-01 10:09:00'),

('7', '6', '9', '1200000.00', '600', '2021-09-01 00:00:00', '2026-09-01 00:00:00', 'ACTIVE', '2021-08-22 15:08:56', '2024-09-01 14:18:00'),

('8', '1', '2', '900000.00', '320', '2021-07-01 00:00:00', '2024-07-01 00:00:00', 'EXPIRED', '2021-06-29 14:09:59', '2024-01-01 15:27:15'),

('9', '6', '9', '500000.00', '200', '2025-02-01 00:00:00', '2025-12-01 00:00:00', 'ACTIVE', '2025-01-22 00:00:00', '2025-09-01 16:26:19'),
('9', '6', '7', '500000.00', '450', '2023-11-01 00:00:00', '2025-10-01 00:00:00', 'EXPIRED', '2023-10-22 11:18:27', '2025-08-01 08:47:00'),

('10', '3', '3', '900000.00', '500', '2020-06-01 00:00:00', '2023-06-01 00:00:00', 'EXPIRED', '2020-05-30 10:25:28', '2023-02-15 09:32:09'),
('10', '8', '8', '900000.00', '180', '2024-07-01 00:00:00', '2027-07-01 00:00:00', 'ACTIVE', '2024-06-25 16:36:19', '2025-07-01 07:30:50'),

('11', '5', '10', '800000.00', '280', '2022-02-01 00:00:00', '2024-02-01 00:00:00', 'EXPIRED', '2022-01-28 17:38:10', '2023-02-01 07:20:40'),

('12', '4', '11', '900000.00', '350', '2025-09-01 00:00:00', '2027-03-01 00:00:00', 'ACTIVE', '2025-08-20 14:45:12', '2025-09-02 17:11:32'),
('12', '4', '6', '900000.00', '350', '2025-08-01 00:00:00', '2028-01-01 00:00:00', 'ACTIVE', '2025-07-28 00:00:00', '2025-08-10 11:10:57'),

(13, 11, 8, 1100000, 280, '2023-01-01', '2026-01-01', 'ACTIVE', '2022-12-15', '2024-02-18'),

(14, 9, 15, 950000, 220, '2022-06-01', '2025-06-01', 'ACTIVE', '2022-05-20', '2024-12-30'),

(15, 10, 7, 650000, 300, '2021-09-01', '2024-09-01', 'EXPIRED', '2021-08-15', '2024-09-02'),

(16, 15, 5, 600000, 320, '2024-02-01', '2027-02-01', 'ACTIVE', '2024-01-20', '2025-01-12'),

(17, 13, 6, 750000, 350, '2023-03-01', '2026-03-01', 'ACTIVE', '2023-02-10', '2025-03-01'),

(18, 11, 8, 700000, 400, '2021-08-01', '2024-08-01', 'EXPIRED', '2021-07-15', '2024-08-02'),

(19, 15, 10, 850000, 380, '2024-05-01', '2027-05-01', 'ACTIVE', '2024-04-12', '2025-02-08'),

(20, 16, 11, 650000, 280, '2022-10-01', '2025-10-01', 'ACTIVE', '2022-09-15', '2024-09-14'),

(21, 1, 2, 750000, 220, '2020-09-01', '2023-09-01', 'EXPIRED', '2020-08-10', '2023-09-02'),

(22, 4, 3, 1200000, 300, '2022-01-01', '2026-01-01', 'ACTIVE', '2021-12-10', '2024-03-05'),
(22, 9, 14, 1200000, 500, '2023-03-01', '2027-03-01', 'ACTIVE', '2023-02-10', '2025-03-05'),
(22, 11, 3, 1200000, 750, '2021-06-01', '2025-06-01', 'EXPIRED', '2021-05-01', '2025-06-02'),
(22, 13, 14, 1200000, 1000, '2024-01-01', '2028-01-01', 'ACTIVE', '2023-12-01', '2025-03-05'),
(22, 12, 3, 1200000, 300, '2020-09-01', '2024-09-01', 'EXPIRED', '2020-08-01', '2024-09-02'),

(23, 1, 1, 1050000, 200, '2022-02-01', '2026-02-01', 'ACTIVE', '2022-01-10', '2025-03-01'),
(23, 13, 4, 1050000, 380, '2023-06-01', '2028-06-01', 'ACTIVE', '2023-05-18', '2025-03-01'),
(23, 15, 15, 1050000, 600, '2021-10-01', '2025-10-01', 'EXPIRED', '2021-09-10', '2025-10-02'),
(23, 9, 4, 1050000, 200, '2024-04-01', '2027-04-01', 'ACTIVE', '2024-03-01', '2025-03-01'),

(24, 7, 6, 950000, 450, '2021-04-01', '2024-04-01', 'EXPIRED', '2021-03-10', '2024-04-02'),

(25, 10, 7, 780000, 350, '2024-01-01', '2027-01-01', 'ACTIVE', '2023-12-15', '2025-01-20'),

(26, 15, 8, 720000, 280, '2022-07-01', '2025-07-01', 'ACTIVE', '2022-06-10', '2024-10-11'),

(27, 14, 4, 650000, 240, '2023-09-01', '2026-09-01', 'ACTIVE', '2023-08-12', '2025-02-15'),

(28, 13, 5, 900000, 400, '2021-05-01', '2024-05-01', 'EXPIRED', '2021-04-10', '2024-05-02'),

(29, 15, 10, 780000, 320, '2024-03-01', '2027-03-01', 'ACTIVE', '2024-02-15', '2024-11-18'),

(30, 15, 15, 950000, 250, '2021-01-01', '2025-01-01', 'EXPIRED', '2020-12-01', '2025-01-02'),
(30, 9, 15, 950000, 450, '2022-05-01', '2026-05-01', 'ACTIVE', '2022-04-01', '2025-03-03'),
(30, 13, 15, 950000, 700, '2024-01-01', '2028-01-01', 'ACTIVE', '2023-12-01', '2025-03-03');

-- =============================
-- BẢNG HÓA ĐƠN / INVOICE
-- =============================
CREATE TABLE invoice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    contract_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    paid_date DATETIME NULL,
    payment_method VARCHAR(50),
    transaction_code VARCHAR(100),

    CONSTRAINT fk_invoice_contract FOREIGN KEY (contract_id)
        REFERENCES contract(id),

    CONSTRAINT fk_invoice_customer FOREIGN KEY (customer_id)
        REFERENCES customer(id)
);

-- =============================
-- BẢNG CHI TIẾT HÓA ĐƠN / INVOICE DETAIL
-- =============================
CREATE TABLE invoice_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,

    CONSTRAINT fk_invoice_detail FOREIGN KEY (invoice_id)
        REFERENCES invoice(id)
);

-- =============================
-- BẢNG SỐ ĐIỆN-NƯỚC / UTILITY METER
-- =============================
CREATE TABLE utility_meter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    contract_id BIGINT NOT NULL,

    month INT NOT NULL CHECK(month BETWEEN 1 AND 12),
    year INT NOT NULL CHECK(year >= 2000),

    electricity_old INT NOT NULL DEFAULT 0,
    electricity_new INT NOT NULL DEFAULT 0,

    water_old INT NOT NULL DEFAULT 0,
    water_new INT NOT NULL DEFAULT 0,

    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_utility_contract 
        FOREIGN KEY (contract_id) REFERENCES contract(id),

    -- Một hợp đồng chỉ có 1 bộ chỉ số cho mỗi tháng/năm
    CONSTRAINT unique_meter UNIQUE (contract_id, month, year)
);
