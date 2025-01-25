-- H2 2.3.230;
;             
CREATE USER IF NOT EXISTS "SA" SALT 'eeb68ed8ee1de1b1' HASH '22718e5ea32ba415db1e2998efa8c1999a3d68653f82e09e8ae2d3de91b1043e' ADMIN;         
CREATE CACHED TABLE "PUBLIC"."CLIENTES"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 5) NOT NULL,
    "NOMBRE" CHARACTER VARYING(100),
    "DIRECCION" CHARACTER VARYING(255),
    "TELEFONO" CHARACTER VARYING(20)
);   
ALTER TABLE "PUBLIC"."CLIENTES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_6" PRIMARY KEY("ID");     
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.CLIENTES;
INSERT INTO "PUBLIC"."CLIENTES" VALUES
(1, 'Joel', 'manco inca', '99999999'),
(2, 'Jorge', 'Manco inca', '94975487'),
(3, 'rossie', 'pampacancha', '99449494'),
(4, 'Rossi', 'pampacancha', '902995586'); 
CREATE CACHED TABLE "PUBLIC"."CONEXIONES"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 52) NOT NULL,
    "NOMBRE_CONEXION" CHARACTER VARYING(100),
    "TIPO_CONEXION" CHARACTER VARYING(50),
    "MEDIDAS_CORTE" CHARACTER VARYING(100),
    "MEDIDAS_CAMPANAS" CHARACTER VARYING(100),
    "MEDIDAS_DE_CORTE_DE_SALIDAS" CHARACTER VARYING(100),
    "MEDIDAS_DE_CAMPANAS_DE_SALIDAS" CHARACTER VARYING(100),
    "TIPO_USO" CHARACTER VARYING(20) NOT NULL,
    "PRECIO" DECIMAL(10, 2)
);       
ALTER TABLE "PUBLIC"."CONEXIONES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("ID");   
-- 51 +/- SELECT COUNT(*) FROM PUBLIC.CONEXIONES;             
INSERT INTO "PUBLIC"."CONEXIONES" VALUES
(1, 'Codo 6" x 45 Gris', 'Codo', '34 cm', 'null', 'null', 'null', 'alcantarillado', 9.00),
(2, 'Tee de 4"x 2"', 'Tee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(3, 'Base de 8"x 8"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(4, 'Base de 8"x 6"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(5, 'Base de 8"x 4"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(6, 'Base de 8"x 4"(110mm) Naranja', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(7, 'Base de 6"x 6"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(8, 'Base de 6"x 4"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(9, 'Base de 6"x 4" (110mm)', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(10, 'Base de 6"x 4" (110mm) en Yee', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(11, 'Base de 6"x 2"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(12, 'Base de 4"(110mm) x 4"(110mm) Naranja', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(13, 'Base de 4" x 4"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(14, 'Base de 4" x 2"', 'Base', '14 cm', NULL, NULL, NULL, 'alcantarillado', 0.00),
(15, 'Base de 4"(110mm) Naranja x 2"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(16, 'Base de 2" x 2"', 'Base', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(17, 'Yee de 8"x 8"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(18, 'Yee de 8"x 6"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(19, 'Yee de 8"x 4"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(20, 'Yee de 6"x 6"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(21, 'Yee de 6"x 4"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(22, 'Yee de 6"x 4" (110mm)', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(23, 'Yee de 6"x 2"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(24, 'Yee de 4"(110mm) x 4"(110mm) Naranja', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(25, 'Yee de 4"x 2"', 'Yee', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(26, 'Union de 8" (200mm) Naranja', 'Union', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(27, 'Union de 6" (160mm) Naranja', 'Union', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(28, 'Union de 4" (110mm) Naranja', 'Union', NULL, NULL, NULL, NULL, 'alcantarillado', NULL),
(29, 'Union de 4" Desague Gris', 'Union', NULL, NULL, NULL, NULL, 'desague', NULL),
(30, 'Union de 2" Desague Gris', 'Union', NULL, NULL, NULL, NULL, 'desague', NULL),
(31, 'Union de 4" (SAP) Gris', 'Union', NULL, NULL, NULL, NULL, 'electrica', NULL),
(32, 'Union de 3" (SAP) Gris', 'Union', NULL, NULL, NULL, NULL, 'electrica', NULL),
(33, 'Union de 2" (SAP) Gris', 'Union', NULL, NULL, NULL, NULL, 'electrica', NULL),
(34, 'Union de 8" Naranja', 'Union', '25', NULL, NULL, NULL, 'alcantarillado', 9.00),
(35, 'Union de 6" (Agua) Naranja', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(36, 'Union de 4" (Agua) Naranja', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(37, 'Union de 3" (Agua) Gris', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(38, 'Union de 2 1/2" (Agua) Gris', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(39, 'Union de 2" (Agua) Gris', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(40, 'Union de 1 1/2" (Agua) Gris', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(41, 'Union de 1" (Agua) Gris', 'Union', NULL, NULL, NULL, NULL, 'agua', NULL),
(42, 'Curva de 4" SAP', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(43, 'Curva de 3" SAP', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(44, 'Curva de 2" SAP', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(45, 'Curva de 1 1/2" SAP', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(46, 'Curva de 1 1/4" SAP', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(47, 'Curva de 1" Agua', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(48, 'Curva de 3/4" Agua', 'Curva', NULL, NULL, NULL, NULL, 'electrica', NULL),
(49, 'Codo 6"x 90 Naranja', 'Codo', '37.3 cm', NULL, NULL, NULL, 'alcantarillado', 10.00);
INSERT INTO "PUBLIC"."CONEXIONES" VALUES
(50, 'Codo 6"x45 Naranja', 'Codo', '31.3cm', NULL, NULL, NULL, 'agua', 9.00),
(51, 'Codo 2"x 45', 'Codo', '15.8 cm', 'NULL', 'NULL', 'NULL', 'agua', 2.50);        
CREATE CACHED TABLE "PUBLIC"."PEDIDOS"(
    "ID_PEDIDO" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 3) NOT NULL,
    "ESTADO_PEDIDO" CHARACTER VARYING(50),
    "OBSERVACIONES" CHARACTER VARYING,
    "PRECIO_TOTAL" DECIMAL(10, 2),
    "CLIENTE" CHARACTER VARYING(50),
    "FECHA_PEDIDO" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);            
ALTER TABLE "PUBLIC"."PEDIDOS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID_PEDIDO");               
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.PEDIDOS; 
INSERT INTO "PUBLIC"."PEDIDOS" VALUES
(1, 'En proceso', 'Observacion del pedido', 460.00, 'Pelo lacio', TIMESTAMP '2025-01-19 19:55:14'),
(2, 'Pendiente', 'Pedido nuevo', 1080.00, 'Cliente Nuevo', TIMESTAMP '2025-01-19 20:22:16');      
ALTER TABLE "PUBLIC"."CONEXIONES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_33" UNIQUE NULLS DISTINCT ("NOMBRE_CONEXION");          
