-- users

-- insert
insert into users values( user_seq.nextval, '김가현', 'kim@gmail.com', '1234', 'feamle' );

delete from users where email = 'rlarkgus@gmail.com';

commit;

--select (login)
select no, name from users where email='kim@gmail.com' and password='1234';