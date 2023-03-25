package com.anixuil.manager_system.entity;

public class Rest {
    //状态码
    private Integer code;
    //消息
    private String msg;
    //数据
    private Object data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Rest() {
    }

    public Rest(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Rest{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    //成功
//    @Override
    public static Rest success(String msg, Object data){
        return new Rest(200,msg + "成功",data);
    }

    //失败
//    @Override
    public static Rest fail(String msg,Object data){
        return new Rest(400,msg + "失败",data);
    }

    // 服务器异常
    public static Rest error(String msg,Object data){
        return new Rest(500,msg + "异常",data);
    }


}
