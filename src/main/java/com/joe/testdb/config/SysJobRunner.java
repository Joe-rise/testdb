package com.joe.testdb.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.joe.testdb.module.schedule.CronTaskRegistrar;
import com.joe.testdb.module.schedule.SchedulingRunnable;
import com.joe.testdb.module.schedule.entity.SysJob;
import com.joe.testdb.module.schedule.mapper.SysJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private SysJobMapper sysJobRepository;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<SysJob> jobList = sysJobRepository.getSysJobListByStatus();
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (SysJob job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }

            logger.info("定时任务已加载完毕...");
        }
    }
}
