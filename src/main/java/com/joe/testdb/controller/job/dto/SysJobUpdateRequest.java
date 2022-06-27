package com.joe.testdb.controller.job.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysJobUpdateRequest {

    /**
     * job id
     */
    @NotBlank
    private String id;
    /**
     * bean名称
     */
    @NotBlank
    @JsonProperty("bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @NotBlank
    @JsonProperty("method_name")
    private String methodName;
    /**
     * 方法参数
     */
    @JsonProperty("method_params")
    private String methodParams;
    /**
     * cron表达式
     */
    @NotBlank
    @JsonProperty("cron_expression")
    private String cronExpression;
    /**
     * 状态（1正常 0暂停）
     */
    @NotNull
    @JsonProperty("job_status")
    private Integer jobStatus;
    /**
     * 备注
     */
    private String remark;
}
