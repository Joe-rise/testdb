package com.joe.testdb.controller.testuser;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joe.testdb.config.ProjectConfig;
import com.joe.testdb.controller.testuser.dto.QueryRequest;
import com.joe.testdb.controller.testuser.dto.TestUserCreateRequest;
import com.joe.testdb.exception.BusinessException;
import com.joe.testdb.module.user.entity.TestUser;
import com.joe.testdb.module.user.mapper.TestUserMapper;
import com.joe.testdb.module.user.service.TestUserService;
import com.joe.testdb.util.DtoConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestUserController {

    @Autowired
    private TestUserService testUserService;

    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private ProjectConfig projectConfig;

    @PostMapping("/create")
    public String create(@Valid @RequestBody TestUserCreateRequest testUserCreateRequest) throws BusinessException {
        TestUser testUser = DtoConvertUtil.copyFrom(testUserCreateRequest, TestUser::new);
        return testUserService.create(testUser);

    }

    @GetMapping("/list")
    public String list(){
        return "success";
    }

    @PostMapping("/query")
    public Page queryPage(@RequestBody QueryRequest queryRequest) {
        Page page = new Page<>(queryRequest.getCurrent() == null ? 1 : queryRequest.getCurrent(),
                queryRequest.getSize() == null ? projectConfig.getPageSize() : queryRequest.getSize());
        return testUserMapper.selectPage(page, null);

    }
}
