package com.kuwai.ysy.module.mine.api;


import com.kuwai.ysy.bean.RResponse;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MineService {

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<ResponseBody> login(@FieldMap Map<String, String> params);

    //个人主页-列表
    @FormUrlEncoded
    @POST("My/PersonalList")
    Observable<UserInfo> getUsetInfo(@Field("uid") String uid);

    //个人主页-资料-本人查看
    @FormUrlEncoded
    @POST("My/PersonalInfo")
    Observable<PersolHomePageBean> getUsetInfoMine(@Field("uid") String uid);

    /**
     * 个人主页-资料-别人查看
     */
    @FormUrlEncoded
    @POST("My/OtherPersonalInfo")
    Observable<PersolHomePageBean> getOtherPersonalInfo(@Field("uid") String uid,
                                                        @Field("other_uid") String otherid);

    /**
     * 我收到的礼物列表
     */
    @FormUrlEncoded
    @POST("My/UserReceiveGiftsnAllList")
    Observable<GiftAcceptBean> getGiftMyAccept(@Field("uid") String uid,
                                               @Field("page") int page);

    /**
     * 我发送的礼物列表
     */
    @FormUrlEncoded
    @POST("My/UserGiveGiftsnAllList")
    Observable<GiftAcceptBean> getGiftMySend(@Field("uid") String uid,
                                             @Field("page") int page);

    /**
     * 访客记录-谁看过我/我看过谁-所有
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/VisitorsRecordSeenMe")
    Observable<VisitorBean> getVisitors(@Field("uid") String uid,
                                        @Field("type") int type);

    /**
     * 访客记录-谁看过我/我看过谁-更早
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/VisitorsRecordSeenMe")
    Observable<VisitorBean> getVisitorsEarlier(@Field("uid") String uid,
                                               @Field("type") int type,
                                               @Field("page") int page);

    /**
     * 我喜欢的
     */
    @FormUrlEncoded
    @POST("My/UserLoveMe")
    Observable<VisitorBean> getIlike(@Field("uid") String uid);

    /**
     * 喜欢我的
     */
    @FormUrlEncoded
    @POST("My/UserILikeIt")
    Observable<VisitorBean> getLikeMe(@Field("uid") String uid);

    /**
     * 互相喜欢的（好友）
     */
    @FormUrlEncoded
    @POST("My/UserLikeEachOther")
    Observable<TodayBean> getLikeEach(@Field("uid") String uid, @Field("uid") int page);

    /**
     * Ta 收到的礼物
     */
    @FormUrlEncoded
    @POST("My/OtherGiftList")
    Observable<TaGiftBean> getTaGift(@Field("uid") String uid,
                                     @Field("page") int page);

    /**
     * 礼物箱
     */
    @FormUrlEncoded
    @POST("My/GiftBox")
    Observable<GiftBoxBean> getGiftBox(@Field("uid") String uid);

    /**
     * 我的想问-列表
     */
    @FormUrlEncoded
    @POST("My/ProblemList")
    Observable<MyAskBean> getAskList(@Field("uid") String uid);

    /**
     * 删除问题
     */
    @FormUrlEncoded
    @POST("My/DelProblem")
    Observable<SimpleResponse> getDelAsk(@Field("uid") String uid, @Field("p_id") int pid);

    /**
     * 添加问题
     */
    @FormUrlEncoded
    @POST("My/AddProblem")
    Observable<SimpleResponse> getAddAsk(@Field("uid") String uid, @Field("problem") String problem);

    /**
     * 修改 问题/回答/添加回答
     */
    @FormUrlEncoded
    @POST("My/UpdateProblem")
    Observable<SimpleResponse> getUpdateProblem(@Field("uid") String uid,
                                                @Field("p_id") int pid,
                                                @Field("problem") String pro,
                                                @Field("answer") String answer);

    /**
     * 用户积分明细-列表
     */
    @FormUrlEncoded
    @POST("My/UserIntegralDetails")
    Observable<IntegralDetailBean> getUserIntegralDetails(@Field("uid") String uid,
                                                          @Field("page") int page);




}
