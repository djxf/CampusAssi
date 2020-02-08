package cn.nicolite.huthelper.model.bean;

import java.util.List;


/**
 * Created by nicolite on 17-10-13.
 */

public class Weather {
    /**
     * data : {"yesterday":{"date":"20日星期三","high":"高温 24℃","fx":"北风","low":"低温 21℃","fl":"<![CDATA[<3级]]>","type":"小雨"},"city":"株洲","forecast":[{"date":"21日星期四","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"北风","type":"阵雨"},{"date":"22日星期五","high":"高温 30℃","fengli":"<![CDATA[<3级]]>","low":"低温 23℃","fengxiang":"北风","type":"多云"},{"date":"23日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 25℃","fengxiang":"南风","type":"多云"},{"date":"24日星期天","high":"高温 34℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"南风","type":"多云"},{"date":"25日星期一","high":"高温 35℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"南风","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"24"}
     * status : 1000
     * desc : OK
     */

    private DataBean data;
    private int status;
    private String desc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean {
        /**
         * yesterday : {"date":"20日星期三","high":"高温 24℃","fx":"北风","low":"低温 21℃","fl":"<![CDATA[<3级]]>","type":"小雨"}
         * city : 株洲
         * forecast : [{"date":"21日星期四","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 22℃","fengxiang":"北风","type":"阵雨"},{"date":"22日星期五","high":"高温 30℃","fengli":"<![CDATA[<3级]]>","low":"低温 23℃","fengxiang":"北风","type":"多云"},{"date":"23日星期六","high":"高温 32℃","fengli":"<![CDATA[<3级]]>","low":"低温 25℃","fengxiang":"南风","type":"多云"},{"date":"24日星期天","high":"高温 34℃","fengli":"<![CDATA[<3级]]>","low":"低温 26℃","fengxiang":"南风","type":"多云"},{"date":"25日星期一","high":"高温 35℃","fengli":"<![CDATA[<3级]]>","low":"低温 27℃","fengxiang":"南风","type":"多云"}]
         * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
         * wendu : 24
         */

        private YesterdayBean yesterday;
        private String city;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 20日星期三
             * high : 高温 24℃
             * fx : 北风
             * low : 低温 21℃
             * fl : <![CDATA[<3级]]>
             * type : 小雨
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 21日星期四
             * high : 高温 26℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 22℃
             * fengxiang : 北风
             * type : 阵雨
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
