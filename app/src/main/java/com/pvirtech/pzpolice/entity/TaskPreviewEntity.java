package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/11.
 */

public class TaskPreviewEntity {


    /**
     * ASSIGNCOUNT : {"COMPLETE":1,"NOTCOMPLETE":4}
     * DAILYCOUNT : {"COMPLETE":1,"NOTCOMPLETE":10}
     */

    private ASSIGNCOUNTBean ASSIGNCOUNT;
    private DAILYCOUNTBean DAILYCOUNT;

    public ASSIGNCOUNTBean getASSIGNCOUNT() {
        return ASSIGNCOUNT;
    }

    public void setASSIGNCOUNT(ASSIGNCOUNTBean ASSIGNCOUNT) {
        this.ASSIGNCOUNT = ASSIGNCOUNT;
    }

    public DAILYCOUNTBean getDAILYCOUNT() {
        return DAILYCOUNT;
    }

    public void setDAILYCOUNT(DAILYCOUNTBean DAILYCOUNT) {
        this.DAILYCOUNT = DAILYCOUNT;
    }

    public static class ASSIGNCOUNTBean {
        /**
         * COMPLETE : 1
         * NOTCOMPLETE : 4
         */

        private int COMPLETE;
        private int NOTCOMPLETE;

        public int getCOMPLETE() {
            return COMPLETE;
        }

        public void setCOMPLETE(int COMPLETE) {
            this.COMPLETE = COMPLETE;
        }

        public int getNOTCOMPLETE() {
            return NOTCOMPLETE;
        }

        public void setNOTCOMPLETE(int NOTCOMPLETE) {
            this.NOTCOMPLETE = NOTCOMPLETE;
        }
    }

    public static class DAILYCOUNTBean {
        /**
         * COMPLETE : 1
         * NOTCOMPLETE : 10
         */

        private int COMPLETE;
        private int NOTCOMPLETE;

        public int getCOMPLETE() {
            return COMPLETE;
        }

        public void setCOMPLETE(int COMPLETE) {
            this.COMPLETE = COMPLETE;
        }

        public int getNOTCOMPLETE() {
            return NOTCOMPLETE;
        }

        public void setNOTCOMPLETE(int NOTCOMPLETE) {
            this.NOTCOMPLETE = NOTCOMPLETE;
        }
    }
}
