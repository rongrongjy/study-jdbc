drop table if exists t_act;
create table t_act(
    actno char(1) primary key ,
    balance double(10,2)
);
insert into t_act(actno, balance) values ('A',20000);
insert into t_act(actno, balance) values ('B',0);
select * from t_act;