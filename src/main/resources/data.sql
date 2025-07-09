DROP TABLE IF EXISTS User_Account;

CREATE TABLE IF NOT EXISTS User_Account -- Root-Entity. Do not use 'User' as table name! It seems to be reserved by something else.
(
    id       long primary key AUTO_INCREMENT,
    name     varchar(100) NOT NULL,
    password varchar(72)  NOT NULL,
    email    varchar(255) NULL
);
CREATE UNIQUE INDEX unique_idx_username ON User_Account (name);
CREATE UNIQUE INDEX unique_idx_email ON User_Account (email);

CREATE TABLE IF NOT EXISTS Authority -- Sub-Entity of User_Account.
(
    role         varchar(100) NOT NULL,
    user_account long         NOT NULL,
    PRIMARY KEY (role, user_account),
    FOREIGN KEY (user_account) REFERENCES User_Account (id)
);

-- CREATE TABLE IF NOT EXISTS Feedback (
--     id long primary key AUTO_INCREMENT,
--     author varchar(100) NOT NULL,
--     modul varchar(100) NOT NULL,
--     feedback_content varchar(5000) NOT NULL,
--     FOREIGN KEY(author) REFERENCES User_Account(name)
-- );


-- DROP TABLE IF EXISTS Authority;
-- DROP TABLE IF EXISTS Feedback;
-- DROP TABLE IF EXISTS User_Account;
--
-- CREATE TABLE IF NOT EXISTS User_Account (
--     id       long primary key AUTO_INCREMENT,
--     name     varchar(100) NOT NULL,
--     password varchar(72)  NOT NULL,
--     email    varchar(255) NULL
--     );
-- CREATE UNIQUE INDEX unique_idx_username ON User_Account (name);
-- CREATE UNIQUE INDEX unique_idx_email ON User_Account (email);
--
-- CREATE TABLE IF NOT EXISTS Authority (
--     role         varchar(100) NOT NULL,
--     user_account long         NOT NULL,
--     PRIMARY KEY (role, user_account),
--     FOREIGN KEY (user_account) REFERENCES User_Account (id)
--     );
--
-- CREATE TABLE IF NOT EXISTS Feedback (
--     id long primary key AUTO_INCREMENT,
--     author_id long NOT NULL,
--     module_name varchar(100) NOT NULL,
--     feedback_content varchar(5000) NOT NULL,
--     created_at timestamp,
--     FOREIGN KEY(author_id) REFERENCES User_Account(id)
-- );

