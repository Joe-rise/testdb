package com.joe.testdb.exception;

import com.joe.testdb.constants.BootError;
import com.joe.testdb.util.JacksonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class ResponseWrapperBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResponseWrapper){
            log.info("==> Url:{},ResponseBody:{}", request.getURI().getPath(), JacksonUtils.toJsonStrByJackson(body));
            return body;
        }
        ResponseWrapper<Object> resp = new ResponseWrapper<>();
        resp.setCode(BootError.SUCCESS.code());
        resp.setMsg(BootError.SUCCESS.message());
        resp.setData(body);
        log.info("==> Url:{},ResponseBody:{}", request.getURI().getPath(),JacksonUtils.toJsonStrByJackson(body));
        return resp;
    }
}
