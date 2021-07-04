CREATE DATABASE bbdd;

use bbdd;
-- Create table
create table USER
(
  ID           BIGINT not null AUTO_INCREMENT primary key,
  USERNAME       VARCHAR(36) unique not null,
  PASSWORD VARCHAR(128) not null,
  ENABLED boolean default true,
  ROL_U VARCHAR(128) not null,
  COMPLEX_U VARCHAR(128)
  
) ;

ALTER TABLE USER AUTO_INCREMENT=1;

 insert into USER ( USERNAME ,PASSWORD ,ENABLED,ROL_U,COMPLEX_U)
 values ( 'Dante', '$2a$10$5Vo40BraGjvHgZaUEYoKbeZj8dtajtwpRW1TU/EH23AUS5aV3u2qK', true,'Profesor','Ninguna');

