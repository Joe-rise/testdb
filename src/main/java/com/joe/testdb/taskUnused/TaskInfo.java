package com.joe.testdb.taskUnused;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sc_task_info")
public class TaskInfo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String cron;

    private String jobName;

    private String status;

    private Date createTime;

    private Date updateTime;
}
