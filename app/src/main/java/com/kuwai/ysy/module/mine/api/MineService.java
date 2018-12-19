package com.kuwai.ysy.module.mine.api;


import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.RResponse;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.BlackListBean;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.GiftExchangeBean;
import com.kuwai.ysy.module.mine.bean.GiftWithdrawalsBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.ShieldAndBlackListBean;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
     *
     * @param type 1:谁看过我，2:是我看过谁
     */
    @FormUrlEncoded
    @POST("My/DelVisitorsRecord")
    Observable<VisitorBean> getDelVisitorsRecord(@Field("uid") String uid,
                                                 @Field("v)id") String vid,
                                                 @Field("type") String type);

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

    /**
     * 信用认证-列表
     */
    @FormUrlEncoded
    @POST("My/MyAuthenticationList")
    Observable<CreditBean> getMyAuthenticationList(@Field("uid") String uid);

    /**
     * 实名认证
     */
    @FormUrlEncoded
    @POST("My/AddRealnameAuthentication")
    Observable<SimpleResponse> getRealnameAuthentication(@Field("uid") String uid,
                                                         @Field("full_name") String name,
                                                         @Field("id_number") String number);

    /**
     * 学历认证
     */
    @FormUrlEncoded
    @POST("My/AddEducationAuthentication")
    Observable<SimpleResponse> getEducationAuthentication(@Field("uid") String uid,
                                                          @Field("full_name") String name,
                                                          @Field("education") String education,
                                                          @Field("school") String school);

    /**
     * 车辆认证
     */
    @FormUrlEncoded
    @POST("My/AddVehicleAuthentication")
    Observable<SimpleResponse> getVehicleAuthentication(@Field("uid") String uid);

    /**
     * 房产认证(不动产认证)
     */
    @FormUrlEncoded
    @POST("My/AddHouseAuthentication")
    Observable<SimpleResponse> getHouseAuthentication(@Field("uid") String uid);

    /**
     * 屏蔽与黑名单-列表
     */
    @FormUrlEncoded
    @POST("My/ShieldAndBlacklistList")
    Observable<ShieldAndBlackListBean> getShieldAndBlackList(@Field("uid") String uid);

    /**
     * 黑名单-列表
     */
    @FormUrlEncoded
    @POST("My/BlacklistList")
    Observable<BlackListBean> getBlackList(@Field("uid") String uid,
                                           @Field("page") int page);

    /**
     * 积分兑换桃花币
     */
    @FormUrlEncoded
    @POST("My/UserExchange")
    Observable<SimpleResponse> getUserExchange(@Field("uid") String uid,
                                               @Field("integral_exchange") int ex);

    /**
     * 礼物单条兑换
     */
    @FormUrlEncoded
    @POST("My/GiftExchangePeachCoin")
    Observable<SimpleResponse> getGiftExchangePeachCoin(@Field("uid") String uid,
                                                        @Field("g_g_id") int gid,
                                                        @Field("type") int type,
                                                        @Field("g_nums") int nums);

    /**
     * 礼物全部提现-列表
     */
    @FormUrlEncoded
    @POST("My/UserWithdrawalsAllList")
    Observable<GiftWithdrawalsBean> getUserWithdrawalsAllList(@Field("uid") String uid);

    /**
     * 礼物多条提现
     */
    @FormUrlEncoded
    @POST("My/GiftExchangePeachCoinAll")
    Observable<SimpleResponse> getGiftExchangePeachCoinAll(@Field("uid") String uid,
                                                           @Field("type_1") int type1,
                                                           @Field("type_2") int type2);

    /**
     * 礼物兑换实物-信息展示
     */
    @FormUrlEncoded
    @POST("My/GiftExchangeEntityInfo")
    Observable<GiftExchangeBean> getGiftExchangeEntityInfo(@Field("uid") String uid,
                                                           @Field("g_g_id") int gid,
                                                           @Field("type") int type,
                                                           @Field("g_nums") int nums);

    /**
     * 礼物兑换实物-信息展示
     */
    @FormUrlEncoded
    @POST("My/GiftExchangeEntity")
    Observable<SimpleResponse> getGiftExchangeEntity(@FieldMap HashMap<String, String> infos);


    /**
     * 我的钱包
     */
    @FormUrlEncoded
    @POST("My/MyWallet")
    Observable<MyWalletBean> getMyWallet(@Field("uid") String uid);

    /**
     * 钱包明细
     */
    @FormUrlEncoded
    @POST("My/WalletList")
    Observable<MyWalletBean> getWalletList(@Field("uid") String uid,
                                           @Field("page") int page);


    /**
     * 会员列表
     */
    @GET("My/AndroidFirstClassMember")
    Observable<VipBean> getVipList();
    /**
     * zfb订单获取
     */
    @GET("Payment/pagePay")
    Observable<AliOrderInfoBean> getAliOrderInfo();

}
