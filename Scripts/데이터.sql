select user(), database();

-- title 
insert into title values
(1, '����'),
(2, '����'),
(3, '����'),
(4, '�븮'),
(5, '���');

-- department;
insert into department values
(1, '����', 8),
(2, '��ȹ', 10),
(3, '����', 9),
(4, '�ѹ�', 7);

-- employee
insert into employee(emp_no, emp_name, title, manager, salary, dept, pass, hire_date) values
(4377, '�̼���', 1, null, 5000000, 2, password('1234567'), '2000-01-1'),
(3426, '�ڿ���', 3, 4377, 3000000, 1, password('1234567'), '2000-02-11'),
(1003, '������', 3, 4377, 3000000, 2, password('1234567'), '2005-03-12'),
(3011, '�̼���', 2, 4377, 4000000, 3, password('1234567'), '2010-04-13'),
(2106, '��â��', 4, 1003, 2500000, 2, password('1234567'), '2016-05-14'),
(3427, '����ö', 5, 3011, 1500000, 3, password('1234567'), '2017-06-15');







