create table feedbacks
(
    id         uuid not null,
    course_id  uuid not null,
    notes      varchar(255),
    student_id uuid not null,
    primary key (id)
);
create table files
(
    id           uuid not null,
    content_type varchar(255),
    data         oid,
    lesson_id    uuid,
    name         varchar(255),
    size         int8,
    student_id   uuid,
    primary key (id)
);
create table grades
(
    id         uuid not null,
    grade      int4 not null,
    lesson_id  uuid not null,
    student_id uuid not null,
    primary key (id)
);
alter table grades
    add constraint UKmaclbn0751e2h92uw07gfssa5 unique (lesson_id, student_id);
