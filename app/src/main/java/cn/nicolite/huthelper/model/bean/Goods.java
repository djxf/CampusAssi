package cn.nicolite.huthelper.model.bean;

/**
 * 二手市场商品实体类
 * Created by nicolite on 17-10-13.
 */

public class Goods {

    /**
     * id : 3317
     * user_id : 13467
     * tit : 泰国牛奶洗面奶
     * prize : 28.00
     * created_on : 1天内
     * image : /uploads/userpics/201710/1507810537.640674169_thumb.jpg
     */

    private String id;
    private String user_id;
    private String tit;
    private String prize;
    private String created_on;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
