package com.hxb.common.controller;

import com.hxb.common.exception.BusinessException;
import com.hxb.common.model.ResultModel;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author hexb
 * @date 2019/11/25 18:50
 */
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    protected static final ThreadLocal<HttpServletRequest> requests = new ThreadLocal<>();
    protected static final ThreadLocal<HttpServletResponse> responses = new ThreadLocal<>();
    @ModelAttribute
    public void init(HttpServletRequest request,HttpServletResponse response){
        requests.set(request);
        responses.set(response);
    }

    public void cleanThreadLocal(){
        requests.remove();
        responses.remove();
    }

    public HttpServletRequest getHttpServletRequest(){
        return requests.get();
    }

    public HttpServletResponse getHttpServletResponse(){
        return responses.get();
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultModel handleMethodArgNotValidEx(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("Invalid Request:");
        Iterator var4 = bindingResult.getFieldErrors().iterator();
        while(var4.hasNext()) {
            FieldError fieldError = (FieldError)var4.next();
            if(var4.hasNext()){
                sb.append(fieldError.getDefaultMessage()).append(",");
            }else{
                sb.append(fieldError.getDefaultMessage());
            }
        }

        log.error(ex.getClass().getSimpleName()+":"+sb.toString());
        return ResultModel.builder()
                .code("ERROR")
                .msg(sb.toString())
                .build();
    }

    public void download(HttpServletResponse response,@NonNull File file) throws IOException {
        if(!file.exists()){
            throw new BusinessException("File is not exist");
        }
        String fileName= URLEncoder.encode(file.getName(),"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+ fileName);
        response.setContentType("application/octet-stream");
        BufferedInputStream bis=null;
        ServletOutputStream out = response.getOutputStream();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int len;
            // 设置缓冲区1024字节
            byte[]buffer=new byte[1024];
            while((len=bis.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
        }finally {
            out.flush();
            out.close();
            if(bis!=null){
                bis.close();
            }
        }
    }
}
