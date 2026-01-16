
-- USUARIOS
INSERT INTO users (name, email, password, created_at, deleted) 
VALUES 
  ('Juan Pérez', 'juan@email.com', 'password123', NOW(), false),
  ('María García', 'maria@email.com', 'password123', NOW(), false),
  ('Carlos López', 'carlos@email.com', 'password123', NOW(), false);

-- CATEGORÍAS
INSERT INTO categories (name, description, created_at, deleted) 
VALUES 
  ('Electrónicos', 'Dispositivos electrónicos y gadgets', NOW(), false),
  ('Gaming', 'Productos para gamers y videojuegos', NOW(), false),
  ('Oficina', 'Equipos de oficina y trabajo', NOW(), false),
  ('Libros', 'Libros y material de lectura', NOW(), false),
  ('Programación', 'Libros y recursos de programación', NOW(), false),
  ('Educación', 'Material educativo en general', NOW(), false),
  ('Diseño', 'Equipos para diseño gráfico', NOW(), false);



INSERT INTO products (name, price, stock, description, user_id, created_at, deleted) 
VALUES ('Laptop Gaming', 1200.00, 5, 'Laptop de alto rendimiento para gaming', 1, NOW(), false);

INSERT INTO product_categories (product_id, category_id) 
VALUES 
  (1, 1),  
  (1, 2), 
  (1, 3);  


INSERT INTO products (name, price, stock, description, user_id, created_at, deleted) 
VALUES ('Mouse Inalámbrico', 45.99, 20, 'Mouse inalámbrico de precisión', 1, NOW(), false);

INSERT INTO product_categories (product_id, category_id) 
VALUES 
  (2, 1),  
  (2, 3);  


INSERT INTO products (name, price, stock, description, user_id, created_at, deleted) 
VALUES ('Monitor 4K', 599.00, 8, 'Monitor 4K de 27 pulgadas', 2, NOW(), false);

INSERT INTO product_categories (product_id, category_id) 
VALUES 
  (3, 1),  
  (3, 2),  
  (3, 7);  


INSERT INTO products (name, price, stock, description, user_id, created_at, deleted) 
VALUES ('Teclado Mecánico', 120.50, 15, 'Teclado mecánico RGB', 2, NOW(), false);

INSERT INTO product_categories (product_id, category_id) 
VALUES 
  (4, 2),  
  (4, 3);  


INSERT INTO products (name, price, stock, description, user_id, created_at, deleted) 
VALUES ('Clean Code: A Handbook of Agile Software Craftsmanship', 65.00, 12, 'Guía completa para escribir código limpio', 3, NOW(), false);

INSERT INTO product_categories (product_id, category_id) 
VALUES 
  (5, 4),  
  (5, 5),  
  (5, 6);  


SELECT 'USUARIOS' as 'DATOS';
SELECT id, name, email FROM users;

SELECT 'CATEGORÍAS' as 'DATOS';
SELECT id, name, description FROM categories;

SELECT 'PRODUCTOS' as 'DATOS';
SELECT p.id, p.name, p.price, p.stock, u.name as owner_name 
FROM products p 
JOIN users u ON p.user_id = u.id;

SELECT 'RELACIONES PRODUCTO-CATEGORÍAS' as 'DATOS';
SELECT p.id, p.name, c.name as category_name 
FROM product_categories pc
JOIN products p ON pc.product_id = p.id
JOIN categories c ON pc.category_id = c.id
ORDER BY p.id, c.name;
