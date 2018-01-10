package com.pvirtech.pzpolice.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 * 时间申报的详情
 */

public class DeclareEntity implements Parcelable {


    /**
     * DAYRECORD : [{"endTime":"2017-04-13 17:00","location":"彭州派出所","startTime":"2017-04-13 09:00","type":"上班"},{"endTime":"2017-04-13 19:00",
     * "location":"彭州派出所","startTime":"2017-04-13 17:00","type":"出差"}]
     * STATE : 1001
     */

    private int STATE;
    private List<DAYRECORDBean> DAYRECORD;

    public int getSTATE() {
        return STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public List<DAYRECORDBean> getDAYRECORD() {
        return DAYRECORD;
    }

    public void setDAYRECORD(List<DAYRECORDBean> DAYRECORD) {
        this.DAYRECORD = DAYRECORD;
    }

    public static class DAYRECORDBean implements Parcelable {
        /**
         * endTime : 2017-04-13 17:00
         * location : 彭州派出所
         * startTime : 2017-04-13 09:00
         * type : 上班
         */

        private String endTime;
        private String location;
        private String startTime;
        private String type;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.endTime);
            dest.writeString(this.location);
            dest.writeString(this.startTime);
            dest.writeString(this.type);
        }

        public DAYRECORDBean() {
        }

        protected DAYRECORDBean(Parcel in) {
            this.endTime = in.readString();
            this.location = in.readString();
            this.startTime = in.readString();
            this.type = in.readString();
        }

        public static final Creator<DAYRECORDBean> CREATOR = new Creator<DAYRECORDBean>() {
            @Override
            public DAYRECORDBean createFromParcel(Parcel source) {
                return new DAYRECORDBean(source);
            }

            @Override
            public DAYRECORDBean[] newArray(int size) {
                return new DAYRECORDBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.STATE);
        dest.writeList(this.DAYRECORD);
    }

    public DeclareEntity() {
    }

    protected DeclareEntity(Parcel in) {
        this.STATE = in.readInt();
        this.DAYRECORD = new ArrayList<DAYRECORDBean>();
        in.readList(this.DAYRECORD, DAYRECORDBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DeclareEntity> CREATOR = new Parcelable.Creator<DeclareEntity>() {
        @Override
        public DeclareEntity createFromParcel(Parcel source) {
            return new DeclareEntity(source);
        }

        @Override
        public DeclareEntity[] newArray(int size) {
            return new DeclareEntity[size];
        }
    };
}
