/*
 table to store products
*/
CREATE TABLE product (
     id INT NOT NULL AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     text VARCHAR(2000)  NOT NULL,
     price FLOAT NOT NULL,
     creationtime datetime default null,
     PRIMARY KEY (id)
);

/*
    table to store reviews related to products
*/
CREATE TABLE review (
    id INT NOT NULL AUTO_INCREMENT,
    fk_product INT NOT NULL,
    text VARCHAR(2000)  NOT NULL,
    star INT,
    creationtime datetime default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_review_product FOREIGN KEY (fk_product) REFERENCES product(id)
);

/*
    table to store comments related to reviews
*/
CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT,
    fk_review INT NOT NULL,
    text VARCHAR(2000)  NOT NULL,
    creationtime datetime default null,
    PRIMARY KEY (id),
    CONSTRAINT fk_comment_review FOREIGN KEY (fk_review) REFERENCES review(id)
);