package cn.nicolite.huthelper.model.bean;

import java.util.List;

/**
 * 考试计划实体类
 * Created by nicolite on 17-11-2.
 */

public class ExamResult {

    /**
     * code : 100
     * status : success
     * activity_message : Excited
     * res : {"exam":[{"KKDepNo":"08","CourseName":"大型数据库应用","Starttime":"2017-12-01 08:00:00","EndTime":"2017-12-01 09:40:00","Week_Num":"13","isset":"0","RoomName":"电气楼403"},{"KKDepNo":"08","CourseName":"Java程序设计","Starttime":"2017-12-04 10:00:00","EndTime":"2017-12-04 11:40:00","Week_Num":"14","isset":"0","RoomName":"电气楼405"},{"KKDepNo":"29","CourseName":"毛泽东思想和中国特色社会主义理论概论课2","Starttime":"2017-12-09 13:30:00","EndTime":"2017-12-09 14:40:00","Week_Num":"14","isset":"0","RoomName":"综合楼217"},{"KKDepNo":"08","CourseName":"计算机网络","Starttime":"2017-12-14 10:00:00","EndTime":"2017-12-14 11:40:00","Week_Num":"15","isset":"0","RoomName":"电气楼402"},{"KKDepNo":"08","CourseName":"计算机操作系统","Starttime":"2018-01-02 10:00:00","EndTime":"2018-01-02 11:40:00","Week_Num":"18","isset":"0","RoomName":"电气楼402"}],"cxexam":[{"KKDepNo":"08","CourseName":"大型数据库应用","Starttime":"2017-12-01 08:00:00","EndTime":"2017-12-01 09:40:00","Week_Num":"13","isset":"0","RoomName":"电气楼403"},{"KKDepNo":"08","CourseName":"Java程序设计","Starttime":"2017-12-04 10:00:00","EndTime":"2017-12-04 11:40:00","Week_Num":"14","isset":"0","RoomName":"电气楼405"},{"KKDepNo":"29","CourseName":"毛泽东思想和中国特色社会主义理论概论课2","Starttime":"2017-12-09 13:30:00","EndTime":"2017-12-09 14:40:00","Week_Num":"14","isset":"0","RoomName":"综合楼217"},{"KKDepNo":"08","CourseName":"计算机网络","Starttime":"2017-12-14 10:00:00","EndTime":"2017-12-14 11:40:00","Week_Num":"15","isset":"0","RoomName":"电气楼402"},{"KKDepNo":"08","CourseName":"计算机操作系统","Starttime":"2018-01-02 10:00:00","EndTime":"2018-01-02 11:40:00","Week_Num":"18","isset":"0","RoomName":"电气楼402"}]}
     * stuclass : 软件工程1504
     * stuname : 陈林祥
     * count : 5
     */

    private int code;
    private String status;
    private String message;
    private ResBean res;
    private String stuclass;
    private String stuname;
    private int count;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public String getStuclass() {
        return stuclass;
    }

    public void setStuclass(String stuclass) {
        this.stuclass = stuclass;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class ResBean {
        private List<Exam> exam;
        private List<Exam> cxexam;

        public List<Exam> getExam() {
            return exam;
        }

        public void setExam(List<Exam> exam) {
            this.exam = exam;
        }

        public List<Exam> getCxexam() {
            return cxexam;
        }

        public void setCxexam(List<Exam> cxexam) {
            this.cxexam = cxexam;
        }
    }
}
