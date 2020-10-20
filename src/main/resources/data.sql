INSERT INTO BANK_ACCOUNT (balance) values (3000),(1000),(4500);

INSERT INTO OPERATION (amount, date, type, bank_account_id) values
    (200,'2020-01-01 10:00:00.000','DEPOSIT',1),
    (100,'2020-01-01 10:00:00.000','WITHDRAWAL',2),
    (2000,'2020-01-01 10:00:00.000','DEPOSIT',3);