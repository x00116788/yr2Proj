
# --- !Ups

insert into category (id,name) values (  1,'Wedding Dress');
insert into category (id,name) values (  2,'Tux');
insert into category (id,name) values (  3,'3 Piece Suit');
insert into category (id,name) values (  4,'A-Line');
insert into category (id,name) values (  5,'Ballgown');

insert into outfit (id,colour,price,size,category_id) values (  1,'Blue',100,32,1);
insert into outfit (id,colour,price,size,category_id) values (  2,'Blue',120,34,2);
insert into outfit (id,colour,price,size,category_id) values (  3,'Blue',80,36,3);
insert into outfit (id,colour,price,size,category_id) values (  4,'Blue',100,38,4);

insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 1,'Sandra','OConnell','4','Sarfield Av','Clonee','Dublin','s.oconnel@gmail.com','012598765' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 2,'Sean','Connery','2','GreenHeights Drive','Inchicore','Dublin','s.connery@yahoo.com','0899563214' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 3,'Roisin','Gold','27','Golfing Apts','saggart','Dublin','ros.gold@clara.net','0856541230' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 4,'Amanda','Winsconsin','16','Citywest','Citywest','Dublin','Amanda.win@aol.com','014569874');
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 5,'Joshua','Android','22','Rotunda fields','Ballyfermot','Dublin','jandroid@yahoo.ie','014789652' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 6,'Dennis','Fisher','94','RoseDale Grove','Clonsilla','Dublin','den.fisher@aol.co.uk','08671563038');
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 7,'Kelly','Clanger','15','Ravenswood Park','Kinsale','Cork','k.clanger@clara.co.uk','021569874');
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 8,'Kenny','Rogers','32','The Crescent','Ennis','Ennis','kennyRogers@hotmail.co.uk','061854321' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 9,'Dolly','Parton','1','Embroidery Lane','Rathcoole','Dublin','d.parton@gmail.co.uk','01654987' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 10,'Tuface','Idibia','45','FreePark Lane','New Market-on-Fergus','Ennis','tupapa@clara.ie','061741258' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 11,'Mercy','Adams','16','Allendale Close','Balbrigan','Dublin','mercyad@yaoo.com','0854563970' );
insert into customer (id,firstname,lastname,address,street,city,county,email,phone_number) values ( 12,'Mariam','Nicole','56','Greenhills','Tallaght','Dublin','m.Nuga@gmail.com','01465987' );

insert into user (email,name,password,userType) values ( 'admin1@sam.ie', 'Alice Admin', 'password','admin' );
insert into user (email,name,password,userType) values ( 'manager1@sam.ie', 'Bob Manager', 'password','manager' );
insert into user (email,name,password,userType) values ( 'clerk1@sam.ie', 'Charlie Clerk', 'password','staff' );

# --- !Downs

