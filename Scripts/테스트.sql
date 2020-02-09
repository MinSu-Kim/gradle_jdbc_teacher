select user(), database();

select * from department d ;
select * from title;
select * from employee e ;
desc employee;

select title_no, title_name from title;

insert into employee(emp_no, emp_name, title, manager, salary, dept, pass) values(0, '', 0, 0, 0, 0, ?, '');

update employee
set emp_name='', title=0, manager=0, salary=0, dept=0
where emp_no=0;

select emp_no, emp_name, title, manager, salary, dept from employee where emp_no=1004 and pass = password('root');
select emp_no, emp_name, title, manager, salary, dept, pass, hire_date pic from employee;