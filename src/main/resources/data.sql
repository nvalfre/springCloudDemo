--jdbc:h2:mem:testdb
insert into user values(10001, sysdate(),'ABEL')
insert into user values(10002, sysdate(),'JILL')
insert into user values(10003, sysdate(),'WILL')
insert into post values(11001, '1stPost', 10001)
insert into post values(11002, '2ndPost', 10002)
insert into post values(11003, '3rdPost', 10001)