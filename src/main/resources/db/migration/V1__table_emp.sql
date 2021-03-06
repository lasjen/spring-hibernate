create table emp(
  empno    number(4,0) GENERATED BY DEFAULT ON NULL AS IDENTITY START WITH 1000 INCREMENT BY 1,
  ename    varchar2(10),
  job      varchar2(9),
  mgr      number(4,0),
  hiredate date,
  sal      number(10,2),
  comm     number(7,2),
  deptno   number(2,0),
  constraint pk_emp primary key (empno)
);

insert into emp (ename, job, mgr, hiredate, sal, comm, deptno) values ('LARRY', 'CEO', null, sysdate-4000, 10000, null, 10);
insert into emp (ename, job, mgr, hiredate, sal, comm, deptno) values ('KING', 'Sales', 1000, sysdate-3000, 5000, null, 30);
commit;

--grant select, insert, update, delete on emp to demo_rw;
--grant select on emp to demo_ro;

create sequence dbop_seq start with 1 increment by 1 cache 100;

--grant select on dbop_seq to demo_rw;