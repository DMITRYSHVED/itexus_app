insert into product_type (name)
values ('Shoes'),
       ('Sweaters');

insert into product (name, description, cost, image_id, product_type_id)
values ('Nike air force 1', 'White/Premium leather', 100, null, 1),
       ('Miracles sweater', 'Cotton 100%', 80, null, 2);

insert into sell_position (product_id, size, quantity, active)
values (1, '9.5US', 12, true),
       (2, 'M', 0, false);