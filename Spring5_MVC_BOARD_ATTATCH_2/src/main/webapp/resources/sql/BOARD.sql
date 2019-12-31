drop table comments;
drop table board;
CREATE TABLE BOARD(
   BOARD_NUM NUMBER,
   BOARD_NAME VARCHAR2(30),
   BOARD_PASS VARCHAR2(30),
   BOARD_SUBJECT VARCHAR2(300),
   BOARD_CONTENT VARCHAR2(4000),
   BOARD_FILE VARCHAR2(50),
   BOARD_ORIGINAL VARCHAR2(50),
   BOARD_RE_REF NUMBER, --답변 글 작성시 참조되는 글
   BOARD_RE_LEV NUMBER, --답변 글의 깊이
   BOARD_RE_SEQ NUMBER, --답변 글의 순서
   BOARD_READCOUNT NUMBER,
   BOARD_DATE DATE,
   PRIMARY KEY(BOARD_NUM)
);

select * from board;
delete BOARD;

create table delete_File(
 BOARD_FILE   VARCHAR2(50) primary key
 )
 select * from delete_file;
 
 