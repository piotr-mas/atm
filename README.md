ATM machine

should initialize with €1500 made up of 10 x €50s, 30 x €20s, 30 x €10s and 20 x €5s

should not dispense funds if the pin is incorrect,

cannot dispense more money than it holds,

cannot dispense more funds than customer have access to

should not expose the customer balance if the pin is incorrect,

should only dispense the exact amounts requested,

should dispense the minimum number of notes per withdrawal, 

***H2***
For the purpose of this project H2 DB is implemented with stored data on a hard drive
Please find more details in application.yml 
Please access the H2 console on http://localhost:8080/h2-console/
    User name: sa
    Password: password
Please run this query to insert a new records into H2 database
    INSERT INTO ATM_USERS VALUES (123456789, 800, 200, '1234');
    INSERT INTO ATM_USERS VALUES (987654321, 1230, 150, '4321');
    INSERT INTO CASH_MACHINE_NOTES VALUES (1111, 10, 30, 30, 20, '1111');
    
    DROP TABLE ATM_USERS;
    DROP TABLE CASH_MACHINE_NOTES ;