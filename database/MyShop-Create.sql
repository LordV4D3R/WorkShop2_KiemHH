-- =============================================
-- Workshop 2 - MyShop Database Creation Script
-- Author: MindyCoding by Tran
-- Description: Creates MyShop database with Mobiles and Users tables
-- =============================================

-- Create Database
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'MyShop')
BEGIN
    CREATE DATABASE MyShop;
    PRINT 'Database MyShop created successfully.';
END
ELSE
BEGIN
    PRINT 'Database MyShop already exists.';
END
GO

USE MyShop;
GO

-- =============================================
-- Create Mobiles Table
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Mobiles]') AND type in (N'U'))
BEGIN
    CREATE TABLE Mobiles (
        mobileId VARCHAR(10) PRIMARY KEY,
        description VARCHAR(250) NOT NULL,
        price FLOAT NOT NULL,
        mobileName VARCHAR(20) NOT NULL,
        yearOfProduction INT NOT NULL,
        quantity INT NOT NULL,
        notSale BIT DEFAULT 0  -- 0: sale, 1: not for sale
    );
    PRINT 'Mobiles table created successfully.';
END
ELSE
BEGIN
    PRINT 'Mobiles table already exists.';
END
GO

-- =============================================
-- Create Users Table
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Users]') AND type in (N'U'))
BEGIN
    CREATE TABLE Users (
        userId VARCHAR(20) PRIMARY KEY,
        password INT NOT NULL,
        fullName VARCHAR(50) NOT NULL,
        role INT NOT NULL  -- 0: user, 1: staff
    );
    PRINT 'Users table created successfully.';
END
ELSE
BEGIN
    PRINT 'Users table already exists.';
END
GO

-- =============================================
-- Insert Sample Users
-- =============================================
DELETE FROM Users;
GO

INSERT INTO Users (userId, password, fullName, role) VALUES
-- Staff Accounts (role = 1)
('admin', 1, 'Administrator', 1),
('staff01', 1, 'Staff Member One', 1),
('staff02', 1, 'Staff Member Two', 1),

-- User Accounts (role = 0)
('user01', 1, 'John Doe', 0),
('user02', 1, 'Jane Smith', 0),
('user03', 1, 'Bob Johnson', 0);
GO

PRINT 'Sample users inserted successfully.';

-- =============================================
-- Insert Sample Mobiles
-- =============================================
DELETE FROM Mobiles;
GO

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale) VALUES
-- iPhone Series
('IP13PM', 'iPhone 13 Pro Max 256GB - Pacific Blue, A15 Bionic chip, ProMotion display', 1299.99, 'iPhone 13 Pro Max', 2021, 50, 0),
('IP13P', 'iPhone 13 Pro 128GB - Graphite, Triple camera system, Super Retina XDR', 1099.99, 'iPhone 13 Pro', 2021, 45, 0),
('IP13', 'iPhone 13 128GB - Midnight, Dual camera, Ceramic Shield', 899.99, 'iPhone 13', 2021, 80, 0),
('IP12', 'iPhone 12 64GB - White, A14 Bionic, 5G capable', 799.99, 'iPhone 12', 2020, 100, 0),
('IP11', 'iPhone 11 64GB - Black, Dual camera, Liquid Retina display', 599.99, 'iPhone 11', 2019, 60, 0),

-- Samsung Galaxy Series
('SS21U', 'Samsung Galaxy S21 Ultra 5G 128GB - Phantom Black, 108MP camera', 1199.99, 'Galaxy S21 Ultra', 2021, 30, 0),
('SS21P', 'Samsung Galaxy S21 Plus 256GB - Phantom Violet, 64MP telephoto', 999.99, 'Galaxy S21 Plus', 2021, 40, 0),
('SS21', 'Samsung Galaxy S21 5G 128GB - Phantom Gray, Snapdragon 888', 799.99, 'Galaxy S21', 2021, 70, 0),
('SSS20', 'Samsung Galaxy S20 FE 128GB - Cloud Navy, 120Hz display', 699.99, 'Galaxy S20 FE', 2020, 55, 0),

-- Xiaomi Series
('XIAO11', 'Xiaomi Mi 11 128GB - Midnight Gray, Snapdragon 888, 108MP camera', 749.99, 'Mi 11', 2021, 45, 0),
('XIAO11L', 'Xiaomi Mi 11 Lite 5G 128GB - Truffle Black, 64MP AI camera', 399.99, 'Mi 11 Lite 5G', 2021, 60, 0),
('XIAPX5', 'Xiaomi Poco X5 Pro 256GB - Horizon Blue, 108MP HM2 sensor', 349.99, 'Poco X5 Pro', 2023, 80, 0),

-- OnePlus Series
('ONE9P', 'OnePlus 9 Pro 256GB - Morning Mist, Hasselblad camera, 120Hz', 969.99, 'OnePlus 9 Pro', 2021, 25, 0),
('ONE9', 'OnePlus 9 128GB - Winter Mist, Snapdragon 888, 50MP Sony sensor', 729.99, 'OnePlus 9', 2021, 35, 0),
('ONENT', 'OnePlus Nord 2T 128GB - Gray Shadow, MediaTek Dimensity 1300', 399.99, 'Nord 2T', 2022, 50, 0),

-- Google Pixel Series
('PIX6P', 'Google Pixel 6 Pro 128GB - Stormy Black, Google Tensor chip', 899.99, 'Pixel 6 Pro', 2021, 30, 0),
('PIX6', 'Google Pixel 6 128GB - Sorta Seafoam, Magic Eraser feature', 699.99, 'Pixel 6', 2021, 40, 0),

-- OPPO Series
('OPFIND', 'OPPO Find X5 Pro 256GB - Ceramic White, Hasselblad camera', 1099.99, 'Find X5 Pro', 2022, 20, 0),
('OPRENO8', 'OPPO Reno8 Pro 256GB - Glazed Black, 50MP Sony IMX766 sensor', 649.99, 'Reno8 Pro', 2022, 45, 0),

-- Discontinued/Not for Sale
('IP8', 'iPhone 8 64GB - Space Gray, Discontinued model', 449.99, 'iPhone 8', 2017, 10, 1),
('SSS10', 'Samsung Galaxy S10 128GB - Prism Black, Discontinued', 399.99, 'Galaxy S10', 2019, 5, 1);
GO

PRINT 'Sample mobiles inserted successfully.';

-- =============================================
-- Verify Data
-- =============================================
PRINT '';
PRINT '========== USERS ==========';
SELECT * FROM Users ORDER BY role DESC, userId;

PRINT '';
PRINT '========== MOBILES ==========';
SELECT mobileId, mobileName, price, quantity, notSale FROM Mobiles ORDER BY price DESC;

PRINT '';
PRINT 'MyShop database setup complete!';
PRINT 'Test accounts:';
PRINT '  Staff: admin/1 or staff01/1';
PRINT '  User:  user01/1 or user02/1';
GO