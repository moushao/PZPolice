package com.tonypy.tonypy.addressbooks.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 组信息
 *
 * @author LZQ
 */
public class Group {


    /**
     * group_ID : 2006
     * group_NAME : 巡逻队
     * group_RESPOSE_NO : 3
     * group_USER : [{"group_ID":"2006","name":"张警长","role_ID":"警长","type":1,"user_ID":"6"},{"group_ID":"2006","name":"王五","role_ID":"警员","type":1,
     * "user_ID":"8"}]
     * sub_GROUP : []
     */

    private String group_ID;
    private String group_NAME;
    private String group_RESPOSE_NO;
    private List<GroupUSERBean> group_USER;
    private List<?> sub_GROUP;
    public int flag = 0;

    public String getGroup_ID() {
        return group_ID;
    }

    public void setGroup_ID(String group_ID) {
        this.group_ID = group_ID;
    }

    public String getGroup_NAME() {
        return group_NAME;
    }

    public void setGroup_NAME(String group_NAME) {
        this.group_NAME = group_NAME;
    }

    public String getGroup_RESPOSE_NO() {
        return group_RESPOSE_NO;
    }

    public void setGroup_RESPOSE_NO(String group_RESPOSE_NO) {
        this.group_RESPOSE_NO = group_RESPOSE_NO;
    }

    public List<GroupUSERBean> getGroup_USER() {
        return group_USER;
    }

    public void setGroup_USER(List<GroupUSERBean> group_USER) {
        this.group_USER = group_USER;
    }

    public List<?> getSub_GROUP() {
        return sub_GROUP;
    }

    public void setSub_GROUP(List<?> sub_GROUP) {
        this.sub_GROUP = sub_GROUP;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public static class GroupUSERBean implements Parcelable {
        /**
         * group_ID : 2006
         * name : 张警长
         * role_ID : 警长
         * type : 1
         * user_ID : 6
         */

        private String group_ID;
        private String name;
        private String role_ID;
        private int type;
        private String user_ID;
        public int flag = 0;
        public int state = 0;

        public String getGroup_ID() {
            return group_ID;
        }

        public void setGroup_ID(String group_ID) {
            this.group_ID = group_ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole_ID() {
            return role_ID;
        }

        public void setRole_ID(String role_ID) {
            this.role_ID = role_ID;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUser_ID() {
            return user_ID;
        }

        public void setUser_ID(String user_ID) {
            this.user_ID = user_ID;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.group_ID);
            dest.writeString(this.name);
            dest.writeString(this.role_ID);
            dest.writeInt(this.type);
            dest.writeString(this.user_ID);
            dest.writeInt(this.flag);
            dest.writeInt(this.state);
        }

        public GroupUSERBean() {
        }

        protected GroupUSERBean(Parcel in) {
            this.group_ID = in.readString();
            this.name = in.readString();
            this.role_ID = in.readString();
            this.type = in.readInt();
            this.user_ID = in.readString();
            this.flag = in.readInt();
            this.state = in.readInt();
        }

        public static final Parcelable.Creator<GroupUSERBean> CREATOR = new Parcelable.Creator<GroupUSERBean>() {
            @Override
            public GroupUSERBean createFromParcel(Parcel source) {
                return new GroupUSERBean(source);
            }

            @Override
            public GroupUSERBean[] newArray(int size) {
                return new GroupUSERBean[size];
            }
        };
    }
}
