package com.joe.testdb.service;

import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.schedule.entity.SysJob;
import com.joe.testdb.module.schedule.mapper.SysJobMapper;
import com.joe.testdb.module.schedule.service.SysJobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SysJobServiceTest {
    @Autowired
    SysJobService sysJobService;

    @MockBean
    SysJobMapper baseMapper;

    SysJob sysJob;

    @Before
    public void setUp(){
        sysJob = new SysJob();
        sysJob.setJobStatus(1);
    }

    @Test
    public void testStartAlreadyRun()  {
        given(baseMapper.selectById(anyString())).willReturn(sysJob);

        try {
            sysJobService.start("123");
        } catch (BusinessException e) {
//            验证方法调用
            Mockito.verify(baseMapper).selectById(anyString());
//            Mockito.verify(baseMapper,Mockito.never()).selectById(anyString());
            assert e.getMessage().equals("任务已在运行中");
        }
    }
}
