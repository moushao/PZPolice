package com.pvirtech.pzpolice.http;


import com.pvirtech.pzpolice.entity.AppUpdateEntity;
import com.pvirtech.pzpolice.entity.BusinessWorkEntity;
import com.pvirtech.pzpolice.entity.CaseInfoEntity;
import com.pvirtech.pzpolice.entity.DeclareEntity;
import com.pvirtech.pzpolice.entity.DetailTrackEntity;
import com.pvirtech.pzpolice.entity.GroupEntity;
import com.pvirtech.pzpolice.entity.GroupcountEntity;
import com.pvirtech.pzpolice.entity.LeaveEntity;
import com.pvirtech.pzpolice.entity.LeaveRecordEntity;
import com.pvirtech.pzpolice.entity.MyAssessmentListEntity;
import com.pvirtech.pzpolice.entity.MyInitiatedEntity;
import com.pvirtech.pzpolice.entity.MyTasksEntity;
import com.pvirtech.pzpolice.entity.PersonalFileBasicFragmentEntity;
import com.pvirtech.pzpolice.entity.PersonalFileEducationFragmentEntity;
import com.pvirtech.pzpolice.entity.PersonalFileFamilyFragmentEntity;
import com.pvirtech.pzpolice.entity.PersonalFileWorkFragmentEntity;
import com.pvirtech.pzpolice.entity.ScoreEntity;
import com.pvirtech.pzpolice.entity.ScoreTeamEntity;
import com.pvirtech.pzpolice.entity.SubtaskEntity;
import com.pvirtech.pzpolice.entity.TaskAndIntegration;
import com.pvirtech.pzpolice.entity.TaskPreviewEntity;
import com.pvirtech.pzpolice.entity.TaskTrajectory;
import com.pvirtech.pzpolice.entity.TeamAssessmentRecordEntity;
import com.pvirtech.pzpolice.entity.TeamAssessmentReasonEntity;
import com.pvirtech.pzpolice.entity.TeamBuildingEntity;
import com.pvirtech.pzpolice.entity.UserInfo;
import com.pvirtech.pzpolice.entity.UserListEntity;
import com.pvirtech.pzpolice.entity.VacationDetailsEntity;
import com.pvirtech.pzpolice.entity.WokFragmentNotifyEntitiy;
import com.pvirtech.pzpolice.entity.WorkDay;
import com.pvirtech.pzpolice.entity.WorkLogEntity;
import com.pvirtech.pzpolice.test.one.CaseQueryEntity;
import com.tonypy.tonypy.addressbooks.entity.Group;

import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.DictionariesEntity;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface HttpApi {

    /**
     * See https://developer.github.com/v3/users/
     */

    @POST("getQbcf.do")
    Observable<HttpResult<List<CaseQueryEntity>>> CaseQueryData(@QueryMap Map map);


    /**
     * 登录
     */
    @POST("applogin/login")
    Observable<HttpResult<UserInfo>> login(@Body HttpSubmit hs);
    //    @GET("article")
    //    Observable<HttpResult<UserInfo>> login(@Query("op") String op );

    /**
     * 请求用户列表
     */
    @POST("appuserinfo/getAppUserList")
    Observable<HttpResult<List<UserListEntity>>> getAppUserList(@Body HttpSubmit hs);

    /**
     * 时间申报
     */
    @POST("appworksheet/declare")
    Observable<HttpResult> declare(@Body HttpSubmit hs);

    /**
     * 假期理由
     */
    @POST("appholiday/reason")
    Observable<HttpResult<List<DictionariesEntity>>> appholidayReason(@Body HttpSubmit hs);

    /**
     * 假期安排
     */
    @POST("appholiday/plan")
    Observable<HttpResult> appholidayPlan(@Body HttpSubmit hs);

    /**
     * 我的剩余假期
     */
    @POST("appholiday/day")
    Observable<HttpResult<List<LeaveEntity>>> appholidaDay(@Body HttpSubmit hs);

    /**
     * 请假接口
     */
    @POST("appholiday/apply")
    Observable<HttpResult> appholidayApply(@Body HttpSubmit hs);


    /**
     * 我的请假记录
     */
    @POST("appholiday/myapply")
    Observable<HttpResult<List<LeaveRecordEntity>>> appholidayMyapply(@Body HttpSubmit hs);

    /**
     * 销假
     */
    @POST("appholiday/backholy")
    Observable<HttpResult> appholidayBackholy(@Body HttpSubmit hs);

    /**
     * 排行榜
     */
    @POST("apprank/list")
    Observable<HttpResult<List<ScoreEntity>>> apprankList(@Body HttpSubmit hs);


    /**
     * 排行榜, 队伍
     */
    @POST("apprank/grouprank")
    Observable<HttpResult<List<ScoreTeamEntity>>> apprankGrouprank(@Body HttpSubmit hs);

    /**
     * 取得这个月的考勤情况
     */
    @POST("appworksheet/mtime")
    Observable<HttpResult<List<WorkDay>>> appworksheetMtime(@Body HttpSubmit hs);


    /**
     * 取得某一天的考勤情况
     */
    @POST("appworksheet/dtime")
    Observable<HttpResult<DeclareEntity>> appworksheetDtime(@Body HttpSubmit hs);


    /**
     * 代办事项
     */
    @POST("appschedule/alist")
    Observable<HttpResult<List<TeamBuildingEntity>>> appscheduleAlist(@Body HttpSubmit hs);

    /**
     * 代办事项 /appschedule/halist  我审批的
     */
    @POST("appschedule/halist")
    Observable<HttpResult<List<TeamBuildingEntity>>> appscheduleHalist(@Body HttpSubmit hs);

    /**
     * 代办事项===========我发起的
     */
    @POST("appschedule/mylist")
    Observable<HttpResult<List<MyInitiatedEntity>>> appscheduleMylist(@Body HttpSubmit hs);


    /**
     * 代办事项  ,查看事项的详细信息 和轨迹并处理
     */
    @POST("appschedule/detail")
    Observable<HttpResult<DetailTrackEntity>> appscheduleDetail(@Body HttpSubmit hs);

    /**
     * 审核是否通过
     */
    @POST("appschedule/approal")
    Observable<HttpResult> appscheduleApproal(@Body HttpSubmit hs);


    /**
     * 销假的轨迹数据
     */
    @POST("appholiday/process")
    Observable<HttpResult<List<VacationDetailsEntity>>> appholidayProcess(@Body HttpSubmit hs);


    /**
     * 按部门返回用户
     */
    @POST("appgroup/info")
    Observable<HttpResult<List<Group>>> appgroupInfo(@Body HttpSubmit hs);

    /**
     * 队伍建设考核  加分或者减分
     */
    @POST("apptbuild/tba")
    Observable<HttpResult> apptbuildTba(@Body HttpSubmit hs);


    /**
     * 队伍建设考核       考核理由
     */
    @POST("apptbuild/ru")
    Observable<HttpResult<List<TeamAssessmentReasonEntity>>> apptbuildRu(@Body HttpSubmit hs);


    /**
     * 业务考核，案件办理  提交数据
     */
    @POST("appbassess/submitADM")
    Observable<HttpResult> appbassessSubmitADM(@Body HttpSubmit hs);


    /**
     * 业务考核基础工作 提交数据
     */
    @POST("appbassess/basework")
    Observable<HttpResult> appbassessBasework(@Body HttpSubmit hs);

    /**
     * 业务考核内勤 提交数据
     */
    @POST("appbassess/insidejob")
    Observable<HttpResult> appbassessInsidejob(@Body HttpSubmit hs);


    /**
     * 查看物业工作的任务
     */
    @POST("appschedule/blist")
    Observable<HttpResult<List<BusinessWorkEntity>>> appscheduleBlist(@Body HttpSubmit hs);

    /**
     * 领导查看已审批的任务
     */
    @POST("appschedule/hblist")
    Observable<HttpResult<List<BusinessWorkEntity>>> appscheduleHblist(@Body HttpSubmit hs);

    /**
     * 查看任务的子任务
     */
    @POST("apptask/taskcase")
    Observable<HttpResult<List<SubtaskEntity>>> apptaskTaskcase(@Body HttpSubmit hs);

    /**
     * 个人信息- 基本情况
     */
    @POST("apppolice/getUserById")
    Observable<HttpResult<PersonalFileBasicFragmentEntity>> apppersonalBasicFragment(@Body HttpSubmit hs);

    /**
     * 个人信息- 工作情况
     */
    @POST("apppolice/getUserById")
    Observable<HttpResult<PersonalFileWorkFragmentEntity>> apppersonalFileWorkFragment(@Body HttpSubmit hs);

    /**
     * 个人信息- 教育
     */
    @POST("apppolice/getUserById")
    Observable<HttpResult<PersonalFileEducationFragmentEntity>> apppersonalFileEducationFragment(@Body HttpSubmit hs);

    /**
     * 个人信息- 家庭
     */
    @POST("apppolice/getUserById")
    Observable<HttpResult<List<PersonalFileFamilyFragmentEntity>>> apppersonalFileFamilyFragment(@Body HttpSubmit hs);

    /**
     * 我的任务 - 日常任务
     */
    @POST("apptask/getTaskByInfo")
    Observable<HttpResult<List<MyTasksEntity>>> apptaskGetTaskByInfo(@Body HttpSubmit hs);


    /**
     * 业务工作的任务审核
     */
    @POST("apptask/taskapproal")
    Observable<HttpResult> apptaskTaskapproal(@Body HttpSubmit hs);

    /**
     * 查看任务的子任务的详细信息
     */
    @POST("appbassess/caseInfo")
    Observable<HttpResult<CaseInfoEntity>> appbassessCaseInfoNormalCase(@Body HttpSubmit hs);


    /**
     * 子任务的轨迹
     */
    @POST("apptask/taskTrail")
    Observable<HttpResult<List<TaskTrajectory>>> apptaskTaskTrail(@Body HttpSubmit hs);


    /**
     * 任务发布接口
     */
    @POST("apptask/pbtask")
    Observable<HttpResult> apptaskPbtask(@Body HttpSubmit hs);

    /**
     * 查询任务的类型和分值分配方式
     */
    @POST("apptask/taskTypeList")
    Observable<HttpResult<TaskAndIntegration>> apptaskTaskTypeList(@Body HttpSubmit hs);


    /**
     * 获取所有的任务类型
     */
    @POST("apptask/taskCaseType")
    Observable<HttpResult> apptaskTaskCaseType(@Body HttpSubmit hs);


    /**
     * 查询分值分配方式
     */
    @POST("apptask/scoreway")
    Observable<HttpResult<List<TaskAndIntegration.SCOREWAYLISTBean>>> apptaskScoreway(@Body HttpSubmit hs);

    /**
     * 软件更新
     */
    @POST("appversion/getFileName")
    Observable<HttpResult<AppUpdateEntity>> getVersionCode(@Body HttpSubmit hs);


    /**
     * 查询分值分配方式
     */
    @POST("appgroup/all")
    Observable<HttpResult<List<GroupEntity>>> appgroupAll(@Body HttpSubmit hs);

    /**
     * 第一页任务查询 ，统计任务个数
     */
    @POST("apptask/index")
    Observable<HttpResult<TaskPreviewEntity>> apptaskIndex(@Body HttpSubmit hs);

    /**
     * 取的即将到期任务
     */
    @POST("apptask/dateDeadTask")
    Observable<HttpResult<List<MyTasksEntity>>> apptaskDateDeadTask(@Body HttpSubmit hs);

    /**
     * 高权限的人预览任务
     */
    @POST("apptask/groupcount")
    Observable<HttpResult<List<GroupcountEntity>>> apptaskGroupcount(@Body HttpSubmit hs);

    /**
     * 保存日志
     */
    @POST("appWorkLog/saveWorkLog")
    Observable<HttpResult> appWorkLogSaveWorkLog(@Body HttpSubmit hs);

    /**
     * 根据日期差日志
     */
    @POST("appWorkLog/getWorkLogByDate")
    Observable<HttpResult<List<WorkLogEntity>>> appWorkLogGetWorkLogByDate(@Body HttpSubmit hs);


    /**
     * 更新保存日志
     */
    @POST("appWorkLog/updateWorkLog")
    Observable<HttpResult> appWorkLogUpdateWorkLog(@Body HttpSubmit hs);


    /**
     * 上传文件
     */
    @Multipart
    @POST("appfileUpload/save")
    Observable<ResponseBody> upload(@Part MultipartBody.Part file);

    /**
     * 查询某人的积分信息
     */
    @POST("apprank/myscorelist")
    Observable<HttpResult<List<MyAssessmentListEntity>>> apprankMyscorelist(@Body HttpSubmit hs);

    /**
     * 查询自己的积分信息
     */
    @POST("apprank/myscore")
    Observable<HttpResult> apprankMyscore(@Body HttpSubmit hs);

    /**
     * 根据用户的id来查询个人详情
     */
    @POST("apppolice/userinfo")
    Observable<HttpResult<UserInfo>> apppoliceUserinfo(@Body HttpSubmit hs);

    /**
     * 驳回假期的理由
     */
    @POST("appholiday/tdreson")
    Observable<HttpResult<List<DictionariesEntity>>> appholidayTdreson(@Body HttpSubmit hs);


    /**
     * 驳回假期的理由
     */
    @POST("appcommon/num")
    Observable<HttpResult<WokFragmentNotifyEntitiy>> appcommonNum(@Body HttpSubmit hs);


    /**
     * 领导队伍建设考核提交记录查询
     */
    @POST("apptbuild/list")
    Observable<HttpResult<List<TeamAssessmentRecordEntity>>> apptbuildList(@Body HttpSubmit hs);

}