DROP TABLE COMMENTS;
CREATE TABLE COMMENTS (
num number primary key,
id varchar2(30) references member(id),
content varchar2(200),
reg_date date,
BOARD_RE_REF number references board(BOARD_NUM)
on delete cascade
);

--on delete cascade : 원문글을 삭제 하면 이 원문글을 참조하는 댓글도 삭제됩니다.

create sequence com_seq;

select * from comments;