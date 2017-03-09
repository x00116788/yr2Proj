# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table basket (
  id                        bigint not null,
  user_email                varchar(255),
  customer_id               bigint,
  constraint uq_basket_user_email unique (user_email),
  constraint uq_basket_customer_id unique (customer_id),
  constraint pk_basket primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table customer (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  address                   varchar(255),
  street                    varchar(255),
  city                      varchar(255),
  county                    varchar(255),
  email                     varchar(255),
  phone_number              varchar(255),
  constraint pk_customer primary key (id))
;

create table order_item (
  id                        bigint not null,
  order_id                  bigint,
  basket_id                 bigint,
  outfit_id                 bigint,
  price                     double,
  deposit                   double,
  return_date               timestamp,
  refund_due                double,
  damaged                   boolean,
  is_early                  boolean,
  constraint pk_order_item primary key (id))
;

create table outfit (
  id                        bigint not null,
  colour                    varchar(255),
  price                     double,
  size                      integer,
  available                 boolean,
  category_id               bigint,
  constraint pk_outfit primary key (id))
;

create table shop_order (
  id                        bigint not null,
  order_date                timestamp,
  due_date                  timestamp,
  user_email                varchar(255),
  customer_id               bigint,
  tot_deposit               double,
  constraint pk_shop_order primary key (id))
;

create table user (
  usertype                  varchar(31) not null,
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence basket_seq;

create sequence category_seq;

create sequence customer_seq;

create sequence order_item_seq;

create sequence outfit_seq;

create sequence shop_order_seq;

create sequence user_seq;

alter table basket add constraint fk_basket_user_1 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_basket_user_1 on basket (user_email);
alter table basket add constraint fk_basket_customer_2 foreign key (customer_id) references customer (id) on delete restrict on update restrict;
create index ix_basket_customer_2 on basket (customer_id);
alter table order_item add constraint fk_order_item_order_3 foreign key (order_id) references shop_order (id) on delete restrict on update restrict;
create index ix_order_item_order_3 on order_item (order_id);
alter table order_item add constraint fk_order_item_basket_4 foreign key (basket_id) references basket (id) on delete restrict on update restrict;
create index ix_order_item_basket_4 on order_item (basket_id);
alter table order_item add constraint fk_order_item_outfit_5 foreign key (outfit_id) references outfit (id) on delete restrict on update restrict;
create index ix_order_item_outfit_5 on order_item (outfit_id);
alter table outfit add constraint fk_outfit_category_6 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_outfit_category_6 on outfit (category_id);
alter table shop_order add constraint fk_shop_order_user_7 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_shop_order_user_7 on shop_order (user_email);
alter table shop_order add constraint fk_shop_order_customer_8 foreign key (customer_id) references customer (id) on delete restrict on update restrict;
create index ix_shop_order_customer_8 on shop_order (customer_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists basket;

drop table if exists category;

drop table if exists customer;

drop table if exists order_item;

drop table if exists outfit;

drop table if exists shop_order;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists basket_seq;

drop sequence if exists category_seq;

drop sequence if exists customer_seq;

drop sequence if exists order_item_seq;

drop sequence if exists outfit_seq;

drop sequence if exists shop_order_seq;

drop sequence if exists user_seq;

