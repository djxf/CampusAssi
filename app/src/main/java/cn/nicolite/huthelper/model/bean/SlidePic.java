package cn.nicolite.huthelper.model.bean;
import java.util.List;

public class SlidePic {
        private String code;
        private Informatin data;

        public Informatin getData() { return data; }
        public void setDate(Informatin data) {
        this.data = data;
    }
        public String getCode() {
        return code;
    }
        public void setCode(String code) {
        this.code = code;
    }


        //静态内部类
        public static class Informatin {
            private String school_opens;
            private String calendar_url;
            private String map_url;
            private int im_msg_count;

            public int getIm_msg_count() {
                return im_msg_count;
            }

            public void setIm_msg_count(int im_msg_count) {
                this.im_msg_count = im_msg_count;
            }

            private APiClass api_base_address;


            public String getMap_url() {
                return map_url;
            }

            public void setMap_url(String map_url) {
                this.map_url = map_url;
            }

            private List<BannerPic> banner_pics;

            public List<BannerPic> getBanner_pics() {
                return banner_pics;
            }

            public void setBanner_pics(List<BannerPic> banner_pics) {
                this.banner_pics = banner_pics;
            }

            public static class BannerPic{
                private String pic_url;
                private String target_url;
                private String target_title;
                private String timeline_color;

                public String getTimeline_color() {
                    return timeline_color;
                }

                public void setTimeline_color(String timeline_color) {
                    this.timeline_color = timeline_color;
                }

                public String getTarget_title() {
                    return target_title;
                }

                public void setTarget_title(String target_title) {
                    this.target_title = target_title;
                }

                public String getPic_url() {
                    return pic_url;
                }

                public void setPic_url(String pic_url) {
                    this.pic_url = pic_url;
                }

                public String getTarget_url() {
                    return target_url;
                }

                public void setTarget_url(String target_url) {
                    this.target_url = target_url;
                }
            }
    }
}
