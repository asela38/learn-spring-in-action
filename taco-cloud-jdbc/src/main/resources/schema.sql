create table if not exists Ingredient (
  id varchar(4) not null,
  name varchar(25) not null,
  type varchar(10) not null
);

create table if not exists INGREDIENT_REF (
  INGREDIENT varchar(4) not null,
  TACO varchar(25) not null,
  TACO_KEY varchar(10) not null
);

create table if not exists Taco (
  id identity,
  taco_Order bigint,
  taco_order_key bigint,
  name varchar(50) not null,
  created_At timestamp not null
);

create table if not exists Taco_Ingredients (
  taco bigint not null,
  ingredient varchar(4) not null
);

alter table Taco_Ingredients
    add foreign key (taco) references Taco(id);
alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient(id);

create table if not exists Taco_Order (
	id identity,
	delivery_Name varchar(50) not null,
	delivery_Street varchar(50) not null,
	delivery_City varchar(50) not null,
	delivery_State varchar(2) not null,
	delivery_Zip varchar(10) not null,
	cc_Number varchar(16) not null,
	cc_Expiration varchar(5) not null,
	cc_CVV varchar(3) not null,
    placed_At timestamp not null
);

create table if not exists Taco_Order_Tacos (
	tacoOrder bigint not null,
	taco bigint not null
);

alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order(id);
alter table Taco_Order_Tacos
    add foreign key (taco) references Taco(id);
