package cn.cdqf.dmsjportal.common;


import lombok.Getter;

/**
 * 自定义异常
 * 1.不是自定义的异常 空指针异常
 * 2.自定义异常：
 */
@Getter
public class CustomException extends RuntimeException{

    private String message;

    public CustomException(String message){
        this.message = message;
    }

}
