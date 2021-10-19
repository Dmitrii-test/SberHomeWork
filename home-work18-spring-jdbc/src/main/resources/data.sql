set schema public;
drop table if exists Dish;
drop table if exists  RECIPES;
drop table if exists  INGREDIENTS;

CREATE TABLE RECIPES
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);
insert into RECIPES(name) values ('борщ');
insert into RECIPES(name) values ('щи');


create table INGREDIENTS
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX IF NOt EXISTS UNIQUE_INGREDIENTS ON INGREDIENTS(name);
insert into INGREDIENTS(name) values ('капуста');
insert into INGREDIENTS(name) values ('свекла');
insert into INGREDIENTS(name) values ('картофель');
insert into INGREDIENTS(name) values ('лук');
insert into INGREDIENTS(name) values ('говядина');
insert into INGREDIENTS(name) values ('помидоры');

create table DISH
(
    recipes_id     INT NOT NULL,
    ingredients_id INT NOT NULL,
    weight         INT NOT NULL,
    FOREIGN KEY (recipes_id) REFERENCES RECIPES(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredients_id) REFERENCES ingredients(id) ON DELETE CASCADE
);
insert into DISH(recipes_id, ingredients_id, weight) values (1, 1,  500);
insert into DISH(recipes_id, ingredients_id, weight) values (1, 2,  300);
insert into DISH(recipes_id, ingredients_id, weight) values (1, 3,  500);
insert into DISH(recipes_id, ingredients_id, weight) values (1, 4,  200);
insert into DISH(recipes_id, ingredients_id, weight) values (1, 5,  500);
insert into DISH(recipes_id, ingredients_id, weight) values (2, 1,  500);
insert into DISH(recipes_id, ingredients_id, weight) values (2, 3,  200);
insert into DISH(recipes_id, ingredients_id, weight) values (2, 4,  300);
insert into DISH(recipes_id, ingredients_id, weight) values (2, 5,  500);
insert into DISH(recipes_id, ingredients_id, weight) values (2, 6,  300);