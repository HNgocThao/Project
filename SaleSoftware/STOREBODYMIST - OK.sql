if DB_ID('STOREBODYMIST') is not null drop database STOREBODYMIST

create database STOREBODYMIST
USE STOREBODYMIST
go

drop table POSITION
drop table EMPLOYEE
drop table PRODUCER
drop table CLASSSIFYPRODUCT
drop table PRODUCT
drop table CUSTOMER
drop table BILL
drop table DETAIL_BILL
drop table REVENUE
drop table SALE	

CREATE TABLE POSITION(
	Positionp nvarchar(20) not null,
	Salary varchar(50),
	primary key(Positionp)
);
CREATE TABLE EMPLOYEE(
    EmplID varchar(20) not null,
    EmplName nvarchar(50),
    UserName nvarchar(20),
    PassWord nvarchar(20),
    Positione nvarchar(20) not null foreign key references POSITION(Positionp),
	DayOfBirth date,
    Address nvarchar(100),
	Gender nvarchar(20),
    Phone varchar(20),
    Email varchar(50),
    --Salary varchar(50),
    primary key(EmplID)
);

CREATE TABLE PRODUCER(
	ProcerID varchar(20) not null,
    ProcerName nvarchar(50),
    Address nvarchar(50),
    Phone varchar(20),
	Email varchar(50),
    primary key(ProcerID)
);

CREATE TABLE CLASSSIFYPRODUCT(
	Classify nvarchar(20) not null,
	ClassifyID varchar(20),
    primary key(Classify)
);

CREATE TABLE PRODUCT(
	Classifyp nvarchar(20) not null foreign key references CLASSSIFYPRODUCT(Classify),
	ProcerIDp varchar(20) not null foreign key references PRODUCER(ProcerID),
    ProdID nvarchar(20) not null,
	ProdName nvarchar(50) not null,
	Quantitytotal int,
	Quantity int,
	Waranty int,
	SingleTime nvarchar(20),
	Unit nvarchar(20),
	Price varchar(20),
    primary key(ProdID)
);

CREATE TABLE CUSTOMER(
    CusID nvarchar(20) not null,
    CusName nvarchar(50),
    Address nvarchar(50),
    Phone nvarchar(20),
    primary key(CusID)
);

CREATE TABLE BILL(
    BillID nvarchar(20) not null,
    EmplID varchar(20) not null foreign key references EMPLOYEE(EmplID),
	CusIDb nvarchar(20) not null foreign key references CUSTOMER(CusID),
    BillDate date,
	BillTime varchar(50),
    TotalAmount varchar(50), -- Tong tien
    primary key (BillID)
);

CREATE TABLE DETAIL_BILL(
    BillIDb nvarchar(20) not null foreign key references BILL(BillID),
    ProdIDb nvarchar(20) not null foreign key references PRODUCT(ProdID),
    Quantity int,
    Price varchar(20),
    Amount varchar(20), -- Thanh tien
   -- primary key (BillIDb, ProdIDb)
);

-- 2 bảng tạm
CREATE TABLE REVENUE(
	TotalBill nvarchar(50),
	TotalRevenue nvarchar(100),
	Fromdate nvarchar(50),
	Todate nvarchar(50),
)

SELECT BillIDb,ProdIDb,Classifyp,ProdName,DETAIL_BILL.Quantity,DETAIL_BILL.Price,Amount INTO SALE FROM DETAIL_BILL join PRODUCT on DETAIL_BILL.ProdIDb = PRODUCT.ProdID

--------------------------------------------------------

select * from POSITION
select * from EMPLOYEE
select BillIDb,ProdIDb,Classifyp,ProdName,DETAIL_BILL.Quantity,DETAIL_BILL.Price,Amount from DETAIL_BILL join PRODUCT on DETAIL_BILL.ProdIDb = PRODUCT.ProdID
select * from BILL
select * from DETAIL_BILL
select * from PRODUCT
select * from PRODUCER
select * from CLASSSIFYPRODUCT
select * from SALE	
select * from CUSTOMER

delete from CLASSSIFYPRODUCT
delete from PRODUCER
delete from PRODUCT
delete from POSITION
delete from EMPLOYEE
delete from PRODUCT
delete from CUSTOMER
delete from BILL
delete from DETAIL_BILL


delete from revenue


-- insert data
insert into POSITION(Positionp, Salary)
values(N'Admin', '100,000,000 VND'),
	  (N'Employee', '7,000,000 VND')

insert into EMPLOYEE(EmplID,EmplName,UserName,PassWord,Positione,DayOfBirth,Address,Gender,Phone,Email)
values('M001',N'Huỳnh Ngọc Thảo','Admin','1',N'Admin',CAST(N'1994-07-15' AS Date),N'Đà Nẵng', N'Male', '0763869084', 'thao@gmail.com'),
      ('M002',N'Đăng Minh','Admin','123',N'Admin',CAST(N'1990-09-21' AS Date),N'Đà Nẵng', N'Male', '0912999123', 'minh@gmail.com'),
	  ('M003',N'Nguyễn Quốc Oai','oai','123',N'Employee',CAST(N'1995-02-04' AS Date),N'Đà Nẵng', N'Male', '0912999123', 'oainguyen@gmail.com'),
	  ('M004',N'Trần Thanh','thanh','123',N'Employee',CAST(N'1996-04-14' AS Date),N'Đà Nẵng', N'Female', '0912999123', 'thanhn@gmail.com')
	  

insert into PRODUCER(ProcerID, ProcerName, Address, Phone, Email)
values('BBW', N'Bath & Bodyworks','USA','0123456','bbw@gmail.com'),
	  ('VS', N'Victoria s Secret','USA','0123456','vs@gmail.com')

insert into CLASSSIFYPRODUCT(Classify, ClassifyID)
values(N'Body mist', 'BM'),
	  (N'Body lotion', 'BL'),
	  (N'Body scrub', 'BS'),
	  (N'Shower Gel', 'SG')

insert into PRODUCT(Classifyp, ProcerIDp, ProdID, ProdName, Quantitytotal, Quantity, Waranty, SingleTime, Unit, Price)
values('Body mist', 'BBW', N'BBWBM01', N'At The Beach', 50, 47, 3, 'Year', 'Chai', '260,000 VND'),
      ('Body mist', 'BBW', N'BBWBM02', N'Bonfire Pash', 50, 49, 3, 'Year', 'Chai', '260,000 VND'),
	  ('Body mist', 'BBW', N'BBWBM03', N'Beautifull Day', 50, 48, 3, 'Year', 'Chai', '260,000 VND'),
	  ('Body mist', 'BBW', N'BBWBM04', N'Berry Waffle Cone', 50, 48, 3, 'Year', 'Chai', '260,000 VND'),
	  ('Body mist', 'BBW', N'BBWBM05', N'Black Raspberry Vanilla', 50, 45, 3, 'Year', 'Chai', '260,000 VND'),

	  ('Body mist', 'VS', N'VSBM01', N'Amber Romance', 70, 67, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM02', N'Aqua Kiss', 70, 68, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM03', N'Bare Vanilla', 70, 67, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM04', N'Cherry Blossoming', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM05', N'Coconut Passion', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM06', N'Early Morning Sun', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM07', N'Horizon In Bloom', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM08', N'Island Away', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM09', N'Temptation', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),
	  ('Body mist', 'VS', N'VSBM10', N'Velvet Petals', 70, 70, 3, 'Year', 'Chai', '280,000 VND'),

	  ('Body lotion', 'BBW', N'BBWBL01', N'Champange Toast', 60, 59, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL02', N'Dark Kiss', 60, 59, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL03', N'Hope', 60, 59, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL04', N'Little Black Party Dress', 60, 59, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL05', N'Japanese Cherry Blossom', 60, 59, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL06', N'Joy', 60, 57, 3, 'Year', 'Chai', '230,000 VND'),
	  ('Body lotion', 'BBW', N'BBWBL07', N'Peace', 60, 57, 3, 'Year', 'Chai', '230,000 VND'),
	  
	  ('Body scrub', 'BBW', N'BBWSC01', N'A Thousand Wishes', 30, 28, 3, 'Year', 'Chai', '240,000 VND'),
	  ('Body scrub', 'BBW', N'BBWSC02', N'Gingham', 30, 28, 3, 'Year', 'Chai', '240,000 VND'),
	  ('Body scrub', 'BBW', N'BBWSC03', N'In The Stars', 30, 26, 3, 'Year', 'Chai', '240,000 VND'),
	  ('Body scrub', 'BBW', N'BBWSC04', N'Into The Night', 30, 25, 3, 'Year', 'Chai', '240,000 VND'),
	  ('Body scrub', 'BBW', N'BBWSC05', N'Pure Wonder', 30, 28, 3, 'Year', 'Chai', '240,000 VND'),

	  ('Shower Gel', 'BBW', N'BBWSG01', N'Board Walk Taffy', 40, 39, 3, 'Year', 'Chai', '210,000 VND'),
	  ('Shower Gel', 'BBW', N'BBWSG02', N'Champagne Sprinkles', 40, 38, 3, 'Year', 'Chai', '210,000 VND')

insert into CUSTOMER(CusID, CusName, Address, Phone)
values(N'KH00', N'No Name', N'No Address', 'No Phone'),
	  (N'KH01', N'Hoàng Đức Bình', N'Đà Nẵng', '0905223445'),
	  (N'KH02', N'Lê Thanh Hiếu', N'Đà Nẵng', '0913486553'),
	  (N'KH03', N'Phạm Thị Tuyền', N'Quảng Trị', '0935925653'),
	  (N'KH04', N'Trần Thanh Sơn', N'Quảng Nam', '0763869084'),
	  (N'KH05', N'Nguyễn Sang', N'Huế', '0121442687'),
	  (N'KH06', N'Nguyễn Hải Nam', N'Quảng Trị', '0905714809'),
	  (N'KH07', N'Trần Phúc Ngụ', N'Quảng Trị', '0939085469'),
	  (N'KH08', N'Đinh Trường Quốc', N'Bình Dương', '01665045931'),
	  (N'KH09', N'Nguyễn Hữu Thọ', N'Hà Tĩnh', '0979128959'),
	  (N'KH10', N'Trịnh Công Kha', N'Đà Nẵng', '0934785781'),
	  (N'KH11', N'Phạm Thọ', N'Đà Nẵng', '0708049481'),
	  (N'KH12', N'Trương Văn Điệp', N'Đà Nẵng', '0905062729'),
	  (N'KH13', N'Nguyễn Anh Huy', N'Đà Nẵng', '0905527036'),
	  (N'KH14', N'Nguyễn Văn Hưu', N'Quảng Bình', '0934864425'),
	  (N'KH15', N'Nguyễn Thanh Phong', N'Quảng Nam', '0983712732')

insert into BILL(BillID, EmplID,CusIDb, BillDate, BillTime, TotalAmount)
values(N'HĐ01', N'M001','KH01',CAST(N'2022-01-10' AS Date), N'08:42:14','1,620,000 VND'),
	  (N'HĐ02', N'M001','KH10',CAST(N'2022-01-17' AS Date), N'07:47:14','1,860,000 VND'),
	  (N'HĐ03', N'M002','KH03',CAST(N'2022-02-06' AS Date), N'08:15:10','920,000 VND'),
	  (N'HĐ04', N'M002','KH06',CAST(N'2022-02-15' AS Date), N'09:49:19','960,000 VND'),
	  (N'HĐ05', N'M003','KH11',CAST(N'2022-02-28' AS Date), N'10:20:11','210,000 VND'),
	  (N'HĐ06', N'M003','KH02',CAST(N'2022-02-28' AS Date), N'11:25:16','2,270,000 VND'),
	  (N'HĐ07', N'M003','KH09',CAST(N'2022-03-07' AS Date), N'09:14:04','1,650,000 VND'),
	  (N'HĐ08', N'M003','KH00',CAST(N'2022-03-24' AS Date), N'09:56:16','1,200,000 VND'),
	  (N'HĐ09', N'M003','KH00',CAST(N'2022-04-09' AS Date), N'10:18:14','520,000 VND'),
	  (N'HĐ10', N'M001','KH13',CAST(N'2022-04-10' AS Date), N'11:12:13','1,170,000 VND')

insert into DETAIL_BILL(BillIDb, ProdIDb, Quantity, Price, Amount)
values(N'HĐ01', N'BBWBM01', 3,'260,000 VND', '780,000 VND'),
	  (N'HĐ01', N'VSBM01', 3,'280,000 VND', '840,000 VND'),
	  (N'HĐ02', N'BBWBM02', 1,'260,000 VND', '260,000 VND'),
	  (N'HĐ02', N'BBWBM03', 2,'260,000 VND', '520,000 VND'),
	  (N'HĐ02', N'BBWBM04', 2,'260,000 VND', '520,000 VND'),
	  (N'HĐ02', N'VSBM02', 2,'280,000 VND', '560,000 VND'),
	  (N'HĐ03', N'BBWBL01', 1,'230,000 VND', '230,000 VND'),
	  (N'HĐ03', N'BBWBL02', 1,'230,000 VND', '230,000 VND'),
	  (N'HĐ03', N'BBWBL03', 1,'230,000 VND', '230,000 VND'),
	  (N'HĐ03', N'BBWBL04', 1,'230,000 VND', '230,000 VND'),
	  (N'HĐ04', N'BBWSC01', 2,'240,000 VND', '480,000 VND'),
	  (N'HĐ04', N'BBWSC02', 2,'240,000 VND', '480,000 VND'),
	  (N'HĐ05', N'BBWSG01', 1,'210,000 VND', '210,000 VND'),
	  (N'HĐ06', N'BBWBL05', 1,'230,000 VND', '230,000 VND'),
	  (N'HĐ06', N'BBWSG02', 2,'210,000 VND', '420,000 VND'),
	  (N'HĐ06', N'BBWBM05', 3,'260,000 VND', '780,000 VND'),
	  (N'HĐ06', N'VSBM03', 3,'280,000 VND', '840,000 VND'),
	  (N'HĐ07', N'BBWSC03', 4,'240,000 VND', '960,000 VND'),
	  (N'HĐ07', N'BBWBL06', 3,'230,000 VND', '690,000 VND'),
	  (N'HĐ08', N'BBWSC04', 5,'240,000 VND', '1,200,000 VND'),
	  (N'HĐ09', N'BBWBM05', 2,'260,000 VND', '520,000 VND'),
	  (N'HĐ10', N'BBWBL07', 3,'230,000 VND', '690,000 VND'),
	  (N'HĐ10', N'BBWSC05', 2,'240,000 VND', '480,000 VND')


---------------------------------------------------------------------------
