package cn.nicolite.huthelper.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 简易返回码
 */
public class Code {

    private int code;

    public Code() {
    }

    public Code(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}