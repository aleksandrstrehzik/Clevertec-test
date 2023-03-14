--Вывести к каждому самолету класс обслуживания и количество мест этого класса

select jsonb_object_field_text(a.model, 'ru') model, s.fare_conditions, count(s.fare_conditions) number_of_seats
from aircrafts_data a
         left join seats s on a.aircraft_code = s.aircraft_code
group by model, s.fare_conditions
order by model;

--Найти 3 самых вместительных самолета (модель + кол-во мест)

select jsonb_object_field_text(a.model, 'ru') model, count(s.seat_no) number_of_seats
from aircrafts_data a
         left join seats s on a.aircraft_code = s.aircraft_code
group by model
order by 2 DESC
limit 3;

--Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам

select a.aircraft_code, jsonb_object_field_text(a.model, 'ru') model, s.seat_no
from aircrafts_data a
         left join seats s on a.aircraft_code = s.aircraft_code
where s.fare_conditions != 'Economy'
  and jsonb_object_field_text(a.model, 'ru') = 'Аэробус A321-200'
order by s.seat_no;

--Вывести города в которых больше 1 аэропорта (код аэропорта, аэропорт, город)

select data.airport_code, jsonb_object_field_text(data.airport_name, 'ru') airport, t.city
from (select jsonb_object_field_text(ad.city, 'ru') city, count(ad.airport_name)
      from airports_data ad
      group by city
      having count(ad.airport_name) > 1) t
         left join airports_data data on jsonb_object_field_text(data.city, 'ru') = t.city;

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация

select *, first_value(now() - f.scheduled_departure) over (order by f.scheduled_departure desc)
from flights f
         join (select jsonb_object_field_text(data.city, 'ru') city, data.airport_code
               from airports_data data
               where jsonb_object_field_text(data.city, 'ru') = 'Москва') a
              on a.airport_code = f.departure_airport
         join (select jsonb_object_field_text(data.city, 'ru') city, data.airport_code
               from airports_data data
               where jsonb_object_field_text(data.city, 'ru') = 'Екатеринбург') b
              on b.airport_code = f.arrival_airport
where f.status in ('Scheduled', 'On Time', 'Delayed')
limit 1;

--Вывести самый дешевый и дорогой билет и стоимость (в одном результирующем ответе)

select tic.ticket_no, tic.flight_id, ti1.min_value
from ticket_flights tic
         left join (select first_value(tf.amount) over (order by tf.amount) min_value, tf.ticket_no, tf.flight_id
                    from ticket_flights tf
                    limit 1) ti1 on ti1.ticket_no = tic.ticket_no
where ti1.ticket_no = tic.ticket_no
  and ti1.flight_id = tic.flight_id
union
select tic.ticket_no, tic.flight_id, ti1.max_value
from ticket_flights tic
         left join (select first_value(tf.amount) over (order by tf.amount desc) max_value, tf.ticket_no, tf.flight_id
                    from ticket_flights tf
                    limit 1) ti1 on ti1.ticket_no = tic.ticket_no
where ti1.ticket_no = tic.ticket_no
  and ti1.flight_id = tic.flight_id;

--Написать DDL таблицы Customers , должны быть поля id , firstName, LastName, email , phone.
--Добавить ограничения на поля (constraints).

create table customers
(
    id        serial,
    firstname varchar(20),
    lastname  varchar(30),
    email     varchar(30),
    phone     varchar(25)
);

alter table customers
    add constraint id_pk primary key (id),
    alter column firstname set not null,
    alter column lastname set not null,
    add constraint email_u unique (email),
    alter column email set not null,
    alter column phone set not null,
    add constraint phone_u unique (phone);

-- Написать DDL таблицы Orders , должен быть id, customerId,	quantity. Должен быть внешний ключ на таблицу customers + ограничения

create table orders
(
    id       serial,
    quantity int
);

alter table orders
    add constraint id_order_pk primary key (id),
    alter column quantity set not null,
    add constraint quantity check (quantity > 0),
    add column customer_id int not null,
    add constraint fk_customer_order foreign key (customer_id) references customers (id);

-- Написать 5 insert в эти таблицы

insert into customers(firstname, lastname, email, phone)
values ('Sergei', 'Buian', 'sergeibuian@gmail.com', '+375257639082'),
       ('Nikita', 'Sakhznkov', 'nikitaKiller@gmail.com', '+375339857382'),
       ('Andrei', 'Ipatov', 'ipatich228@gmail.com', '+375446987513'),
       ('Leonid', 'Tarasevich', 'leo007@mail.ru', '+375258957496'),
       ('Levon', 'Aronian', 'levoniha@gmail.com', '+375336984598');

insert into orders(quantity, customer_id)
values (3, 1),
       (10, 2),
       (2, 3),
       (30, 4),
       (34, 5);

-- удалить таблицы

drop table orders;
drop table customers;

--Найти модель самолета с наибольшим количеством вылетов, его среднюю заполненность в пассажирах и в процентах от имеющихся мест

select s.aircraft_code,
       round(avg(s.count), 3)                           avg_of_passengers,
       round(100 * avg(s.count) / r.number_of_seats, 3) avg_in_percent
from (select f.aircraft_code, count(tf.flight_id)
      from ticket_flights tf
               left join flights f on tf.flight_id = f.flight_id
      where f.aircraft_code = (select t.aircraft_code
                               from (select a.aircraft_code, count(flight_id)
                                     from aircrafts_data a
                                              left join flights f on a.aircraft_code = f.aircraft_code
                                     group by a.aircraft_code
                                     order by 2 desc
                                     limit 1) t)
      group by tf.flight_id, f.aircraft_code) s
         left join (select a.aircraft_code, count(s.seat_no) number_of_seats
                    from aircrafts_data a
                             left join seats s on a.aircraft_code = s.aircraft_code
                    group by a.aircraft_code) r on s.aircraft_code = r.aircraft_code
group by s.aircraft_code, r.number_of_seats;
