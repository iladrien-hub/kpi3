DELETE FROM `product_product_options`;
DELETE FROM `hibernate_sequence`;
DELETE FROM `cart_items`;
DELETE FROM `cart_item`;
DELETE FROM `cart`;
DELETE FROM `order_data`;
DELETE FROM `product_option`;

--
-- Дамп данных таблицы `product_option`
--

INSERT INTO `product_option` VALUES
(18, '1.00', '1', 17),
(25, '2.50', '300g', 24),
(26, '11.99', '1kg', 24),
(28, '1.10', '1', 27),
(41, '9.99', '1kg', 40);

--
-- Дамп данных таблицы `order_data`
--

INSERT INTO `order_data` VALUES
(150, 'Korsun', '2021-05-31 19:36:05.594000', 'mail@gmail.com', 'Serhii', 2);

--
-- Дамп данных таблицы `cart`
--

INSERT INTO `cart` VALUES
(143, '8ec4f838-06a6-4f2b-9b9b-dc4ddb691d5e', NULL),
(144, 'a3e06e9a-1645-43a5-8a4c-a53e8e1dc002', NULL),
(148, '4f3b8ccd-4831-44b5-8106-3cf0573dd0bf', 150);

--
-- Дамп данных таблицы `cart_item`
--

INSERT INTO `cart_item` VALUES
(145, '9.99', 1, 41),
(146, '9.99', 12, 41),
(147, '9.99', 1, 41),
(149, '11.99', 1, 26);

--
-- Дамп данных таблицы `cart_items`
--

INSERT INTO `cart_items` VALUES
(144, 145),
(144, 146),
(144, 147),
(148, 149);

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` VALUES
(151);

--
-- Дамп данных таблицы `product_product_options`
--

INSERT INTO `product_product_options` VALUES
(17, 18),
(24, 25),
(24, 26),
(27, 28),
(40, 41);
