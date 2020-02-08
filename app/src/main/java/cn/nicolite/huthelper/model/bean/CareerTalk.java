package cn.nicolite.huthelper.model.bean;

/**
 * 宣告会列表内容
 * Created by nicolite on 17-11-5.
 */

public class CareerTalk {

    /**
     * id : 542542
     * company : 湖南省轻工盐业集团有限公司
     * title : 湖南省轻工盐业集团有限公司
     * holdtime : 2017-11-06 10:00:00
     * univ_id : 89
     * universityShortName : 商学院
     * address : 图书馆一楼报告厅
     * logoUrl : http://cdn6.haitou.cc/university/89.png
     * is_cancel : 0
     * is_official : 0
     * isExpired : false
     * totalClicks : 110
     * isSaved : false
     * assocLiveId : 0
     * zone : cs
     * company_id : null
     * clicks : 0
     */

    private int id;
    private String company;
    private String title;
    private String holdtime;
    private int univ_id;
    private String universityShortName;
    private String address;
    private String logoUrl;
    private int is_cancel;
    private int is_official;
    private boolean isExpired;
    private int totalClicks;
    private boolean isSaved;
    private int assocLiveId;
    private String zone;
    private Object company_id;
    private int clicks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHoldtime() {
        return holdtime;
    }

    public void setHoldtime(String holdtime) {
        this.holdtime = holdtime;
    }

    public int getUniv_id() {
        return univ_id;
    }

    public void setUniv_id(int univ_id) {
        this.univ_id = univ_id;
    }

    public String getUniversityShortName() {
        return universityShortName;
    }

    public void setUniversityShortName(String universityShortName) {
        this.universityShortName = universityShortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    public int getIs_official() {
        return is_official;
    }

    public void setIs_official(int is_official) {
        this.is_official = is_official;
    }

    public boolean isIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }

    public boolean isIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public int getAssocLiveId() {
        return assocLiveId;
    }

    public void setAssocLiveId(int assocLiveId) {
        this.assocLiveId = assocLiveId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Object getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Object company_id) {
        this.company_id = company_id;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
