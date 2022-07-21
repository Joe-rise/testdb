create database test;
use test;
create table test_user(
    id bigint unsigned auto_increment comment '自增主键' primary key ,
    user_name varchar(40) not null default '' comment '用户名',
    password varchar(40) not null comment '密码',
    mobile varchar(20) not null comment '手机号' unique ,
    create_time      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)engine =INNODB default charset =utf8mb4 auto_increment=1 comment '用户注册在·信息表';


create table sys_job(
    id int(10) auto_increment primary key comment '自增主键',
    bean_name varchar(100) not null comment 'bean名称',
    method_name varchar(100) not null comment '方法名称',
    method_params varchar(255) default '' not null comment '方法参数',
    cron_expression varchar(255) not null comment 'cron表达式',
    remark varchar(100) default '' not null comment '备注',
    job_status tinyint(3) not null comment '状态',
    create_time      datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time      datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)engine =INNODB default charset =utf8mb4 auto_increment=1 comment '定时任务表';


alter table sys_job
    add unique key (method_name,bean_name,method_params)


