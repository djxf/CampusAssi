package cn.nicolite.huthelper.model.bean;

import java.util.List;

/**
 * Created by nicolite on 17-12-2.
 */

public class GradeRankResult {

    /**
     * code : 200
     * zxf : 110.5
     * gks : 0
     * wdxf : null
     * zhjd : 3.08
     * pjf : 78.19
     * rank : {"xnrank":[{"xn":"2015-2016","zhjd":"3.00","bjrank":"8","zyrank":"27","pjf":"82.94"},{"xn":"2016-2017","zhjd":"2.79","bjrank":"12","zyrank":"41","pjf":"75.38"}],"xqrank":[{"xn":"2015-2016","xq":"2","zhjd":"2.42","bjrank":"13","zyrank":"44","pjf":"78.71"},{"xn":"2016-2017","xq":"1","zhjd":"2.68","bjrank":"15","zyrank":"42","pjf":"75.08"},{"xn":"2015-2016","xq":"1","zhjd":"3.48","bjrank":"7","zyrank":"14","pjf":"86.22"},{"xn":"2016-2017","xq":"2","zhjd":"2.91","bjrank":"13","zyrank":"45","pjf":"75.69"}]}
     */

    private int code;
    private String zxf;
    private String gks;
    private String wdxf;
    private String zhjd;
    private String pjf;
    private RankBean rank;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getZxf() {
        return zxf;
    }

    public void setZxf(String zxf) {
        this.zxf = zxf;
    }

    public String getGks() {
        return gks;
    }

    public void setGks(String gks) {
        this.gks = gks;
    }

    public String getWdxf() {
        return wdxf;
    }

    public void setWdxf(String wdxf) {
        this.wdxf = wdxf;
    }

    public String getZhjd() {
        return zhjd;
    }

    public void setZhjd(String zhjd) {
        this.zhjd = zhjd;
    }

    public String getPjf() {
        return pjf;
    }

    public void setPjf(String pjf) {
        this.pjf = pjf;
    }

    public RankBean getRank() {
        return rank;
    }

    public void setRank(RankBean rank) {
        this.rank = rank;
    }

    public static class RankBean {
        private List<GradeRank> xnrank;
        private List<GradeRank> xqrank;

        public List<GradeRank> getXnrank() {
            return xnrank;
        }

        public void setXnrank(List<GradeRank> xnrank) {
            this.xnrank = xnrank;
        }

        public List<GradeRank> getXqrank() {
            return xqrank;
        }

        public void setXqrank(List<GradeRank> xqrank) {
            this.xqrank = xqrank;
        }
    }
}
