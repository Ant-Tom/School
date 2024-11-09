-- Ограничение на возраст студента (не менее 16 лет)
ALTER TABLE Student
ADD CONSTRAINT check_student_age CHECK (age >= 16);

-- Ограничение на уникальность имени студента и запрет на NULL
ALTER TABLE Student
ADD CONSTRAINT unique_student_name UNIQUE (name),
ADD CONSTRAINT check_student_name_not_null CHECK (name IS NOT NULL);

-- Ограничение на уникальность пары "название факультета" - "цвет факультета"
ALTER TABLE Faculty
ADD CONSTRAINT unique_faculty_name_color UNIQUE (name, color);

-- Значение возраста по умолчанию (20 лет)
ALTER TABLE Student
ALTER COLUMN age SET DEFAULT 20;
