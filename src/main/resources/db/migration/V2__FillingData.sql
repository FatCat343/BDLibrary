INSERT INTO company (company_id, name) VALUES (1, 'google');
INSERT INTO company (company_id, name) VALUES (2, 'Yandex');
INSERT INTO company (company_id, name) VALUES (3, '2GIS');
INSERT INTO company (company_id, name) VALUES (4, 'Bethesda');
INSERT INTO company (company_id, name) VALUES (5, 'Blizzard');
INSERT INTO company (company_id, name) VALUES (6, 'Steelseries');
INSERT INTO company (company_id, name) VALUES (7, 'Apple');
INSERT INTO company (company_id, name) VALUES (8, 'Microsoft');
INSERT INTO company (company_id, name) VALUES (9, 'Wallmart');
INSERT INTO company (company_id, name) VALUES (10, 'Amazon');

INSERT INTO department(department_id, faculty, university) VALUES (1, 'DataScience', 'Princeton University');
INSERT INTO department(department_id, faculty, university) VALUES (2, 'Architecture', 'Princeton University');
INSERT INTO department(department_id, faculty, university) VALUES (3, 'Psychology', 'Harvard University');
INSERT INTO department(department_id, faculty, university) VALUES (4, 'Biochemistry', 'Harvard University');
INSERT INTO department(department_id, faculty, university) VALUES (5, 'History of Art', 'Columbia University');
INSERT INTO department(department_id, faculty, university) VALUES (6, 'Philosophy', 'Columbia University');
INSERT INTO department(department_id, faculty, university) VALUES (7, 'Law', 'Yale University');
INSERT INTO department(department_id, faculty, university) VALUES (8, 'Manufacturing and management', 'Yale University');
INSERT INTO department(department_id, faculty, university) VALUES (9, 'Gender Studies', 'Stanford University');
INSERT INTO department(department_id, faculty, university) VALUES (10, 'Linguistics', 'Stanford University');

INSERT INTO profession (profession_id, name) VALUES (1, 'Cleaner');
INSERT INTO profession (profession_id, name) VALUES (2, 'Manager');
INSERT INTO profession (profession_id, name) VALUES (3, 'CEO');
INSERT INTO profession (profession_id, name) VALUES (4, 'Cook');
INSERT INTO profession (profession_id, name) VALUES (5, 'Salesman');
INSERT INTO profession (profession_id, name) VALUES (6, 'Electrician');
INSERT INTO profession (profession_id, name) VALUES (7, 'Gardener');
INSERT INTO profession (profession_id, name) VALUES (8, 'Security');
INSERT INTO profession (profession_id, name) VALUES (9, 'Secretary');
INSERT INTO profession (profession_id, name) VALUES (10, 'Accountant');

INSERT INTO category (category_id, name) VALUES (1, 'Adventure');
INSERT INTO category (category_id, name) VALUES (2, 'Classics');
INSERT INTO category (category_id, name) VALUES (3, 'Comic Book');
INSERT INTO category (category_id, name) VALUES (4, 'Detective');
INSERT INTO category (category_id, name) VALUES (5, 'Mystery');
INSERT INTO category (category_id, name) VALUES (6, 'Fantasy');
INSERT INTO category (category_id, name) VALUES (7, 'Historical Fiction');
INSERT INTO category (category_id, name) VALUES (8, 'Science Fiction');
INSERT INTO category (category_id, name) VALUES (9, 'Horror');
INSERT INTO category (category_id, name) VALUES (10, 'Action');

INSERT INTO subject (subject_id, name) VALUES (1, 'Architecture');
INSERT INTO subject (subject_id, name) VALUES (2, 'Economics');
INSERT INTO subject (subject_id, name) VALUES (3, 'Business&Management');
INSERT INTO subject (subject_id, name) VALUES (4, 'Engineering');
INSERT INTO subject (subject_id, name) VALUES (5, 'Art');
INSERT INTO subject (subject_id, name) VALUES (6, 'Law');
INSERT INTO subject (subject_id, name) VALUES (7, 'Finance');
INSERT INTO subject (subject_id, name) VALUES (8, 'History');
INSERT INTO subject (subject_id, name) VALUES (9, 'Linguistics');
INSERT INTO subject (subject_id, name) VALUES (10, 'Gender Studies');

INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (1, 1, 1, 1);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (2, 2, 1, 2);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (3, 1, 3, 3);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (4, 1, 6, 4);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (5, 6, 1, 5);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (6, 7, 4, 6);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (7, 1, 5, 7);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (8, 8, 1, 8);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (9, 9, 2, 9);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (10, 10, 3, 10);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (11, 1, 4, 11);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (12, 1, 7, 12);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (13, 7, 1, 13);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (14, 9, 5, 14);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (15, 7, 9, 15);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (16, 2, 8, 16);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (17, 5, 4, 17);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (18, 4, 3, 18);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (19, 9, 1, 19);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (20, 3, 3, 20);
INSERT INTO worker (worker_id, company_id, profession_id, reader_id) VALUES (21, 6, 6, 21);

INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (1, 1, 1222345632, 22);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (2, 1, 1222345633, 23);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (3, 1, 1222345634, 24);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (4, 1, 1222345635, 25);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (5, 1, 1222345636, 26);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (6, 6, 1222345637, 27);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (7, 6, 1222345638, 28);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (8, 6, 1222345639, 29);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (9, 6, 1222341632, 30);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (10, 5, 1222325632, 31);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (11, 7, 1222355632, 32);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (12, 7, 1222365632, 33);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (13, 7, 1222375632, 34);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (14, 7, 1222385632, 35);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (15, 1, 1222395632, 36);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (16, 1, 1222305632, 37);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (17, 8, 1232345632, 38);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (18, 8, 1242345632, 39);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (19, 8, 1252345632, 40);
INSERT INTO student (student_id, department_id, student_code, reader_id) VALUES (20, 8, 1262345632, 41);

INSERT INTO library (library_id, address) VALUES (1, '205 West 39');
INSERT INTO library (library_id, address) VALUES (2, '1600 Pennsylvania Avenue');
INSERT INTO library (library_id, address) VALUES (3, '11 Wall Street');
INSERT INTO library (library_id, address) VALUES (4, '350 Fifth Avenue');
INSERT INTO library (library_id, address) VALUES (5, '221 B Baker Street');
INSERT INTO library (library_id, address) VALUES (6, '2 Macquarie Street');
INSERT INTO library (library_id, address) VALUES (7, '4059 Mt Lee Dr.');
INSERT INTO library (library_id, address) VALUES (8, '48 Doughty Street');
INSERT INTO library (library_id, address) VALUES (9, '25 Brook Street');
INSERT INTO library (library_id, address) VALUES (10, '1 Pirogova Street');

INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (1, 'John', 'Williams', 1);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (2, 'Richard', 'Williams', 1);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (3, 'James', 'Stevenson', 1);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (4, 'Steve', 'Brown', 1);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (5, 'Christian', 'Johnson', 2);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (6, 'Olivia', 'Jones', 2);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (7, 'William', 'Jones', 2);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (8, 'Noah', 'Garcia', 2);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (9, 'Herman', 'Miller', 2);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (10, 'Emma', 'Davis', 3);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (11, 'Ava', 'Martinez', 3);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (12, 'Sophia', 'Lopez', 4);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (13, 'Isabella', 'Hernandez', 4);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (14, 'Charlotte', 'Wilson', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (15, 'Amelia', 'Gonzales', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (16, 'Mia', 'Anderson', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (17, 'Evelyn', 'Thomas', 1);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (18, 'Harper', 'Taylor', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (19, 'Ethan', 'Moore', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (20, 'Mason', 'Martin', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (21, 'Benjamin', 'Perez', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (22, 'Lucas', 'Thompson', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (23, 'Oliver', 'Lee', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (24, 'Mason', 'White', 5);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (25, 'Richard', 'Harris', 6);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (26, 'James', 'Clark', 6);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (27, 'Olivia', 'Lewis', 6);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (28, 'Steve', 'Walker', 7);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (29, 'Christian', 'Young', 7);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (30, 'Noah', 'Allen', 7);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (31, 'Emma', 'King', 7);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (32, 'Emma', 'Wright', 8);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (33, 'Ava', 'Scott', 8);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (34, 'Charlotte', 'Hill', 8);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (35, 'Isabella', 'Flores', 8);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (36, 'Amelia', 'Torres', 8);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (37, 'Mia', 'Green', 9);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (38, 'Evelyn', 'Adams', 10);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (39, 'Mason', 'Nelson', 10);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (40, 'Benjamin', 'Baker', 10);
INSERT INTO reader (reader_id, firstname, lastname, assigned_library_id) VALUES (41, 'Lucas', 'Smith', 10);

INSERT INTO storage (storage_id, library_id, room_number) VALUES (1, 1, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (2, 2, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (3, 2, 2);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (4, 2, 3);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (5, 3, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (6, 4, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (7, 5, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (8, 6, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (9, 7, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (10, 8, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (11, 9, 1);
INSERT INTO storage (storage_id, library_id, room_number) VALUES (12, 10, 1);

INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (1, 'Richard', 'Williams', 1);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (2, 'Henry', 'Hall', 1);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (3, 'Olivia', 'Campbell', 2);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (4, 'Emy', 'Mitchell', 3);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (5, 'Steven', 'Carter', 3);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (6, 'William', 'Roberts', 4);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (7, 'James', 'Phillips', 5);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (8, 'Evelyn', 'Evans', 5);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (9, 'Amelia', 'Turner', 5);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (10, 'Benjamin', 'Smith', 6);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (11, 'Ava', 'Parker', 7);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (12, 'Mason', 'Williams', 8);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (13, 'Ethan', 'Morgan', 9);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (14, 'Harper', 'Cook', 10);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (15, 'John', 'Murphy', 11);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (16, 'Henry', 'Morales', 12);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (17, 'Amelia', 'Morris', 11);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (18, 'Emma', 'Stewart', 10);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (19, 'Noah', 'Reyes', 9);
INSERT INTO staff (staff_id, firstname, lastname, storage_id) VALUES (20, 'Sofia', 'Collins', 8);

INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);
INSERT INTO bookposition (position_id, storage_id, rack_number, shelf_number) VALUES (1, 1, 1, 1);










