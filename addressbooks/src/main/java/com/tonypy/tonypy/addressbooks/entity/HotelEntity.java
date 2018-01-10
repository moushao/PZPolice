package com.tonypy.tonypy.addressbooks.entity;

import java.util.ArrayList;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HotelEntity {

    public ArrayList<TagsEntity> allTagsList;

    public class TagsEntity {
        public String tagsName;
        public ArrayList<TagInfo> tagInfoList;

        public class TagInfo {
            public String tagName;

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int state;

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int flag;
        }
    }

}
