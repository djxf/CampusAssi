package cn.nicolite.huthelper.model.bean;
import java.util.List;

/**
 * 空教室实体类
 */
public class EmptyRoom {

    private String xnxqid;
    private List<Data> data;
    private boolean success;
    public void setXnxqid(String xnxqid) {
        this.xnxqid = xnxqid;
    }
    public String getXnxqid() {
        return xnxqid;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

      public static class Data {

        private String jxl;
        private List<JsList> jsList;

        public void setJxl(String jxl) {
            this.jxl = jxl;
        }
        public String getJxl() {
            return jxl;
        }

        public void setJsList(List<JsList> jsList) {
            this.jsList = jsList;
        }
        public List<JsList> getJsList() {
            return jsList;
        }


          public static class JsList {


              public JsList(String jsid, String jzwid, String jsmc, int zws, String xqmc, String jsh, String jzwmc, int yxzws) {
                  this.jsid = jsid;
                  this.jzwid = jzwid;
                  this.jsmc = jsmc;
                  this.zws = zws;
                  this.xqmc = xqmc;
                  this.jsh = jsh;
                  this.jzwmc = jzwmc;
                  this.yxzws = yxzws;
              }

              private String jsid;
              private String jzwid;
              private String jsmc;
              private int zws;
              private String xqmc;
              private String jsh;
              private String jzwmc;
              private int yxzws;
              public void setJsid(String jsid) {
                  this.jsid = jsid;
              }
              public String getJsid() {
                  return jsid;
              }

              public void setJzwid(String jzwid) {
                  this.jzwid = jzwid;
              }
              public String getJzwid() {
                  return jzwid;
              }

              public void setJsmc(String jsmc) {
                  this.jsmc = jsmc;
              }
              public String getJsmc() {
                  return jsmc;
              }

              public void setZws(int zws) {
                  this.zws = zws;
              }
              public int getZws() {
                  return zws;
              }

              public void setXqmc(String xqmc) {
                  this.xqmc = xqmc;
              }
              public String getXqmc() {
                  return xqmc;
              }

              public void setJsh(String jsh) {
                  this.jsh = jsh;
              }
              public String getJsh() {
                  return jsh;
              }

              public void setJzwmc(String jzwmc) {
                  this.jzwmc = jzwmc;
              }
              public String getJzwmc() {
                  return jzwmc;
              }

              public void setYxzws(int yxzws) {
                  this.yxzws = yxzws;
              }
              public int getYxzws() {
                  return yxzws;
              }
          }
    }


}