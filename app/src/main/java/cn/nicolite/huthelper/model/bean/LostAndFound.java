package cn.nicolite.huthelper.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nicolite on 17-11-12.
 */

public class LostAndFound implements Serializable{

    /**
     * id : 356
     * tit : 111
     * content : 111
     * locate : 111
     * time : 2017-10-17 00:00:00
     * user_id : 25065
     * pics : ["/uploads/loses/201611/1479433192.865763303_thumb.png"]
     * created_on : 2017-10-17
     * phone : 111
     * type : 1
     * username : 昵称
     * head_pic : /uploads/headpics/201710/1508411490.928838025.jpg
     * head_pic_thumb : /uploads/headpics/201710/1508411490.928838025_thumb.jpg
     */

    private String id;
    private String tit;
    private String content;
    private String locate;
    private String time;
    private String user_id;
    private String created_on;
    private String phone;
    private String type;
    private String username;
    private String head_pic;
    private String head_pic_thumb;
    private List<String> pics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getHead_pic_thumb() {
        return head_pic_thumb;
    }

    public void setHead_pic_thumb(String head_pic_thumb) {
        this.head_pic_thumb = head_pic_thumb;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
