DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_id INT(250) NOT NULL,
  price float NOT NULL
);

INSERT INTO products (product_id, price) VALUES
  (1, 50.00),
  (2, 75.00),
  (3, 100.00),
  (4, 500.00),
  (5, 1000.00);
