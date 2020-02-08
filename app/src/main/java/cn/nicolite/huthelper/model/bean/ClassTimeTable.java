package cn.nicolite.huthelper.model.bean;

import java.util.List;

public class ClassTimeTable {
        private int code;
        private Data data;
        public void setCode(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public void setData(Data data) {
            this.data = data;
        }
        public Data getData() {
            return data;
        }


    public static class Data {

        private List<String> 交通工程学院;
        private List<String> 体育;
        private List<String> 体育学院;
        private List<String> 体育教学;
        private List<String> 冶金与材料工程学院;
        private List<String> 冶金工程;
        private List<String> 冶金材料工程;
        private List<String> 包装与材料工程学院;
        private List<String> 包装工程;
        private List<String> 包装设计艺术学院;
        private List<String> 商学院;
        private List<String> 国际学院;
        private List<String> 土木工程学科;
        private List<String> 土木工程学院;
        private List<String> 外国语学院;
        private List<String> 外国语言学及应用语言学;
        private List<String> 岩土工程;
        private List<String> 工商管理;
        private List<String> 工程;
        private List<String> 市政工程;
        private List<String> 城市与环境学院;
        private List<String> 戏剧与舞蹈学学科;
        private List<String> 教务处;
        private List<String> 文学与新闻传播学院;
        private List<String> 机械工程学院;
        private List<String> 机械电子工程;
        private List<String> 机械设计及理论;
        private List<String> 材料加工工程;
        private List<String> 材料学;
        private List<String> 材料工程;
        private List<String> 材料物理与化学;
        private List<String> 材料科学与工程学科;
        private List<String> 桥梁与隧道工程;

        private List<String> 法学院;
        private List<String> 物流工程;
        private List<String> 理学院;
        private List<String> 生命科学与化学学院;
        private List<String> 生物医学工程;
        private List<String> 生物医学工程学科;
        private List<String> 电气与信息工程学院;
        private List<String> 电气工程;
        private List<String> 电气工程学科;
        private List<String> 社会体育指导;
        private List<String> 管理科学与工程;
        private List<String> 管理科学与工程学科;
        private List<String> 经济与贸易学院;
        private List<String> 结构工程;
        private List<String> 绿色包装与低碳管理;
        private List<String> 网络化系统控制;
        private List<String> 美术学;
        private List<String> 美术学学科;
        private List<String> 艺术;
        private List<String> 艺术设计;
        private List<String> 计算机学院;
        private List<String> 计算机应用技术;
        private List<String> 计算机系统结构;
        private List<String> 计算机软件与理论;
        private List<String> 设计学;
        private List<String> 设计学学科;
        private List<String> 诉讼法学;
        private List<String> 车辆工程;
        private List<String> 醴陵陶瓷学院;
        private List<String> 音乐学院;
        private List<String> 马克思主义学院;
        private List<String> 高等教育研究所;
        public void set交通工程学院(List<String> 交通工程学院) {
            this.交通工程学院 = 交通工程学院;
        }
        public List<String> get交通工程学院() {
            return 交通工程学院;
        }

        //通用get方法
        public List<String> getList(String college){
            List<String> classlist = null;
            switch(college){
                case "交通工程学院":
                    classlist =  get交通工程学院();
                    break;
                case "体育学院":
                    classlist =  get体育学院();
                    break;
                case "体育教学":
                    classlist =  get体育教学();
                    break;
                case "冶金与材料工程学院":
                    classlist =  get冶金与材料工程学院();
                    break;
                case "冶金工程":
                    classlist =  get冶金工程();
                    break;
                case "冶金材料工程":
                    classlist =  get冶金材料工程();
                    break;
                case "包装与材料工程学院":
                    classlist =  get包装与材料工程学院();
                    break;
                case "包装工程":
                    classlist =  get包装工程();
                    break;
                case "包装设计艺术学院":
                    classlist =  get包装设计艺术学院();
                    break;
                case "商学院":
                    classlist =  get商学院();
                    break;
                case "国际学院":
                    classlist =  get国际学院();
                    break;
                case "土木工程学科":
                    classlist =  get土木工程学科();
                    break;
                case "土木工程学院":
                    classlist =  get土木工程学院();
                    break;
                case "外国语学院":
                    classlist =  get外国语学院();
                    break;
                case "外国语言学及应用语言学":
                    classlist =  get外国语言学及应用语言学();
                    break;
                case "岩土工程":
                    classlist =  get岩土工程();
                    break;
                case "工商管理":
                    classlist =  get工商管理();
                    break;
                case "工程":
                    classlist =  get工程();
                    break;
                case "市政工程":
                    classlist =  get市政工程();
                    break;
                case "城市与环境学院":
                    classlist =  get城市与环境学院();
                    break;
                case "戏剧与舞蹈学学科":
                    classlist =  get戏剧与舞蹈学学科();
                    break;
                case "文学与新闻传播学院":
                    classlist =  get文学与新闻传播学院();
                    break;
                case "机械工程学院":
                    classlist =  get机械工程学院();
                    break;
                case "机械电子工程":
                    classlist =  get机械电子工程();
                    break;
                case "机械设计及理论":
                    classlist =  get机械设计及理论();
                    break;
                case "材料加工工程":
                    classlist =  get材料加工工程();
                    break;
                case "材料学":
                    classlist =  get材料学();
                    break;
                case "材料工程":
                    classlist =  get材料工程();
                    break;
                case "材料物理与化学":
                    classlist =  get材料物理与化学();
                    break;
                case "材料科学与工程学科":
                    classlist =  get材料科学与工程学科();
                    break;
                case "桥梁与隧道工程":
                    classlist =  get桥梁与隧道工程();
                    break;
                case "法学院":
                    classlist =  get法学院();
                    break;
                case "物流工程":
                    classlist =  get物流工程();
                    break;
                case "理学院":
                    classlist =  get理学院();
                    break;
                case "生命科学与化学学院":
                    classlist =  get生命科学与化学学院();
                    break;
                case "生物医学工程":
                    classlist =  get生物医学工程();
                    break;
                case "生物医学工程学科":
                    classlist =  get生物医学工程学科();
                    break;
                case "电气与信息工程学院":
                    classlist =  get电气与信息工程学院();
                    break;
                case "电气工程":
                    classlist =  get电气工程();
                    break;
                case "电气工程学科":
                    classlist =  get电气工程学科();
                    break;
                case "社会体育指导":
                    classlist =  get社会体育指导();
                    break;
                case "管理科学与工程":
                    classlist =  get管理科学与工程();
                    break;
                case "管理科学与工程学科":
                    classlist =  get管理科学与工程学科();
                    break;
                case "经济与贸易学院":
                    classlist =  get经济与贸易学院();
                    break;
                case "结构工程":
                    classlist =  get结构工程();
                    break;
                case "绿色包装与低碳管理":
                    classlist =  get绿色包装与低碳管理();
                    break;
                case "网络化系统控制":
                    classlist =  get网络化系统控制();
                    break;
                case "美术学":
                    classlist =  get美术学();
                    break;
                case "美术学学科":
                    classlist =  get美术学学科();
                    break;
                case "艺术":
                    classlist =  get艺术();
                    break;
                case "艺术设计":
                    classlist =  get艺术设计();
                    break;
                case "计算机学院":
                    classlist =  get计算机学院();
                    break;
                case "计算机应用技术":
                    classlist =  get计算机应用技术();
                    break;
                case "计算机系统结构":
                    classlist =  get计算机系统结构();
                    break;
                case "计算机软件与理论":
                    classlist =  get计算机软件与理论();
                    break;
                case "设计学":
                    classlist =  get设计学();
                    break;
                case "设计学学科":
                    classlist =  get设计学学科();
                    break;
                case "诉讼法学":
                    classlist =  get诉讼法学();
                    break;
                case "车辆工程":
                    classlist =  get车辆工程();
                    break;
                case "醴陵陶瓷学院":
                    classlist =  get醴陵陶瓷学院();
                    break;
                case "音乐学院":
                    classlist =  get音乐学院();
                    break;
                case "马克思主义学院":
                    classlist =  get马克思主义学院();
                    break;
                case "高等教育研究所":
                    classlist =  get高等教育研究所();
                    break;
                    default:
                        //classlist.add("无");
                        break;
            }
            return classlist;
        }

//        public void set企业管理（含：财务管理、市场营销、人力资源管理）(List<String> 企业管理（含：财务管理、市场营销、人力资源管理）) {
//            this.企业管理（含：财务管理、市场营销、人力资源管理） = 企业管理（含：财务管理、市场营销、人力资源管理）;
//        }
//        public List<String> get企业管理（含：财务管理、市场营销、人力资源管理）() {
//            return 企业管理（含：财务管理、市场营销、人力资源管理）;
//        }

        public void set体育(List<String> 体育) {
            this.体育 = 体育;
        }
        public List<String> get体育() {
            return 体育;
        }

        public void set体育学院(List<String> 体育学院) {
            this.体育学院 = 体育学院;
        }
        public List<String> get体育学院() {
            return 体育学院;
        }

        public void set体育教学(List<String> 体育教学) {
            this.体育教学 = 体育教学;
        }
        public List<String> get体育教学() {
            return 体育教学;
        }

        public void set冶金与材料工程学院(List<String> 冶金与材料工程学院) {
            this.冶金与材料工程学院 = 冶金与材料工程学院;
        }
        public List<String> get冶金与材料工程学院() {
            return 冶金与材料工程学院;
        }

        public void set冶金工程(List<String> 冶金工程) {
            this.冶金工程 = 冶金工程;
        }
        public List<String> get冶金工程() {
            return 冶金工程;
        }

        public void set冶金材料工程(List<String> 冶金材料工程) {
            this.冶金材料工程 = 冶金材料工程;
        }
        public List<String> get冶金材料工程() {
            return 冶金材料工程;
        }

        public void set包装与材料工程学院(List<String> 包装与材料工程学院) {
            this.包装与材料工程学院 = 包装与材料工程学院;
        }
        public List<String> get包装与材料工程学院() {
            return 包装与材料工程学院;
        }

        public void set包装工程(List<String> 包装工程) {
            this.包装工程 = 包装工程;
        }
        public List<String> get包装工程() {
            return 包装工程;
        }

        public void set包装设计艺术学院(List<String> 包装设计艺术学院) {
            this.包装设计艺术学院 = 包装设计艺术学院;
        }
        public List<String> get包装设计艺术学院() {
            return 包装设计艺术学院;
        }

        public void set商学院(List<String> 商学院) {
            this.商学院 = 商学院;
        }
        public List<String> get商学院() {
            return 商学院;
        }

        public void set国际学院(List<String> 国际学院) {
            this.国际学院 = 国际学院;
        }
        public List<String> get国际学院() {
            return 国际学院;
        }

        public void set土木工程学科(List<String> 土木工程学科) {
            this.土木工程学科 = 土木工程学科;
        }
        public List<String> get土木工程学科() {
            return 土木工程学科;
        }

        public void set土木工程学院(List<String> 土木工程学院) {
            this.土木工程学院 = 土木工程学院;
        }
        public List<String> get土木工程学院() {
            return 土木工程学院;
        }

        public void set外国语学院(List<String> 外国语学院) {
            this.外国语学院 = 外国语学院;
        }
        public List<String> get外国语学院() {
            return 外国语学院;
        }

        public void set外国语言学及应用语言学(List<String> 外国语言学及应用语言学) {
            this.外国语言学及应用语言学 = 外国语言学及应用语言学;
        }
        public List<String> get外国语言学及应用语言学() {
            return 外国语言学及应用语言学;
        }

        public void set岩土工程(List<String> 岩土工程) {
            this.岩土工程 = 岩土工程;
        }
        public List<String> get岩土工程() {
            return 岩土工程;
        }

        public void set工商管理(List<String> 工商管理) {
            this.工商管理 = 工商管理;
        }
        public List<String> get工商管理() {
            return 工商管理;
        }

        public void set工程(List<String> 工程) {
            this.工程 = 工程;
        }
        public List<String> get工程() {
            return 工程;
        }

        public void set市政工程(List<String> 市政工程) {
            this.市政工程 = 市政工程;
        }
        public List<String> get市政工程() {
            return 市政工程;
        }

        public void set城市与环境学院(List<String> 城市与环境学院) {
            this.城市与环境学院 = 城市与环境学院;
        }
        public List<String> get城市与环境学院() {
            return 城市与环境学院;
        }

        public void set戏剧与舞蹈学学科(List<String> 戏剧与舞蹈学学科) {
            this.戏剧与舞蹈学学科 = 戏剧与舞蹈学学科;
        }
        public List<String> get戏剧与舞蹈学学科() {
            return 戏剧与舞蹈学学科;
        }

        public void set教务处(List<String> 教务处) {
            this.教务处 = 教务处;
        }
        public List<String> get教务处() {
            return 教务处;
        }

        public void set文学与新闻传播学院(List<String> 文学与新闻传播学院) {
            this.文学与新闻传播学院 = 文学与新闻传播学院;
        }
        public List<String> get文学与新闻传播学院() {
            return 文学与新闻传播学院;
        }

        public void set机械工程学院(List<String> 机械工程学院) {
            this.机械工程学院 = 机械工程学院;
        }
        public List<String> get机械工程学院() {
            return 机械工程学院;
        }

        public void set机械电子工程(List<String> 机械电子工程) {
            this.机械电子工程 = 机械电子工程;
        }
        public List<String> get机械电子工程() {
            return 机械电子工程;
        }

        public void set机械设计及理论(List<String> 机械设计及理论) {
            this.机械设计及理论 = 机械设计及理论;
        }
        public List<String> get机械设计及理论() {
            return 机械设计及理论;
        }

        public void set材料加工工程(List<String> 材料加工工程) {
            this.材料加工工程 = 材料加工工程;
        }
        public List<String> get材料加工工程() {
            return 材料加工工程;
        }

        public void set材料学(List<String> 材料学) {
            this.材料学 = 材料学;
        }
        public List<String> get材料学() {
            return 材料学;
        }

        public void set材料工程(List<String> 材料工程) {
            this.材料工程 = 材料工程;
        }
        public List<String> get材料工程() {
            return 材料工程;
        }

        public void set材料物理与化学(List<String> 材料物理与化学) {
            this.材料物理与化学 = 材料物理与化学;
        }
        public List<String> get材料物理与化学() {
            return 材料物理与化学;
        }

        public void set材料科学与工程学科(List<String> 材料科学与工程学科) {
            this.材料科学与工程学科 = 材料科学与工程学科;
        }
        public List<String> get材料科学与工程学科() {
            return 材料科学与工程学科;
        }

        public void set桥梁与隧道工程(List<String> 桥梁与隧道工程) {
            this.桥梁与隧道工程 = 桥梁与隧道工程;
        }
        public List<String> get桥梁与隧道工程() {
            return 桥梁与隧道工程;
        }

//        public void set民商法学（含：劳动法学、社会保障法学）(List<String> 民商法学（含：劳动法学、社会保障法学）) {
//            this.民商法学（含：劳动法学、社会保障法学） = 民商法学（含：劳动法学、社会保障法学）;
//        }
//        public List<String> get民商法学（含：劳动法学、社会保障法学）() {
//            return 民商法学（含：劳动法学、社会保障法学）;
//        }

        public void set法学院(List<String> 法学院) {
            this.法学院 = 法学院;
        }
        public List<String> get法学院() {
            return 法学院;
        }

        public void set物流工程(List<String> 物流工程) {
            this.物流工程 = 物流工程;
        }
        public List<String> get物流工程() {
            return 物流工程;
        }

        public void set理学院(List<String> 理学院) {
            this.理学院 = 理学院;
        }
        public List<String> get理学院() {
            return 理学院;
        }

        public void set生命科学与化学学院(List<String> 生命科学与化学学院) {
            this.生命科学与化学学院 = 生命科学与化学学院;
        }
        public List<String> get生命科学与化学学院() {
            return 生命科学与化学学院;
        }

        public void set生物医学工程(List<String> 生物医学工程) {
            this.生物医学工程 = 生物医学工程;
        }
        public List<String> get生物医学工程() {
            return 生物医学工程;
        }

        public void set生物医学工程学科(List<String> 生物医学工程学科) {
            this.生物医学工程学科 = 生物医学工程学科;
        }
        public List<String> get生物医学工程学科() {
            return 生物医学工程学科;
        }

        public void set电气与信息工程学院(List<String> 电气与信息工程学院) {
            this.电气与信息工程学院 = 电气与信息工程学院;
        }
        public List<String> get电气与信息工程学院() {
            return 电气与信息工程学院;
        }

        public void set电气工程(List<String> 电气工程) {
            this.电气工程 = 电气工程;
        }
        public List<String> get电气工程() {
            return 电气工程;
        }

        public void set电气工程学科(List<String> 电气工程学科) {
            this.电气工程学科 = 电气工程学科;
        }
        public List<String> get电气工程学科() {
            return 电气工程学科;
        }

        public void set社会体育指导(List<String> 社会体育指导) {
            this.社会体育指导 = 社会体育指导;
        }
        public List<String> get社会体育指导() {
            return 社会体育指导;
        }

        public void set管理科学与工程(List<String> 管理科学与工程) {
            this.管理科学与工程 = 管理科学与工程;
        }
        public List<String> get管理科学与工程() {
            return 管理科学与工程;
        }

        public void set管理科学与工程学科(List<String> 管理科学与工程学科) {
            this.管理科学与工程学科 = 管理科学与工程学科;
        }
        public List<String> get管理科学与工程学科() {
            return 管理科学与工程学科;
        }

        public void set经济与贸易学院(List<String> 经济与贸易学院) {
            this.经济与贸易学院 = 经济与贸易学院;
        }
        public List<String> get经济与贸易学院() {
            return 经济与贸易学院;
        }

        public void set结构工程(List<String> 结构工程) {
            this.结构工程 = 结构工程;
        }
        public List<String> get结构工程() {
            return 结构工程;
        }

        public void set绿色包装与低碳管理(List<String> 绿色包装与低碳管理) {
            this.绿色包装与低碳管理 = 绿色包装与低碳管理;
        }
        public List<String> get绿色包装与低碳管理() {
            return 绿色包装与低碳管理;
        }

        public void set网络化系统控制(List<String> 网络化系统控制) {
            this.网络化系统控制 = 网络化系统控制;
        }
        public List<String> get网络化系统控制() {
            return 网络化系统控制;
        }

        public void set美术学(List<String> 美术学) {
            this.美术学 = 美术学;
        }
        public List<String> get美术学() {
            return 美术学;
        }

        public void set美术学学科(List<String> 美术学学科) {
            this.美术学学科 = 美术学学科;
        }
        public List<String> get美术学学科() {
            return 美术学学科;
        }

        public void set艺术(List<String> 艺术) {
            this.艺术 = 艺术;
        }
        public List<String> get艺术() {
            return 艺术;
        }

        public void set艺术设计(List<String> 艺术设计) {
            this.艺术设计 = 艺术设计;
        }
        public List<String> get艺术设计() {
            return 艺术设计;
        }

        public void set计算机学院(List<String> 计算机学院) {
            this.计算机学院 = 计算机学院;
        }
        public List<String> get计算机学院() {
            return 计算机学院;
        }

        public void set计算机应用技术(List<String> 计算机应用技术) {
            this.计算机应用技术 = 计算机应用技术;
        }
        public List<String> get计算机应用技术() {
            return 计算机应用技术;
        }

        public void set计算机系统结构(List<String> 计算机系统结构) {
            this.计算机系统结构 = 计算机系统结构;
        }
        public List<String> get计算机系统结构() {
            return 计算机系统结构;
        }

        public void set计算机软件与理论(List<String> 计算机软件与理论) {
            this.计算机软件与理论 = 计算机软件与理论;
        }
        public List<String> get计算机软件与理论() {
            return 计算机软件与理论;
        }

        public void set设计学(List<String> 设计学) {
            this.设计学 = 设计学;
        }
        public List<String> get设计学() {
            return 设计学;
        }

        public void set设计学学科(List<String> 设计学学科) {
            this.设计学学科 = 设计学学科;
        }
        public List<String> get设计学学科() {
            return 设计学学科;
        }

        public void set诉讼法学(List<String> 诉讼法学) {
            this.诉讼法学 = 诉讼法学;
        }
        public List<String> get诉讼法学() {
            return 诉讼法学;
        }

        public void set车辆工程(List<String> 车辆工程) {
            this.车辆工程 = 车辆工程;
        }
        public List<String> get车辆工程() {
            return 车辆工程;
        }

        public void set醴陵陶瓷学院(List<String> 醴陵陶瓷学院) {
            this.醴陵陶瓷学院 = 醴陵陶瓷学院;
        }
        public List<String> get醴陵陶瓷学院() {
            return 醴陵陶瓷学院;
        }

        public void set音乐学院(List<String> 音乐学院) {
            this.音乐学院 = 音乐学院;
        }
        public List<String> get音乐学院() {
            return 音乐学院;
        }

        public void set马克思主义学院(List<String> 马克思主义学院) {
            this.马克思主义学院 = 马克思主义学院;
        }
        public List<String> get马克思主义学院() {
            return 马克思主义学院;
        }

        public void set高等教育研究所(List<String> 高等教育研究所) {
            this.高等教育研究所 = 高等教育研究所;
        }
        public List<String> get高等教育研究所() {
            return 高等教育研究所;
        }

    }
    }

