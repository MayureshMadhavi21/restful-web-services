insert into user_details(ID,birth_date,name)
values('1',current_date(),'Adam'),
      ('2',current_date(),'Jim'),
      ('3',current_date(),'Eve');
      
insert into post(id,description,user_id)
values (101,'I want to learn aws','1'),
	   (102,'I want to learn spring','2'),
	   (103,'I want to learn angular','3');