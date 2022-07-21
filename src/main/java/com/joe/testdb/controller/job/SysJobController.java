package com.joe.testdb.controller.job;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joe.testdb.constants.BootError;
import com.joe.testdb.controller.job.dto.SysJobCreateRequest;
import com.joe.testdb.controller.job.dto.SysJobUpdateRequest;
import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.schedule.CronTaskRegistrar;
import com.joe.testdb.module.schedule.SchedulingRunnable;
import com.joe.testdb.module.schedule.mapper.SysJobMapper;
import com.joe.testdb.module.schedule.entity.SysJob;
import com.joe.testdb.module.schedule.service.SysJobService;
import com.joe.testdb.util.DtoConvertUtil;
import com.joe.testdb.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.List;

/**
 * job
 */
@RestController
@RequestMapping("job")
public class SysJobController {

    @Autowired
    SysJobMapper sysJobMapper;

    @Autowired
    SysJobService sysJobService;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    /**
     * create
     * @param sysJobCreateRequest
     * @return
     * @throws BusinessException
     */
    @PostMapping("create")
    public Boolean create(@Valid @RequestBody SysJobCreateRequest sysJobCreateRequest) throws BusinessException {
        // 校验bean和method
        Object target;
        try{
             target = SpringContextUtils.getBean(sysJobCreateRequest.getBeanName());
        }catch (Exception e){
            throw new BusinessException(BootError.INVALID_PARAM, "bean", "不存在");
        }
        Method method = null;
        try{
            if (StringUtils.isNotEmpty(sysJobCreateRequest.getMethodParams())) {
                method = target.getClass().getDeclaredMethod(sysJobCreateRequest.getMethodName(), String.class);
            } else {
                method = target.getClass().getDeclaredMethod(sysJobCreateRequest.getMethodName());
            }
        }catch (Exception e){
            throw new BusinessException(BootError.INVALID_PARAM, "method", "不存在");
        }

        SysJob sysJob = DtoConvertUtil.copyFrom(sysJobCreateRequest, SysJob::new);
        return sysJobService.create(sysJob);
    }

    /**
     * start
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("start")
    public Boolean startJob(@RequestParam("id") String id) throws BusinessException{
        return sysJobService.start(id);
    }

    /**
     * stop
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("stop")
    public Boolean stopJob(@RequestParam("id") String id) throws BusinessException{
        return sysJobService.stop(id);
    }

    /**
     * delete
     * @param id
     * @return
     * @throws BusinessException
     */
    @DeleteMapping("delete")
    public Boolean delete(@RequestParam("id") String id) throws BusinessException {
        return sysJobService.delete(id);
    }

    /**
     * update
     * @param sysJobUpdateRequest
     * @return
     * @throws BusinessException
     */
    @PostMapping("update")
    public Boolean update(@RequestBody SysJobUpdateRequest sysJobUpdateRequest) throws BusinessException {
        SysJob sysJob = DtoConvertUtil.copyFrom(sysJobUpdateRequest, SysJob::new);
        return sysJobService.update(sysJob);
    }

    /**
     * list
     * @return
     */
    @PostMapping("list")
    public List<SysJob> list(){
        return sysJobService.list();
    }

    /**
     * runOnce
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("runOnce")
    public boolean runOnce(@RequestParam("id") String id) throws BusinessException {
//        配置代码运行一次
        return sysJobService.runOnce(id);
    }



}
