package com.gentrio.zhrb.bean;

import java.util.List;

/**
 * Created by Gentrio on 2016/10/21.
 */
public class LatestBean {


    /**
     * date : 20161021
     * stories : [{"images":["http://pic3.zhimg.com/f51238208a35edea22852e2c27277ada.jpg"],"type":0,"id":8897647,"ga_prefix":"102110","title":"大脑是怎么工作的？快让机器跟着学学"},{"title":"20 个我逛过的国内外良心景点","ga_prefix":"102109","images":["http://pic3.zhimg.com/552438e259b65a1738560ea1b3bdb6ce.jpg"],"multipic":true,"type":0,"id":8902082},{"images":["http://pic2.zhimg.com/f23a1cb690fb4c617ab90f30290a7e61.jpg"],"type":0,"id":8902296,"ga_prefix":"102108","title":"这个老外建的「雾霾净化塔」，真的有用吗？"},{"images":["http://pic3.zhimg.com/b0a73296e569e1a951ec497371ceedc2.jpg"],"type":0,"id":8895403,"ga_prefix":"102107","title":"当孩子遇到不顺利，需要让他们学会表达愤怒与悲伤"},{"images":["http://pic2.zhimg.com/9f2e42ec112357bbdd1c78cfad74942d.jpg"],"type":0,"id":8905032,"ga_prefix":"102107","title":"房价涨了，说好的房子不卖给我了，应该怎么办？"},{"images":["http://pic2.zhimg.com/d254580b7cf578cdb36ca1363525071d.jpg"],"type":0,"id":8903454,"ga_prefix":"102107","title":"微软和美国政府打了一场官司，改变了软件业的商业模式"},{"images":["http://pic2.zhimg.com/47dd70c001f15712e39b897e4b797131.jpg"],"type":0,"id":8905512,"ga_prefix":"102107","title":"读读日报 24 小时热门 TOP 5 · 近期口碑第一的国产剧"},{"images":["http://pic1.zhimg.com/9854b74f6cff775c0acad6cf82e74218.jpg"],"type":0,"id":8903464,"ga_prefix":"102106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/9a66a63f7d9b1c6c458df75805908c28.jpg","type":0,"id":8902296,"ga_prefix":"102108","title":"这个老外建的「雾霾净化塔」，真的有用吗？"},{"image":"http://pic3.zhimg.com/2630441c6afa002a1337c257bde5144e.jpg","type":0,"id":8905512,"ga_prefix":"102107","title":"读读日报 24 小时热门 TOP 5 · 近期口碑第一的国产剧"},{"image":"http://pic4.zhimg.com/909e9231d5c7e79d9055386cc498a267.jpg","type":0,"id":8903454,"ga_prefix":"102107","title":"微软和美国政府打了一场官司，改变了软件业的商业模式"},{"image":"http://pic4.zhimg.com/42a4b30dc0ced4e917610f60677a3893.jpg","type":0,"id":8904323,"ga_prefix":"102017","title":"知乎好问题 · 金融新人如何进行实用行业分析？"},{"image":"http://pic3.zhimg.com/e837e8ea09c5eec33aee74ece23d41ae.jpg","type":0,"id":8904268,"ga_prefix":"102016","title":"有哪些技术看起来很高端，其实原理很暴力很初级？"}]
     */

    private String date;
    /**
     * images : ["http://pic3.zhimg.com/f51238208a35edea22852e2c27277ada.jpg"]
     * type : 0
     * id : 8897647
     * ga_prefix : 102110
     * title : 大脑是怎么工作的？快让机器跟着学学
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic1.zhimg.com/9a66a63f7d9b1c6c458df75805908c28.jpg
     * type : 0
     * id : 8902296
     * ga_prefix : 102108
     * title : 这个老外建的「雾霾净化塔」，真的有用吗？
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public LatestBean(String date, List<StoriesBean> stories, List<TopStoriesBean> top_stories) {
        this.date = date;
        this.stories = stories;
        this.top_stories = top_stories;
    }

    public LatestBean() {
        super();
    }
}
