CREATE TABLE IF NOT EXISTS "passengercar"
                 (
                     passengercount integer not null,
                     id varchar (255) not null,
                    CONSTRAINT PassengerCar_pkey PRIMARY KEY (id)
                 );

                 CREATE TABLE IF NOT EXISTS "truck"
                 (
                     loadcapacity integer null,
                     id varchar (255) not null,
                     CONSTRAINT Truck_pkey PRIMARY KEY (id)
                 );
                 CREATE TABLE IF NOT EXISTS "car_order"
                 (
                     created date  not null,
                     orderid varchar (255) not null,
                     CONSTRAINT Orders_pkey PRIMARY KEY (orderid)
                 );

                 CREATE TABLE IF NOT EXISTS "engine"
                 (
                     POWER integer not null,
                     TYPE varchar (255) not null,
                     engineid varchar (255) not null,
                     CONSTRAINT Engine_pkey PRIMARY KEY (engineid)
                 );

                 CREATE TABLE IF NOT EXISTS "car"
                 (
                     ID varchar (255) NOT NULL,
                     MANUFACTURER varchar (255) not null,
                     CARTYPE varchar(255) not null,
                     COLOR varchar (255) not null,
                     PRICE integer null,
                     COUNT integer null,
                     ENGINE_ENGINE_ID varchar (255) null,
                     ORDER_ID varchar (255) null,
                     CONSTRAINT Car_pkey PRIMARY KEY (id)

                 );