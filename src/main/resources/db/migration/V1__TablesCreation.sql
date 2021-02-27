CREATE TABLE library
(
    library_id integer NOT NULL,
    address    varchar(50) NOT NULL,
    CONSTRAINT PK_library PRIMARY KEY ( library_id )
);
CREATE TABLE storage
(
    storage_id  integer NOT NULL,
    library_id  integer NOT NULL,
    room_number integer NOT NULL,
    CONSTRAINT PK_storage PRIMARY KEY ( storage_id ),
    CONSTRAINT FK_library FOREIGN KEY ( library_id ) REFERENCES library ( library_id )
);
CREATE TABLE bookPosition
(
    position_id  integer NOT NULL,
    storage_id   integer NOT NULL,
    rack_number  integer NOT NULL,
    shelf_number integer NOT NULL,
    CONSTRAINT PK_bookpos PRIMARY KEY ( position_id ),
    CONSTRAINT FK_storage FOREIGN KEY ( storage_id ) REFERENCES storage ( storage_id )
);
CREATE TABLE author
(
    author_id  integer NOT NULL,
    firstName  varchar(50) NOT NULL,
    lastName   varchar(50) NOT NULL,
    CONSTRAINT PK_author PRIMARY KEY ( author_id )
);
CREATE TABLE publication
(
    publication_id integer NOT NULL,
    title          varchar(50) NOT NULL,
    author_id      integer NOT NULL,
    CONSTRAINT PK_publication PRIMARY KEY ( publication_id ),
    CONSTRAINT FK_author FOREIGN KEY ( author_id ) REFERENCES author ( author_id )
);
CREATE TABLE category
(
    category_id  integer NOT NULL,
    name         varchar(50) NOT NULL,
    CONSTRAINT PK_category PRIMARY KEY ( category_id )
);
CREATE TABLE book
(
    book_id              integer NOT NULL,
    publication_id       integer NOT NULL,
    category_id          integer NOT NULL,
    CONSTRAINT PK_book PRIMARY KEY ( book_id ),
    CONSTRAINT FK_publication FOREIGN KEY ( publication_id ) REFERENCES publication ( publication_id ),
    CONSTRAINT FK_category FOREIGN KEY ( category_id ) REFERENCES category ( category_id )
);
CREATE TABLE subject
(
    subject_id  integer NOT NULL,
    name        varchar(50) NOT NULL,
    CONSTRAINT PK_subject PRIMARY KEY ( subject_id )
);
CREATE TABLE dissertation
(
    dissertation_id     integer NOT NULL,
    publication_id      integer NOT NULL,
    subject_id          integer NOT NULL,
    CONSTRAINT PK_dissertation PRIMARY KEY ( dissertation_id ),
    CONSTRAINT FK_publication FOREIGN KEY ( publication_id ) REFERENCES publication ( publication_id ),
    CONSTRAINT FK_subject FOREIGN KEY ( subject_id ) REFERENCES subject ( subject_id )
);
CREATE TABLE edition
(
    edition_id     integer NOT NULL,
    usage_type     varchar(50) NOT NULL,
    position_id    integer NOT NULL,
    publication_id integer NOT NULL,
    date_arrived   date NOT NULL,
    date_left      date,
    CONSTRAINT PK_edition PRIMARY KEY ( edition_id ),
    CONSTRAINT FK_publication FOREIGN KEY ( publication_id ) REFERENCES publication ( publication_id ),
    CONSTRAINT FK_position FOREIGN KEY ( position_id ) REFERENCES bookPosition ( position_id )
);
CREATE TABLE indoor
(
    indoor_id                     integer NOT NULL,
    reason_for_indoor_usage_only  text NOT NULL,
    edition_id                    integer NOT NULL,
    CONSTRAINT PK_indoor PRIMARY KEY ( indoor_id ),
    CONSTRAINT FK_edition FOREIGN KEY ( edition_id ) REFERENCES edition ( edition_id )
);
CREATE TABLE outdoor
(
    outdoor_id    integer NOT NULL,
    rental_period interval NOT NULL,
    edition_id    integer NOT NULL,
    CONSTRAINT PK_outdoor PRIMARY KEY ( outdoor_id ),
    CONSTRAINT FK_edition FOREIGN KEY ( edition_id ) REFERENCES edition ( edition_id )
);
CREATE TABLE reader
(
    reader_id           integer NOT NULL,
    firstName           varchar(50) NOT NULL,
    lastName            varchar(50) NOT NULL,
    assigned_library_id integer NOT NULL,
    CONSTRAINT PK_reader PRIMARY KEY ( reader_id ),
    CONSTRAINT FK_library FOREIGN KEY ( assigned_library_id ) REFERENCES library ( library_id )
);
CREATE TABLE department
(
    department_id     integer NOT NULL,
    faculty           varchar(50) NOT NULL,
    university        varchar(50) NOT NULL,
    CONSTRAINT PK_department PRIMARY KEY ( department_id )
);
CREATE TABLE student
(
    student_id      integer NOT NULL,
    department_id   integer NOT NULL,
    student_code    integer NOT NULL,
    reader_id       integer NOT NULL,
    CONSTRAINT PK_student PRIMARY KEY ( student_id ),
    CONSTRAINT FK_reader FOREIGN KEY ( reader_id ) REFERENCES reader ( reader_id ),
    CONSTRAINT FK_department FOREIGN KEY (department_id) REFERENCES department(department_id)
);
CREATE TABLE company
(
    company_id     integer NOT NULL,
    name           varchar(50) NOT NULL,
    CONSTRAINT PK_company PRIMARY KEY ( company_id )
);
CREATE TABLE profession
(
    profession_id     integer NOT NULL,
    name              varchar(50) NOT NULL,
    CONSTRAINT PK_profession PRIMARY KEY ( profession_id )
);
CREATE TABLE worker
(
    worker_id           integer NOT NULL,
    company_id          integer NOT NULL,
    profession_id       integer NOT NULL,
    reader_id           integer NOT NULL,
    CONSTRAINT PK_worker PRIMARY KEY ( worker_id ),
    CONSTRAINT FK_reader FOREIGN KEY ( reader_id ) REFERENCES reader ( reader_id ),
    CONSTRAINT FK_company FOREIGN KEY (company_id) REFERENCES company (company_id),
    CONSTRAINT FK_profession FOREIGN KEY (profession_id) REFERENCES profession(profession_id)

);
CREATE TABLE staff
(
    staff_id        integer NOT NULL,
    firstName       varchar(50) NOT NULL,
    lastName        varchar(50) NOT NULL,
    storage_id      integer NOT NULL,
    CONSTRAINT PK_staff PRIMARY KEY ( staff_id ),
    CONSTRAINT FK_storage FOREIGN KEY ( storage_id ) REFERENCES storage ( storage_id )
);
CREATE TABLE distribution
(
    distribution_id integer NOT NULL,
    reader_id       integer NOT NULL,
    edition_id      integer NOT NULL,
    staff_id        integer NOT NULL,
    date_give       date NOT NULL,
    date_return     date,
    CONSTRAINT PK_distribution PRIMARY KEY ( distribution_id ),
    CONSTRAINT FK_reader FOREIGN KEY ( reader_id ) REFERENCES reader ( reader_id ),
    CONSTRAINT FK_staff FOREIGN KEY ( staff_id ) REFERENCES staff ( staff_id ),
    CONSTRAINT FK_edition FOREIGN KEY ( edition_id ) REFERENCES edition ( edition_id )
);






