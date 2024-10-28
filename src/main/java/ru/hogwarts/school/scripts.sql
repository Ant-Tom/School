-- 1. Get students with age between 10 and 20
SELECT * FROM student WHERE age BETWEEN 10 AND 20;

-- 2. Get names of all students
SELECT name FROM student;

-- 3. Get students with "O" in their name
SELECT * FROM student WHERE name LIKE '%O%';

-- 4. Get students where age is less than id
SELECT * FROM student WHERE age < id;

-- 5. Get students ordered by age
SELECT * FROM student ORDER BY age;
