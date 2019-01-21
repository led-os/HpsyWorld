package com.kuwai.ysy.module.chat.api;


import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.chat.bean.NoticeDateBean;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.bean.RedMySendBean;
import com.kuwai.ysy.module.chat.bean.RedRecordBean;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.chat.bean.UserInfoBean;
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
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.rong.bean.RedBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatService {

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<ResponseBody> login(@FieldMap Map<String, String> params);

    //好友列表
    @FormUrlEncoded
    @POST("Chat/MyFriendList")
    Observable<MyFriends> getFriends(@FieldMap  Map<String,String> map);

    //搜索用户添加好友
    @FormUrlEncoded
    @POST("Chat/SearchFriendList")
    Observable<MyFriends> searchFriends(@Field("search_criteria") String search_criteria,@Field("page") int page);

    //好友申请列表
    @FormUrlEncoded
    @POST("Chat/FriendsApplicationList")
    Observable<MyFriends> getNewFriends(@Field("uid") String uid);

    //好友申请列表
    @FormUrlEncoded
    @POST("Currency/UserInformation")
    Observable<UserInfoBean> getUserInfo(@Field("uid") String uid,@Field("loginguserid") String loginguserid);

    //同意拒绝申请
    @FormUrlEncoded
    @POST("Chat/HandleFiend")
    Observable<SimpleResponse> agreeNewFriends(@Field("uid") String uid,@Field("other_uid") String other_id,@Field("status") int status);

    //申请加好友
    @FormUrlEncoded
    @POST("Chat/addfriend")
    Observable<SimpleResponse> addFriends(@Field("uid") String uid,@Field("other_uid") String other_id);

    //寻找好友列表-推荐
    @FormUrlEncoded
    @POST("Chat/RecommendFriendList")
    Observable<MyFriends> getTuiFriends(@Field("uid") String uid,@Field("gender") String gender,@Field("page") int page);

    //寻找好友列表-附近
    @FormUrlEncoded
    @POST("Chat/NearbydFriendList")
    Observable<MyFriends> getNearFriends(@Field("uid") String uid,@Field("page") int page,@Field("longitude") String longitude,@Field("latitude") String latitude);

    //设置备注
    @FormUrlEncoded
    @POST("Chat/Remarks")
    Observable<SimpleResponse> setRemark(@Field("uid") String uid,@Field("other_id") String other_id,@Field("other_nickname") String other_nickname);

    //加入黑名单
    @FormUrlEncoded
    @POST("Chat/Blacklist")
    Observable<SimpleResponse> setBlack(@Field("uid") String uid,@Field("other_id") String other_id);

    //取消黑名单
    @FormUrlEncoded
    @POST("Chat/CancelBlacklist")
    Observable<SimpleResponse> cancelBlack(@Field("uid") String uid,@Field("other_uid") String other_id);

    //取消屏蔽
    @FormUrlEncoded
    @POST("Currency/UpdateShield")
    Observable<SimpleResponse> cancelPing(@Field("uid") String uid,@Field("s_id") String other_id);

    //发红包
    @FormUrlEncoded
    @POST("Chat/HandOutRedEnvelopes")
    Observable<RedBean> sendRed(@FieldMap  Map<String,Object> map);

    //收红包
    @FormUrlEncoded
    @POST("Chat/CollectRedPackets")
    Observable<SimpleResponse> receiveRed(@Field("uid") String uid,@Field("r_id") String r_id);

    //红包详情-接收方看到
    @FormUrlEncoded
    @POST("Chat/OthersRedEnvelopesDetails")
    Observable<ReceiveBean> redDetailOther(@Field("other_uid") String other_uid, @Field("r_id") String r_id);

    //红包详情-自己看到
    @FormUrlEncoded
    @POST("Chat/OwnRedEnvelopesDetails")
    Observable<RedMySendBean> redDetailMine(@Field("uid") String uid, @Field("r_id") String r_id);

    //发出/收到-红包列表
    @FormUrlEncoded
    @POST("Chat/HandOutRedEnvelopesList")
    Observable<RedRecordBean> redList(@Field("uid") String uid, @Field("type") String type, @Field("page") int page);

    //STS
    @GET("Upload/vodPlayRole")
    Observable<StsBean> getSts();

    //系统通知列表
    @FormUrlEncoded
    @POST("Chat/NotificationList")
    Observable<NoticeBean> getSystemNotice(@Field("uid") String uid, @Field("page") int page);

    //约会通知列表
    @FormUrlEncoded
    @POST("Chat/ReleaseDataNoticeList")
    Observable<NoticeDateBean> getDateNotice(@Field("uid") String uid);

    //打赏人
    @FormUrlEncoded
    @POST("Chat/Giving")
    Observable<SimpleResponse> rewardPe(@FieldMap Map<String,Object> map);

}
