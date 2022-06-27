package com.joe.testdb.service;

import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.user.entity.TestUser;
import com.joe.testdb.module.user.mapper.TestUserMapper;
import com.joe.testdb.module.user.service.TestUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private TestUser testUser;

    @Autowired
    private TestUserService testUserService;

    @MockBean
    TestUserMapper baseMapper;


//    执行每个方法前调用此方法
    @Before
    public void setUp() {
        // 完成对测试参数的初始化
        testUser = new TestUser();
        testUser.setUserName("unit_test");
        testUser.setMobile("18");
        testUser.setPassword("1234567890");
    }

    @Test
    @Transactional
    public void createTest() throws BusinessException {
        testUserService.create(testUser);
        TestUser createUser = testUserService.getById(testUser.getId());
        assert createUser != null;
        Assert.isTrue(testUser.getUserName().equals(createUser.getUserName()),"非同一个对象");
    }

}
