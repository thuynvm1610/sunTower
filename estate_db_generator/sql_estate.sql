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
('Nam Từ Liêm');

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
('ttt1612', '$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS', 'Trịnh Thu Trang', '0902000010', 'trang.tt@gmail.com', 'staff_011', 'STAFF', '2023-03-12', '2025-02-25');

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
('Tràng An Complex', 1, 'Ngọc Khánh', 'Phạm Văn Đồng', 29, 3, 4800, 'DONG_BAC', 'A', 900000, 1400000, 1800000, 90000, 18000, 5000, 90000000, 'https://trangancomplex.vn', 'trang-an-complex.jpg', '2019-08-08', '2025-01-29');

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
('ThietKeXanh','$2a$10$9GOPNTSC5oXEiRj4u3nyEeCHBduPyco5u0QGfhZKNYfP2.QpGuXkS','Công ty TNHH Thiết kế Xanh', '0903000008', 'contact@thietkexanh.vn', 'CUSTOMER', '2023-02-10', '2024-12-15');

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
(12, 350, '2019-08-15', '2025-01-29');

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
(12, 11), (12, 6);

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
(1, 11), (1,2),
(3, 5), (3, 2), (3, 3),
(4, 3), (4, 4), (4, 11), (4, 6),
(5, 8), (5, 10), (5, 6),
(6, 9), (6, 7),
(7, 6), (7, 7), (7, 5),
(8, 8);

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
('12', '4', '6', '900000.00', '350', '2025-08-01 00:00:00', '2028-01-01 00:00:00', 'ACTIVE', '2025-07-28 00:00:00', '2025-08-10 11:10:57');

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
