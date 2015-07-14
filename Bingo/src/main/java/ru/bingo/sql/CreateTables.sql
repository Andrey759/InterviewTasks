CREATE TABLE test.users(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(250) NOT NULL,
    pass VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE test.authorities(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    role VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);