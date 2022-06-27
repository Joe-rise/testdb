package com.joe.testdb.module.user.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("test_user")
public class TestUser {

    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String userName;
    private String password;
    private String mobile;
    private Date createTime;
    private Date updateTime;
}
