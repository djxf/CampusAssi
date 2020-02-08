package cn.nicolite.huthelper.model.bean;

import java.util.List;

/**
 * Created by nicolite on 17-12-2.
 * 课程表
 */

public class SyllabusResult {

    /**
     * code : 200
     * data : [{"dsz":0,"zs":[1,2],"xqj":"1","djj":"5","name":"毛泽东思想和中国特色社会主义理论概论课2","teacher":"邹素文","room":"公共201"},{"dsz":0,"zs":[1,2,3,4],"xqj":"1","djj":"9","name":"计算机操作系统","teacher":"左新娥","room":"公共229"},{"dsz":0,"zs":[1,2,3,4],"xqj":"2","djj":"1","name":"Java程序设计","teacher":"周浩","room":"公共201"},{"dsz":0,"zs":[1,2,3,4],"xqj":"3","djj":"1","name":"计算机网络","teacher":"文鸿","room":"公共304"},{"dsz":0,"zs":[1,2,3,4],"xqj":"3","djj":"9","name":"毛泽东思想和中国特色社会主义理论概论课2","teacher":"邹素文","room":"外语楼107"},{"dsz":0,"zs":[1,2,3,4],"xqj":"4","djj":"1","name":"Java程序设计","teacher":"周浩","room":"公共201"},{"dsz":0,"zs":[1,2,3,4],"xqj":"4","djj":"7","name":"计算机操作系统","teacher":"左新娥","room":"计通楼512"},{"dsz":0,"zs":[1,2,3,4],"xqj":"5","djj":"5","name":"大型数据库应用","teacher":"陶立新","room":"公共507"},{"dsz":0,"zs":[1,2,3,4],"xqj":"5","djj":"7","name":"计算机网络","teacher":"文鸿","room":"公共331"},{"dsz":2,"zs":[2,3,4,5,6,7,8,9,10],"xqj":"3","djj":"5","name":"大型数据库应用","teacher":"陶立新","room":"公共507"},{"dsz":0,"zs":[6,7,8,9,10,11,12,13],"xqj":"1","djj":"9","name":"计算机操作系统","teacher":"左新娥","room":"公共229"},{"dsz":0,"zs":[6,7,8,9,10,11,12],"xqj":"2","djj":"1","name":"Java程序设计","teacher":"周浩","room":"公共201"},{"dsz":0,"zs":[6,7,8,9,10,11,12,13],"xqj":"3","djj":"1","name":"计算机网络","teacher":"文鸿","room":"公共304"},{"dsz":0,"zs":[6,7,8,9,10,11,12,13,14,15],"xqj":"3","djj":"9","name":"毛泽东思想和中国特色社会主义理论概论课2","teacher":"邹素文","room":"外语楼107"},{"dsz":0,"zs":[6,7,8,9,10,11,12],"xqj":"4","djj":"1","name":"Java程序设计","teacher":"周浩","room":"公共201"},{"dsz":0,"zs":[6,7,8,9,10,11,12,13],"xqj":"4","djj":"7","name":"计算机操作系统","teacher":"左新娥","room":"计通楼512"},{"dsz":0,"zs":[6,7,8,9,10,11],"xqj":"5","djj":"5","name":"大型数据库应用","teacher":"陶立新","room":"公共507"},{"dsz":0,"zs":[6,7,8,9,10,11,12,13],"xqj":"5","djj":"7","name":"计算机网络","teacher":"文鸿","room":"公共331"}]
     */

    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dsz : 0
         * zs : [1,2]
         * xqj : 1
         * djj : 5
         * name : 毛泽东思想和中国特色社会主义理论概论课2
         * teacher : 邹素文
         * room : 公共201
         */

        private int dsz;
        private String xqj;
        private String djj;
        private String name;
        private String teacher;
        private String room;
        private List<Integer> zs;

        public int getDsz() {
            return dsz;
        }

        public void setDsz(int dsz) {
            this.dsz = dsz;
        }

        public String getXqj() {
            return xqj;
        }

        public void setXqj(String xqj) {
            this.xqj = xqj;
        }

        public String getDjj() {
            return djj;
        }

        public void setDjj(String djj) {
            this.djj = djj;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public List<Integer> getZs() {
            return zs;
        }

        public void setZs(List<Integer> zs) {
            this.zs = zs;
        }
    }
}
