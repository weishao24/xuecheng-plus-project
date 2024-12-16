package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author: weichongzhan
 * @create: 2024-12-05 14:56
 * @description: 和前端约定返回的异常信息模型
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    private int errCode;

    public RestErrorResponse(String errMessage){
        this.errMessage = errMessage;
    }

    public RestErrorResponse(String errMessage,int errCode){
        this.errMessage = errMessage;
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
