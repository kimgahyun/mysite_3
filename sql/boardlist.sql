--list 
select count(*) from board;


--board

--view
select no, title, content from board where no = 2; --내용보여주서 뽑아내고 
update board set hit = hit + 1 where no = 2; --뽑아낸거 업데이트로 조회수 늘리기

select *
from
(select rownum as rn, no, title, hit, reg_date, name, users_no
 from( select a.no,
	    	  a.title, 
	   		  a.hit, 
	  		  to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date,
	   		  b.name,
			  a.users_no
    	 from board a, users b
  	 	where a.users_no = b.no
	 --   and title like '%kwd%' or content like '%kwd%' --키워드 서치인 경우
	 order by group_no desc, order_no asc))
where (2-1)*5+1 <= rn --1은 current page현재 페이지 // 현재 페이지가 1이면 // 페이지 사이즈가 2인 경우  // 5가 페이지 사이즈
  and rn <= 2*5;

-- 원글은 group_no 내림차순 답글은 원글 그룹내 order_no 올림차순; 

--board 
select max(group_no) from board;


--insert1 (새글)
insert
 into board
 values(board_seq.nextval,'안녕' , '안녕', sysdate, 0,
		nvl((select max(group_no) from board),0) + 1, 1, 0, 2);

insert
 into board
 values(board_seq.nextval,'배고프다 그만해' , '냉무', sysdate, 0,
		nvl((select max(group_no) from board),0) + 1, 1, 0, 2);

--insert2(답글이 들어갈 때 )
update board
   set order_no = order_no + 1
 where group_no = 2
   and order_no > 1; --부모 글 순서


insert
 into board
 values(board_seq.nextval,
 		'짜장' , '냉무', sysdate, 0,
		2, --부모글의 그룹 
		2, --부모글의 오더(순서)에 +1
		1, --부모글 깊이에 		+1
		2);


select * from board;

commit;







drop table board;
drop sequence board_no_seq;

CREATE TABLE board
( 
no           NUMBER(8),
title        VARCHAR2(200) NOT NULL,
content      VARCHAR2(4000) NOT NULL,
member_no    NUMBER(8),
member_name  VARCHAR2(30),
view_cnt     NUMBER(10),
reg_date     DATE NOT NULL
) ;

ALTER TABLE board
ADD ( CONSTRAINT board_no_pk PRIMARY KEY ( no ) );


CREATE SEQUENCE board_seq
 START WITH     1
 INCREMENT BY   1
 MAXVALUE       99999999
 NOCACHE
 NOCYCLE;
 
