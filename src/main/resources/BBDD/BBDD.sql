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
 values ( 'Dante', 'contra7', true,'Alumno','Lineal');
 insert into USER ( USERNAME ,PASSWORD ,ENABLED,ROL_U,COMPLEX_U)
 values ( 'ragnar', 'contra7',true, 'Profesor',null);
 insert into USER ( USERNAME ,PASSWORD ,ENABLED,ROL_U,COMPLEX_U)
 values ( 'angel', 'contra7',true, 'Estudiante','logn');

