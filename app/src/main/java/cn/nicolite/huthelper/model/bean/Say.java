package cn.nicolite.huthelper.model.bean;

import java.util.List;

/**
 * Created by nicolite on 17-10-13.
 */

public class Say {

    /**
     * id : 3628
     * user_id : 24546
     * content : Android 工大助手 0.9.4（11358）
     * 已进行推送更新。
     * 本次更新内容：
     * 1.优化了部分体验。
     * 2.适配了MIUI9
     * 3.二手市场、失物招领、说说对接私信
     * 4.做了一些内存优化，提升低配手机体验
     * <p>
     * pics : ["/uploads/moments/201709/1505478102.397529744_thumb.png","/uploads/moments/201709/1505478103.442737698_thumb.png"]
     * created_on : 2017-09-15
     * is_top : 0
     * likes : 2
     * view_cnt : 2055
     * username : NICOLITE
     * dep_name : 计算机学院
     * head_pic : /uploads/headpics/201709/1505546333.797338542.png
     * head_pic_thumb : /uploads/headpics/201709/1505546333.797338542_thumb.png
     * comments : [{"id":"9724","moment_id":"3628","comment":"...所以之前安卓二手市场是还没有对接私信的吗","user_id":"33406","created_on":"2017-09-15 20:33:54","username":"蓝蓝路"},{"id":"9725","moment_id":"3628","comment":"之前是没有","user_id":"24546","created_on":"2017-09-15 20:42:37","username":"NICOLITE"},{"id":"9728","moment_id":"3628","comment":"那我之前向可能是安卓用户的对方发送私信对方是不是无法收到且没有记录？","user_id":"33406","created_on":"2017-09-15 23:54:50","username":"蓝蓝路"},{"id":"9730","moment_id":"3628","comment":"只是没对接说说这些功能而已，私信功能肯定是正常能用的","user_id":"24546","created_on":"2017-09-16 00:25:23","username":"NICOLITE"},{"id":"9733","moment_id":"3628","comment":"了解啦 谢谢(-.-)","user_id":"33406","created_on":"2017-09-16 01:07:37","username":"蓝蓝路"}]
     */

    private String id;
    private String user_id;
    private String content;
    private String created_on;
    private String is_top;
    private String likes;
    private String view_cnt;
    private String username;
    private String dep_name;
    private String head_pic;
    private String head_pic_thumb;
    private boolean isLike;
    private List<String> pics;
    private List<CommentsBean> comments;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(String view_cnt) {
        this.view_cnt = view_cnt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * id : 9724
         * moment_id : 3628
         * comment : ...所以之前安卓二手市场是还没有对接私信的吗
         * user_id : 33406
         * created_on : 2017-09-15 20:33:54
         * username : 蓝蓝路
         */

        private String id;
        private String moment_id;
        private String comment;
        private String user_id;
        private String created_on;
        private String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoment_id() {
            return moment_id;
        }

        public void setMoment_id(String moment_id) {
            this.moment_id = moment_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
