package cn.nicolite.huthelper.model.bean;

/**
 * 修改qq实体类
 */
public class QQResult {
      int code;
      Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setDate(Data date) {
        this.data = date;
    }

   public static class Data {
            String qq;
            String username;

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
}
