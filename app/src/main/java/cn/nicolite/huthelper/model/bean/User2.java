package cn.nicolite.huthelper.model.bean;

/**
 * 新教务系统用户实体类
 */

public class User2 {

    private boolean success;
    private String token;
    private User user;
    private String usertype;
    private String userrealname;
    private String userdwmc;
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    public String getUsertype() {
        return usertype;
    }

    public void setUserrealname(String userrealname) {
        this.userrealname = userrealname;
    }
    public String getUserrealname() {
        return userrealname;
    }

    public void setUserdwmc(String userdwmc) {
        this.userdwmc = userdwmc;
    }
    public String getUserdwmc() {
        return userdwmc;
    }
}
