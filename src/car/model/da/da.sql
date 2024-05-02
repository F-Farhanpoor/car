    CREATE TABLE CAR(
                       ID NUMBER(12) PRIMARY KEY,
                       NAME NVARCHAR2(30),
                       COLOR NVARCHAR2(30),
                       MAN_DATE DATE,
                       STATUS NUMBER(1) DEFAULT 1
);

    CREATE SEQUENCE CAR_SEQ START WITH 1 INCREMENT BY 1;