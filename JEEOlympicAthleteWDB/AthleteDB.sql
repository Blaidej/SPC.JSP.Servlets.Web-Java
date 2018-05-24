/***
 * Create foam using MySQL database:
 *   -- remember to add MySQL JDBC driver to project
 *   -- run the following DDL:
 **/
DROP DATABASE IF EXISTS foam;
  
CREATE DATABASE foam;
/***
 * To create athletes table with auto-increment ID field:
 *
 **/ 
use foam;
CREATE TABLE Athletes(
    AthleteID INT NOT NULL AUTO_INCREMENT
    , NationalID VARCHAR(10) NOT NULL DEFAULT ''
    , FirstName VARCHAR(32) NOT NULL DEFAULT ''
    , LastName VARCHAR(32) NOT NULL DEFAULT ''
    , DateOfBirth Date 
    , PRIMARY KEY (AthleteID)
);
	
  
/***
 * Create foam using Derby Database:
 *    -- Open the Services tab in NetBeans
 *    -- right mouse click and select "Create Database..."
 *    --    - Database Name : use "foam"
 *          - username      : use foam_admin
 *          - password      : use csci2466foam
 *    -- remember to add Java DB Driver to project
 ****
 * To create events table with auto-increment ID field:
 *
 

CREATE TABLE Athletes(
      AthleteID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
    , NationalID VARCHAR(10) NOT NULL DEFAULT ''
    , FirstName VARCHAR(32) NOT NULL DEFAULT ''
    , LastName VARCHAR(32) NOT NULL DEFAULT '' 
    , DateOfBirth Date 
    , PRIMARY KEY (AthleteID)
);
**/
/** SQL below works for both databases: **/
show tables;

INSERT INTO Athletes
(NationalID, FirstName, LastName, DateOfBirth)
VALUES
     ('FD399', 'Jarmo',      'Jokihaara',    '1998/03/26')
   , ('UP279','Pontus',      'Ylihärsilä',   '1996/11/12')
   , ('PA908','Retu',        'Seikola',      '1980/04/17')
   , ('MF851','Karoliina',   'Rahkamo',     '1991/03/18')
   , ('QM634','Seppo',       'Jantunen',     '1984/12/12')
   , ('TA835','Kati',        'Nurminen',     '1977/06/19')
   , ('EU926','Pirkko',      'Sjöström',     '1999/05/19')
   , ('AH599','Jarno',       'Riihijärvi',   '1989/02/17')
   , ('GV449','Keijo',       'Lintilä',      '1981/04/20')
   , ('LO110','Karri',       'Saikkonen',    '1994/02/28')
;
/** another set of athletes in case you have problems with the special characters in the first set 

INSERT INTO Athletes
(NationalID, FirstName, LastName, DateOfBirth)
VALUES
  ('DC721', 'Gemma', 'Brighi', '1978-04-04')
, ('YD289', 'Shawnee', 'Reifel', '1981-01-10')
, ('OK950', 'Bertie', 'Hiers', '1995-08-26')
, ('FE331', 'Laverna', 'Duling', '1985-09-17')
, ('TF544', 'Ana', 'Patrich', '1998-10-06')
, ('CN957', 'Terry', 'Schlicht', '1997-10-16')
, ('QK856', 'Wilhelmina', 'Fudala', '1978-08-28')
, ('TX719', 'Murray', 'Lazzaro', '1995-09-23')
, ('JE182', 'Olin', 'Funn', '1987-06-13')
, ('RH312', 'Christene', 'Gorny', '1984-11-16')
;
**/
