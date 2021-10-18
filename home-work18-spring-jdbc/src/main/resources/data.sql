set schema public;
drop table if exists Dish;
drop table if exists  RECIPES;
drop table if exists  INGREDIENTS;

CREATE TABLE RECIPES
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);
insert into RECIPES(name) values ('Борщ');
insert into RECIPES(name) values ('Щи');


create table INGREDIENTS
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX IF NOt EXISTS UNIQUE_INGREDIENTS ON INGREDIENTS(name);
insert into INGREDIENTS(name) values ('Капуста');
insert into INGREDIENTS(name) values ('Свекла');
insert into INGREDIENTS(name) values ('Картофель');
insert into INGREDIENTS(name) values ('Лук');
insert into INGREDIENTS(name) values ('Говядина');
insert into INGREDIENTS(name) values ('Помидоры');

create table Dish
(
    recipes_id     INT NOT NULL,
    ingredients_id INT NOT NULL,
    weight         INT NOT NULL,
    FOREIGN KEY (recipes_id) REFERENCES RECIPES(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredients_id) REFERENCES ingredients(id) ON DELETE CASCADE
);
insert into Dish(recipes_id, ingredients_id, weight) values (1, 1,  500);
insert into Dish(recipes_id, ingredients_id, weight) values (1, 2,  300);
insert into Dish(recipes_id, ingredients_id, weight) values (1, 3,  500);
insert into Dish(recipes_id, ingredients_id, weight) values (1, 4,  200);
insert into Dish(recipes_id, ingredients_id, weight) values (1, 5,  500);
insert into Dish(recipes_id, ingredients_id, weight) values (2, 1,  500);
insert into Dish(recipes_id, ingredients_id, weight) values (2, 3,  200);
insert into Dish(recipes_id, ingredients_id, weight) values (2, 4,  300);
insert into Dish(recipes_id, ingredients_id, weight) values (2, 5,  500);
insert into Dish(recipes_id, ingredients_id, weight) values (2, 6,  300);