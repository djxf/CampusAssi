package cn.nicolite.huthelper.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 强制教务系统课表实体类
 * create by djxf 2019
 */
@Entity
public class ScheduleQZ {

        private String jsxm;    //教师名称
        private String jsmc;    //教室名称
        private String jssj;    //结束时间
        private String kssj;    //开始时间
        private String kkzc;    //开课周数
        private String kcsj;    //10102
        private String kcmc;    //开课名称
        private String sjbz;    //0
    @Generated(hash = 827982586)
    public ScheduleQZ(String jsxm, String jsmc, String jssj, String kssj, String kkzc, String kcsj, String kcmc, String sjbz) {
        this.jsxm = jsxm;
        this.jsmc = jsmc;
        this.jssj = jssj;
        this.kssj = kssj;
        this.kkzc = kkzc;
        this.kcsj = kcsj;
        this.kcmc = kcmc;
        this.sjbz = sjbz;
    }

    @Generated(hash = 629398744)
    public ScheduleQZ() {
    }

    public void setJsxm(String jsxm) {
            this.jsxm = jsxm;
        }
        public String getJsxm() {
            return jsxm;
        }

        public void setJsmc(String jsmc) {
            this.jsmc = jsmc;
        }
        public String getJsmc() {
            return jsmc;
        }

        public void setJssj(String jssj) {
            this.jssj = jssj;
        }
        public String getJssj() {
            return jssj;
        }

        public void setKssj(String kssj) {
            this.kssj = kssj;
        }
        public String getKssj() {
            return kssj;
        }

        public void setKkzc(String kkzc) {
            this.kkzc = kkzc;
        }
        public String getKkzc() {
            return kkzc;
        }

        public void setKcsj(String kcsj) {
            this.kcsj = kcsj;
        }
        public String getKcsj() {
            return kcsj;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }
        public String getKcmc() {
            return kcmc;
        }

        public void setSjbz(String sjbz) {
            this.sjbz = sjbz;
        }
        public String getSjbz() {
            return sjbz;
        }
}
