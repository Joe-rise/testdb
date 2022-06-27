package com.joe.testdb.module.schedule.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.testdb.constants.BootError;
import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.schedule.AsyncService;
import com.joe.testdb.module.schedule.CronTaskRegistrar;
import com.joe.testdb.module.schedule.SchedulingRunnable;
import com.joe.testdb.module.schedule.entity.SysJob;
import com.joe.testdb.module.schedule.mapper.SysJobMapper;
import com.joe.testdb.util.JacksonUtils;
import com.joe.testdb.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Service
@Slf4j
public class SysJobService extends ServiceImpl<SysJobMapper, SysJob> {

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    AsyncService asyncService;

    @Transactional
    public boolean create(SysJob sysJob) throws BusinessException {
        Integer res;
        try{
            res = baseMapper.insert(sysJob);
        }catch (DuplicateKeyException e){
            throw new BusinessException(BootError.DATA_EXIST);
        }
        if (res <= 0 || StringUtils.isEmpty(sysJob.getId())) {
            throw new BusinessException(BootError.DB_FAILED, "test_user", "create", JacksonUtils.toJsonStrByJackson(sysJob));
        }
        if(sysJob.getJobStatus()==1){
            switchJobStatus(true, sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams(), sysJob.getCronExpression());
        }
        return true;
    }

    @Transactional
    public boolean delete(String id) throws  BusinessException{
        SysJob sysJob = baseMapper.selectById(id);
        if(sysJob.getJobStatus()==1){
            throw new BusinessException(BootError.GENERAL_ERROR,"任务正在运行，不能删除");
        }
        int i = baseMapper.deleteById(id);
        if(i==0){
            throw new BusinessException(BootError.DB_FAILED, "sys_job", "delete", JacksonUtils.toJsonStrByJackson(sysJob));
        }
        return true;
    }

    /**
     * 启动任务
     * @param id
     * @return
     * @throws BusinessException
     */
    public Boolean start(String id) throws BusinessException {
        SysJob sysJob = baseMapper.selectById(id);
        if(sysJob.getJobStatus()==1){
            throw new BusinessException(BootError.GENERAL_ERROR,"任务已在运行中");
        }
        sysJob.setJobStatus(1);
        int i = baseMapper.updateById(sysJob);
        if(i==0){
            throw new BusinessException(BootError.DB_FAILED, "sys_job", "start", JacksonUtils.toJsonStrByJackson(sysJob));
        }
        switchJobStatus(true, sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams(), sysJob.getCronExpression());
        return true;
    }

    /**
     * 停止任务
     * @param id
     * @return
     * @throws BusinessException
     */
    public Boolean stop(String id) throws BusinessException {
        SysJob sysJob = baseMapper.selectById(id);
        if(sysJob.getJobStatus()==0){
            throw new BusinessException(BootError.GENERAL_ERROR,"任务已停止");
        }
        sysJob.setJobStatus(0);
        int i = baseMapper.updateById(sysJob);
        if(i==0){
            throw new BusinessException(BootError.DB_FAILED, "sys_job", "stop", JacksonUtils.toJsonStrByJackson(sysJob));
        }
        switchJobStatus(false, sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams(), null);
        return true;
    }

    public boolean update(SysJob sysJob) throws BusinessException {
        SysJob existJob = baseMapper.selectById(sysJob.getId());
        if(existJob.getJobStatus()==1){
            switchJobStatus(false, existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams(), null);
        }
        int i = baseMapper.updateById(sysJob);
        if(i==0){
            // 数据库更新异常 重新打开之前关闭的任务
            switchJobStatus(true, existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams(), existJob.getCronExpression());
            throw new BusinessException(BootError.DB_FAILED, "sys_job", "update", JacksonUtils.toJsonStrByJackson(sysJob));
        }
        if(sysJob.getJobStatus()==1){
            switchJobStatus(true, sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams(), sysJob.getCronExpression());
        }
        return true;
    }

    private void switchJobStatus(boolean start,String beanName,String methodName,String methodParams,String cron){
        SchedulingRunnable task = new SchedulingRunnable(beanName, methodName, methodParams);
        if(start){
            cronTaskRegistrar.addCronTask(task, cron);
        }else{
            cronTaskRegistrar.removeCronTask(task);
        }

    }


    public boolean runOnce(String id) throws BusinessException {
        SysJob sysJob = baseMapper.selectById(id);
        if(sysJob==null){
            throw new BusinessException(BootError.GENERAL_ERROR, "任务不存在");
        }
        asyncService.execute(sysJob.getBeanName(),sysJob.getMethodName(),sysJob.getMethodParams());
        return true;
    }


}
