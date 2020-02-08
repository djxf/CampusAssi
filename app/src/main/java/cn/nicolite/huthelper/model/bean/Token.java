package cn.nicolite.huthelper.model.bean;

/**
 * Created by nicolite on 17-10-22.
 */

public class Token {
    /**
     * code : 200
     * status : 0
     * token : NWmIo8YJeMfIEU9TKpXrHJ0sG1nndZdw1RE6H/2UtHIYZ1JNgoWnKLd3mDnaedLCsMc7tHCmsjZv/q7Hax3tDA==
     * userId : 2
     */

    private int code;
    private String status;
    private String token;
    private String userId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
