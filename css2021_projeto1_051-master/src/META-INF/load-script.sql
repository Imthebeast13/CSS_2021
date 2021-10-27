DELETE FROM COMPANY
DELETE FROM VENUE
DELETE FROM EVENT
DELETE FROM TICKET
DELETE FROM Company_ALLOWEDEVENTTYPES
DELETE FROM EVENTDAY
INSERT INTO COMPANY (ID, NAME) VALUES (1, 'una')
INSERT INTO COMPANY (ID, NAME) VALUES (2, 'dua')
INSERT INTO Company_ALLOWEDEVENTTYPES (Company_ID, ALLOWEDEVENTTYPES) VALUES (1, 'TeteATete')
INSERT INTO Company_ALLOWEDEVENTTYPES (Company_ID, ALLOWEDEVENTTYPES) VALUES (1, 'BandoSentado')
INSERT INTO Company_ALLOWEDEVENTTYPES (Company_ID, ALLOWEDEVENTTYPES) VALUES (2, 'BandoSentado')
INSERT INTO Company_ALLOWEDEVENTTYPES (Company_ID, ALLOWEDEVENTTYPES) VALUES (2, 'MultidaoEmPe')
INSERT INTO VENUE (ID, VENUE_TYPE, NAME) VALUES (1, 'Seated', 'Micro Pavilhao')
INSERT INTO VENUE (ID, VENUE_TYPE, NAME) VALUES (2, 'Seated', 'Mini Estadio')
INSERT INTO VENUE (ID, VENUE_TYPE, NAME, CAPACITY) VALUES (3, 'Standing', 'Pequeno Relvado', 10)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (1, 'A', 1, 1)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (2, 'A', 2, 1)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (3, 'B', 1, 1)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (4, 'B', 2, 1)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (5, 'B', 3, 1)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (6, 'A', 1, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (7, 'A', 2, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (8, 'A', 3, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (9, 'A', 4, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (10, 'A', 5, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (11, 'B', 1, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (12, 'B', 2, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (13, 'B', 3, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (14, 'B', 4, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (15, 'B', 5, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (16, 'B', 6, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (17, 'B', 7, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (18, 'B', 8, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (19, 'B', 9, 2)
INSERT INTO SEAT (ID, SEATLETTER, SEATNUMBER, VENUE_ID) VALUES (20, 'B', 10, 2)