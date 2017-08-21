package com.ftoul.androidclient.bean;

import java.io.Serializable;

/**
 * Created by M on 2016/10/18.
 */

public class BaseModel<T> implements Serializable {
    String id;
    String code;
    String message;
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
