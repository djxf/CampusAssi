package cn.nicolite.huthelper.model.bean;

/**
 * Created by nicolite on 17-10-24.
 */

public class Update {

    /**
     * school_opens : 2017-09-04
     * api_base_address : {"library":"172.16.64.7:8080","test_plan":"218.75.197.122:84"}
     */

    private String school_opens;
    private ApiBaseAddressBean api_base_address;

    public String getSchool_opens() {
        return school_opens;
    }

    public void setSchool_opens(String school_opens) {
        this.school_opens = school_opens;
    }

    public ApiBaseAddressBean getApi_base_address() {
        return api_base_address;
    }

    public void setApi_base_address(ApiBaseAddressBean api_base_address) {
        this.api_base_address = api_base_address;
    }

    public static class ApiBaseAddressBean {
        /**
         * library : 172.16.64.7:8080
         * test_plan : 218.75.197.122:84
         */

        private String library;
        private String test_plan;

        public String getLibrary() {
            return library;
        }

        public void setLibrary(String library) {
            this.library = library;
        }

        public String getTest_plan() {
            return test_plan;
        }

        public void setTest_plan(String test_plan) {
            this.test_plan = test_plan;
        }
    }
}
