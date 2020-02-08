package cn.nicolite.huthelper.model.bean;

/**
 * 电费查询实体类
 * Created by nicolite on 17-10-31.
 */

public class Electric {

    /**
     * code : 200
     * ammeter : 13.27
     * balance : 7.79
     * time : 2017-10-25 20:57:21
     * msg : "你访问太快，稍后再试！"
     */

    private int code;
    private String electricity;
    private String balance;
    private String time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAmmeter() {
        return electricity;
    }

    public void setAmmeter(String electricity) {
        this.electricity = electricity;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
