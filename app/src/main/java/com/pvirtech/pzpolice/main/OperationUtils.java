package com.pvirtech.pzpolice.main;

import android.content.Context;
import android.content.Intent;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.enumeration.CommuityAlarmEnum;
import com.pvirtech.pzpolice.enumeration.CommuitySecurityEnum;
import com.pvirtech.pzpolice.enumeration.CommunityBaseCaseEnum;
import com.pvirtech.pzpolice.enumeration.CommunityCaseEnum;
import com.pvirtech.pzpolice.enumeration.CommunityInvestigationEnum;
import com.pvirtech.pzpolice.enumeration.CommunityOtherCaseEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationAlarmEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationBaseCaseEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationOtherCaseEnum;
import com.pvirtech.pzpolice.enumeration.InvestigationTypeEnum;
import com.pvirtech.pzpolice.enumeration.OfficeTypeEnum;
import com.pvirtech.pzpolice.enumeration.PatrolTypeEnum;
import com.pvirtech.pzpolice.enumeration.RoleEnum;
import com.pvirtech.pzpolice.ui.activity.business.community.AlarmActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.BusinessBaseCaseActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.EconomicInvestigationActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.FourVisitActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.NationalWorkActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.NormalCaseActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.PopulationSupervisionActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.ProvideCluesActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.SecurityCaseActivity;
import com.pvirtech.pzpolice.ui.activity.business.community.TrafficPunishmentActivity;
import com.pvirtech.pzpolice.ui.activity.business.investigation.CaseHandActivity;
import com.pvirtech.pzpolice.ui.activity.business.investigation.InvestigationArrestActivity;
import com.pvirtech.pzpolice.ui.activity.business.investigation.InvestigationCarRobberyActivity;
import com.pvirtech.pzpolice.ui.activity.business.investigation.InvestigationPersonActivity;
import com.pvirtech.pzpolice.ui.activity.business.investigation.InvestigationSandActivity;
import com.pvirtech.pzpolice.ui.activity.business.office.OfficePublishActivity;
import com.pvirtech.pzpolice.ui.activity.business.patrol.PatrolTrafficActivity;
import com.pvirtech.pzpolice.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by youpengda on 2017/3/2.
 */

public class OperationUtils {
    /**
     * 计算时长
     *
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static String getIntervalTime(String dateOne, String dateTwo) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long between = 0;
        try {
            java.util.Date begin = dfs.parse(dateOne);
            java.util.Date end = dfs.parse(dateTwo);
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        if (between <= 0) {
            return "";
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
//        System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒");
        String data = day + "天" + hour + "小时" + min + "分";
        return data;
    }


    /**
     * 就算天数    整天
     *
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static String getDays(String dateOne, String dateTwo) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
        long between = 0;
        try {
            java.util.Date begin = dfs.parse(dateOne);
            java.util.Date end = dfs.parse(dateTwo);
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        if (between < 0) {
            return "";
        }
        long day = between / (24 * 60 * 60 * 1000);
        return String.valueOf(day + 1);
    }


    /**
     * 计算日期是否在今天之前
     *
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static String compareTime(String dateOne, String dateTwo) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
        long between = 0;
        try {
            java.util.Date begin = dfs.parse(dateOne);
            java.util.Date end = dfs.parse(dateTwo);

            SimpleDateFormat dfs2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String tonight = dateOne + " 23:59";
            java.util.Date dateTonight = dfs2.parse(tonight);
            long currentTime = System.currentTimeMillis();
            long longDateTonight = dateTonight.getTime();
            if (longDateTonight < currentTime) {
                return "请假时间不能为今天之前";
            }
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
            return "请选择时间";
        }

        if (between >= 0) {
            return Constant.SUCCESS;
        } else {
            return "开始时间大于结束时间，请重新选择时间";
        }
    }


    /**
     * 判断日期是否过期
     */

    public static boolean getBeOverdue(String date) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (date.length() == 10) {
            date = date + " 09:00";
        }

        java.util.Date dateTonight = null;
        try {
            dateTonight = dfs.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentTime = System.currentTimeMillis();
        long longDateTonight = dateTonight.getTime();
        if (currentTime > longDateTonight) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 计算时长   天数和分都可以
     *
     * @param dateOne
     * @param dateTwo
     * @return
     */

    public static String getTotalTime(String dateOne, String dateTwo) {
        java.util.Date begin = new Date();
        SimpleDateFormat dfsBegin = null, dfsEnd = null;
        java.util.Date end = new Date();
        long between = 0;

        /**
         * 开始时间
         */
        if (dateOne.length() == 10) {
            dfsBegin = new SimpleDateFormat("yyyy-MM-dd");
        } else if (dateOne.length() == 16) {
            dfsBegin = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        try {
            begin = dfsBegin.parse(dateOne);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**
         * 结束时间
         */
        if (dateTwo.length() == 10) {
            dfsEnd = new SimpleDateFormat("yyyy-MM-dd");
        } else if (dateTwo.length() == 16) {
            dfsEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        try {
            end = dfsEnd.parse(dateTwo);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        if (between < 0) {
            return "";
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        String data = day + "天" + hour + "小时" + min + "分";

        if (dateOne.equals(dateTwo) && dateOne.length() == 10 && dateTwo.length() == 10) {
            data = "1天";
        }
        return data;
    }


    /**
     * 计算销假的天数
     */
    public static int getSickLeave09(String date) {
        java.util.Date end = new Date();
        java.util.Date now = new Date();
        SimpleDateFormat YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        long between = 0;
        String dateNight = "";
        int data = 0;
        if (date.length() == 10) {
            dateNight = date + " 23:59";
        }
        try {
            end = YMDHM.parse(dateNight);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * 如果是今天09:00以前就加一天
         */
        String today = YMD.format(now);
        today = today + " 09:00";
        java.util.Date date09 = new Date();
        try {
            date09 = YMDHM.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date09.getTime() > now.getTime()) {//如果是九点以前,天数
            data++;
        }

        between = (end.getTime() - now.getTime());// 得到两者的毫秒数
        if (between < 0) {
            return 0;
        }
        long day = between / (24 * 60 * 60 * 1000);
        data = data + (int) day;
        return data;
    }

    /**
     * 计算假期的剩余天数
     */
    public static int getSickLeave(String start, String end) {
        if (start.length() != 10 || end.length() != 10) {
            return 0;
        }
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = new Date();
        Date dateEnd = new Date();
        Date dateNow = new Date();
        try {
            dateStart = YMD.parse(start);
            dateEnd = YMD.parse(end);
            dateNow = YMD.parse(TimeUtil.getYMD());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //正在休假中
        if (dateNow.getTime() >= dateStart.getTime() && dateEnd.getTime() >= dateNow.getTime()) {
            return (int) ((dateEnd.getTime() - dateNow.getTime()) / (24 * 60 * 60 * 1000));
        }
        //还没有开始休假
        if (dateNow.getTime() < dateStart.getTime() && dateNow.getTime() < dateEnd.getTime()) {
            return (int) ((dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        }

        return 0;
    }

    /**
     * 得到所领导权限
     */

    public static boolean getRoleLeader() {
        String ROLE_ID = AppValue.getInstance().getmUserInfo().getROLE_ID();
        if (ROLE_ID.equals(RoleEnum.LEADER.getValue().getRoleId())) {
            return true;
        } else if (ROLE_ID.equals(RoleEnum.VICE_LEADER.getValue().getRoleId())) {
            return true;
        }

        return false;
    }


    /**
     * 领导和警长权限
     *
     * @return
     */
    public static boolean getRoleLeaderAndSheriff() {
        String ROLE_ID = AppValue.getInstance().getmUserInfo().getROLE_ID();
        if (ROLE_ID.equals(RoleEnum.LEADER.getValue().getRoleId())) {
            return true;
        } else if (ROLE_ID.equals(RoleEnum.VICE_LEADER.getValue().getRoleId())) {
            return true;
        } else if (ROLE_ID.equals(RoleEnum.SHERIFF.getValue().getRoleId())) {
            return true;
        }

        return false;
    }

    public static int[] getRandomColor(Context context) {
        int[] colaors_random = new int[5];
        colaors_random[0] = context.getResources().getColor(R.color.random01);
        colaors_random[1] = context.getResources().getColor(R.color.random02);
        colaors_random[2] = context.getResources().getColor(R.color.random03);
        colaors_random[3] = context.getResources().getColor(R.color.random04);
        colaors_random[4] = context.getResources().getColor(R.color.random05);

        return colaors_random;
    }


    public static String getInterceptString(String notice) {
        if (notice.length() > Constant.MAXIMUM_CHARACTER_LENGTH) {
            notice = notice.substring(0, Constant.MAXIMUM_CHARACTER_LENGTH - 1) + "...";
        }
        return notice;
    }

    public static Intent getCaseActivityIntent(Context mContext, int type, Intent intent) {

        /**
         * 行政案件办理
         */
        if (type == CommunityCaseEnum.PUBLIC_SECURITY.getValue().getID()) {
            /**
             * 治安案件
             */
            intent.setClass(mContext, SecurityCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityCaseEnum.PUBLIC_SECURITY.getValue().getContent());
        }

        /**
         * ====================治安案件
         */
        if (type == CommuitySecurityEnum.GAMBLING.getValue().getID()) {
            /**
             * 涉赌案件
             */
            intent.setClass(mContext, SecurityCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommuitySecurityEnum.GAMBLING.getValue().getContent());
        }
        if (type == CommuitySecurityEnum.JURISPRUDENCE.getValue().getID()) {
            /**
             * 涉黄案件
             */
            intent.setClass(mContext, SecurityCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommuitySecurityEnum.JURISPRUDENCE.getValue().getContent());
        }
        if (type == CommuitySecurityEnum.POISON.getValue().getID()) {
            /**
             * 涉毒案件
             */
            intent.setClass(mContext, SecurityCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommuitySecurityEnum.POISON.getValue().getContent());
        }
        /**
         * =================治安案件
         */

        else if (type == CommunityCaseEnum.FIRE_CONTROL.getValue().getID()) {
            /**
             * 消防案件
             */
            intent.setClass(mContext, NormalCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityCaseEnum.FIRE_CONTROL.getValue().getContent());
        } else if (type == CommunityCaseEnum.NATIONAL_SECURITY.getValue().getID()) {
            /**
             * 国保案件
             */
            intent.setClass(mContext, NormalCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityCaseEnum.NATIONAL_SECURITY.getValue().getContent());
        } else if (type == CommunityCaseEnum.OTHER.getValue().getID()) {
            /**
             * 其他案件
             */
            intent.setClass(mContext, NormalCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityCaseEnum.OTHER.getValue().getContent());
        } else if (type == CommunityCaseEnum.TRAFFIC.getValue().getID()) {
            /**
             * 交通案件
             */
            intent.setClass(mContext, TrafficPunishmentActivity.class);
            intent.putExtra(Constant.TITLE, CommunityCaseEnum.TRAFFIC.getValue().getContent());
        }


        /**
         * 基础工作
         */
        else if (type == CommunityBaseCaseEnum.FOUR_VISIT.getValue().getID()) {
            /**
             * 四必访
             */
            intent.setClass(mContext, FourVisitActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.FOUR_VISIT.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.NATIONAL_WORK.getValue().getID()) {
            /**
             * 国保工作
             */
            intent.setClass(mContext, NationalWorkActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.NATIONAL_WORK.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.SPECIAL_CASE_MANAGE.getValue().getID()) {
            /**
             * 特业管理
             */
            intent.setClass(mContext, BusinessBaseCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.SPECIAL_CASE_MANAGE.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.DANGEROUS_CHEMISTRY.getValue().getID()) {
            /**
             * 枪爆危化
             */
            intent.setClass(mContext, BusinessBaseCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.DANGEROUS_CHEMISTRY.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.COMMUNITY_YARD.getValue().getID()) {
            /**
             * 小区院落
             */
            intent.setClass(mContext, BusinessBaseCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.COMMUNITY_YARD.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.POPULATION_SUPERVISION.getValue().getID()) {
            /**
             * 重点人口监改对象重性精神病人
             */
            intent.setClass(mContext, PopulationSupervisionActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.POPULATION_SUPERVISION.getValue().getContent());
        } else if (type == CommunityBaseCaseEnum.SCHOOL_POLICE_CASE.getValue().getID()) {
            /**
             * 学校管理
             */
            intent.setClass(mContext, BusinessBaseCaseActivity.class);
            intent.putExtra(Constant.TITLE, CommunityBaseCaseEnum.SCHOOL_POLICE_CASE.getValue().getContent());
        }

        /**
         *刑侦基础
         */
        else if (type == CommunityInvestigationEnum.CRIMINAL_INVESTIGATION.getValue().getID()) {
            /**
             * 刑侦工作
             */
            intent.setClass(mContext, EconomicInvestigationActivity.class);
            intent.putExtra(Constant.TITLE, CommunityInvestigationEnum.CRIMINAL_INVESTIGATION.getValue().getContent());
        } else if (type == CommunityInvestigationEnum.ECONOMIC_INVESTIGATION.getValue().getID()) {
            /**
             * 经侦工作
             */
            intent.setClass(mContext, EconomicInvestigationActivity.class);
            intent.putExtra(Constant.TITLE, CommunityInvestigationEnum.ECONOMIC_INVESTIGATION.getValue().getContent());
        }


        /**
         * 接处警
         */
        else if (type == CommuityAlarmEnum.DISPUTE.getValue().getID()) {
            /**
             * 纠纷
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.DISPUTE.getValue().getContent());
        } else if (type == CommuityAlarmEnum.SEEK_HELP.getValue().getID()) {
            /**
             * 求助
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.SEEK_HELP.getValue().getContent());
        } else if (type == CommuityAlarmEnum.TRAFFIC_ACCIDENT.getValue().getID()) {
            /**
             * 交通事故
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.TRAFFIC_ACCIDENT.getValue().getContent());
        } else if (type == CommuityAlarmEnum.ADMINISTRATIVE_CASE.getValue().getID()) {
            /**
             * 行政案件
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.ADMINISTRATIVE_CASE.getValue().getContent());
        } else if (type == CommuityAlarmEnum.CRIMINAL_CASE.getValue().getID()) {
            /**
             * 刑事案件
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.CRIMINAL_CASE.getValue().getContent());
        }


        /**
         * 其他
         */
        else if (type == CommunityOtherCaseEnum.CRIMINAL.getValue().getID()) {
            /**
             * 刑事
             */
            intent.setClass(mContext, ProvideCluesActivity.class);
            intent.putExtra(Constant.TITLE, CommunityOtherCaseEnum.CRIMINAL.getValue().getContent());
        } else if (type == CommunityOtherCaseEnum.ADMINISTRATION.getValue().getID()) {
            /**
             * 行政
             */
            intent.setClass(mContext, ProvideCluesActivity.class);
            intent.putExtra(Constant.TITLE, CommunityOtherCaseEnum.ADMINISTRATION.getValue().getContent());
        }


        /**
         * 案侦
         */
      /*  else if (type == InvestigationTypeEnum.ALARM.getValue().getID()) {
            *//*
            intent.setClass(mContext, AlarmActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationTypeEnum.ALARM.getValue().getContent());
        }*/

        /**
         * 接处警
         */
        else if (type == InvestigationAlarmEnum.DISPUTE.getValue().getID()) {
            /**
             * 纠纷
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.DISPUTE.getValue().getContent());
        } else if (type == InvestigationAlarmEnum.SEEK_HELP.getValue().getID()) {
            /**
             * 求助
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.SEEK_HELP.getValue().getContent());
        } else if (type == InvestigationAlarmEnum.TRAFFIC_ACCIDENT.getValue().getID()) {
            /**
             * 交通事故
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.TRAFFIC_ACCIDENT.getValue().getContent());
        } else if (type == InvestigationAlarmEnum.ADMINISTRATIVE_CASE.getValue().getID()) {
            /**
             * 行政案件
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.ADMINISTRATIVE_CASE.getValue().getContent());
        } else if (type == InvestigationAlarmEnum.CRIMINAL_CASE.getValue().getID()) {
            /**
             * 刑事案件
             */
            intent.setClass(mContext, AlarmActivity.class);
//            intent.putExtra(Constant.TITLE, CommuityAlarmEnum.CRIMINAL_CASE.getValue().getContent());
        } else if (type == InvestigationTypeEnum.CASE.getValue().getID()) {
            /**
             * 案件办理
             */
            intent.setClass(mContext, CaseHandActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationTypeEnum.CASE.getValue().getContent());
        }

        /**
         * 案侦业务
         * 基础工作
         */
        else if (type == InvestigationBaseCaseEnum.FINGERPRINT_COLLECT.getValue().getID()) {
            /**
             * 指纹
             */
            intent.setClass(mContext, PopulationSupervisionActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationBaseCaseEnum.FINGERPRINT_COLLECT.getValue().getContent());
        } else if (type == InvestigationBaseCaseEnum.DNA_COLLECT.getValue().getID()) {
            /**
             * DNA
             */
            intent.setClass(mContext, PopulationSupervisionActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationBaseCaseEnum.DNA_COLLECT.getValue().getContent());
        } else if (type == InvestigationBaseCaseEnum.PHONE_INFO_COLLECT.getValue().getID()) {
            /**
             * 手机
             */
            intent.setClass(mContext, PopulationSupervisionActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationBaseCaseEnum.PHONE_INFO_COLLECT.getValue().getContent());
        } else if (type == InvestigationBaseCaseEnum.HIGH_RISK_INFO_COLLECT.getValue().getID()) {
            /**
             * 高危
             */
            intent.setClass(mContext, PopulationSupervisionActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationBaseCaseEnum.HIGH_RISK_INFO_COLLECT.getValue().getContent());
        }

        /**
         * 案侦业务
         * 其他工作
         */
        else if (type == InvestigationOtherCaseEnum.RESCUE_ABDUCTED.getValue().getID()) {
            /**
             * 解救被拐人员
             */
            intent.setClass(mContext, InvestigationPersonActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationOtherCaseEnum.RESCUE_ABDUCTED.getValue().getContent());
        } else if (type == InvestigationOtherCaseEnum.STOLEN_CAR.getValue().getID()) {
            /**
             * 查获被盗汽车
             */
            intent.setClass(mContext, InvestigationCarRobberyActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationOtherCaseEnum.STOLEN_CAR.getValue().getContent());
        } else if (type == InvestigationOtherCaseEnum.ONLINE_FUGITIVE.getValue().getID()) {
            /**
             * 抓获网上逃犯
             */
            intent.setClass(mContext, InvestigationArrestActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationOtherCaseEnum.ONLINE_FUGITIVE.getValue().getContent());
        } else if (type == InvestigationOtherCaseEnum.ILLEGAL_SUSPECTS.getValue().getID()) {
            /**
             * 抓获违法犯罪嫌疑
             */
            intent.setClass(mContext, InvestigationArrestActivity.class);
            intent.putExtra(Constant.TITLE, InvestigationOtherCaseEnum.ILLEGAL_SUSPECTS.getValue().getContent());
        }

        /**
         *巡逻
         */
    /*    else if (type == PatrolTypeEnum.ILLEGEAL_CARS.getValue().getID()) {
            *//**
         * 查处违法停车
         *//*
            intent.setClass(mContext, PatrolTrafficActivity.class);
            intent.putExtra(Constant.TITLE, PatrolTypeEnum.ILLEGEAL_CARS.getValue().getContent());
        }*/
        else if (type == PatrolTypeEnum.BLOCKED_SUSPECTS.getValue().getID()) {
            /**
             * 抓获嫌疑人
             */
            intent.setClass(mContext, InvestigationArrestActivity.class);
            intent.putExtra(Constant.TITLE, PatrolTypeEnum.BLOCKED_SUSPECTS.getValue().getContent());
        } else if (type == PatrolTypeEnum.BLOCKED_CARS.getValue().getID()) {
            /**
             * 挡获盗抢车
             */
            intent.setClass(mContext, InvestigationCarRobberyActivity.class);
            intent.putExtra(Constant.TITLE, PatrolTypeEnum.BLOCKED_CARS.getValue().getContent());
        } else if (type == PatrolTypeEnum.BLOCKED_SAND_CARS.getValue().getID()) {
            /**
             * 挡获踩沙石工具
             */
            intent.setClass(mContext, InvestigationSandActivity.class);
            intent.putExtra(Constant.TITLE, PatrolTypeEnum.BLOCKED_SAND_CARS.getValue().getContent());
        } else if (type == PatrolTypeEnum.TRAFFIC_OFFENCE.getValue().getID()) {
            /**
             * 交通违法单
             */
            intent.setClass(mContext, PatrolTrafficActivity.class);
            intent.putExtra(Constant.TITLE, PatrolTypeEnum.TRAFFIC_OFFENCE.getValue().getContent());
        }


        /**
         * 内勤
         */

        else if (type == OfficeTypeEnum.PUBLIC_INFO.getValue().getID()) {
            /**
             * 发表内宣信息
             */
            intent.setClass(mContext, OfficePublishActivity.class);
            intent.putExtra(Constant.TITLE, OfficeTypeEnum.PUBLIC_INFO.getValue().getContent());
        } else if (type == OfficeTypeEnum.PUBLIC_POLITICAL_INFO.getValue().getID()) {
            /**
             * 发表政工信息
             */
            intent.setClass(mContext, OfficePublishActivity.class);
            intent.putExtra(Constant.TITLE, OfficeTypeEnum.PUBLIC_POLITICAL_INFO.getValue().getContent());
        } else if (type == OfficeTypeEnum.PUBLIC_ARTICAL.getValue().getID()) {
            /**
             * 发表调研文章
             */
            intent.setClass(mContext, OfficePublishActivity.class);
            intent.putExtra(Constant.TITLE, OfficeTypeEnum.PUBLIC_ARTICAL.getValue().getContent());
        }


        return intent;


    }
}
