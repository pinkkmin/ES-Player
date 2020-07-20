package com.player.es.Utils;

import lombok.Data;
//统一结果封装
@Data
public class ResponseUnit {
    private int code;
    private String message;
    private  Object data;
    public ResponseUnit(){ }
    public ResponseUnit(int _code, String msg, Object _data) {
        code = _code;
        message = msg;
        data = _data;
    }
    public static ResponseUnit succ(Object data) {
        return succ(200, "操作成功", data);
    }
    public static ResponseUnit succ(int code, String msg, Object data) {
        ResponseUnit r = new ResponseUnit();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }
    public static ResponseUnit fail(String msg) {
        return fail(400, msg, null);
    }

    public static ResponseUnit fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public static ResponseUnit fail(int code, String msg, Object data) {
        ResponseUnit r = new ResponseUnit();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }
}
