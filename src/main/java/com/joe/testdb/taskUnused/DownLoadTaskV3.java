package com.joe.testdb.taskUnused;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 动态定时任务实现步骤
 * 步骤1：定义定时任务 DownLoadTaskV3 类实现 SchedulingConfigurer 接口；
 * 步骤2：编写定时任务要执行的业务逻辑；
 * 步骤3：数据库中配置任务执行的具体时间规则，记住任务名称
 * 步骤4：根据任务名称从数据库获取 Cron 参数，设置任务触发器，触发任务执行。
 * （仅抛砖引玉，可作进一步的抽象）
 */
@Component
//@EnableScheduling
public class DownLoadTaskV3 implements SchedulingConfigurer {

    private static final Log logger = LogFactory.getLog(DownLoadTaskV3.class);

    @Autowired
    private TaskInfoRepository taskInfoRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                // 步骤2：编写定时任务要执行的业务逻辑(可以进一步抽象)。
                logger.info("V3-开始下载银行 C 的对账文件");
                logger.info("V3-银行 C 对账文件下载完成，进行解密操作");
                logger.info("V3-银行 C 对账文件下载解密完成");
            }
        };

        // 步骤 4：根据任务名称从数据库获取 Cron 参数，设置任务触发器，触发任务执行。
        Trigger trigger = new Trigger() {
            /**
             * 每一次任务触发，都会调用一次该方法
             * 然后重新获取下一次的执行时间
             */
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {

                // 方式二：动态获取执行时间（从数据库、redis 等都可以做任务执行时间的存储管理，本次以数据库为例）
                TaskInfo taskInfo = new TaskInfo();
                // 数据库配置的任务名称，通过任务名称获取对应的任务执行时间
                taskInfo.setJobName("downLoadTaskV3");
                TaskInfo taskInfo1= taskInfoRepository.selectOne(new QueryWrapper<>(taskInfo));
                // 获取配置的任务执行时间 cron 表达式
                String cron = taskInfo1.getCron();
                System.out.println(cron);
                CronTrigger trigger = new CronTrigger(cron);
                return trigger.nextExecutionTime(triggerContext);
            }
        };
        // 设置任务触发器，触发任务执行。
        taskRegistrar.addTriggerTask(task, trigger);
    }
}
