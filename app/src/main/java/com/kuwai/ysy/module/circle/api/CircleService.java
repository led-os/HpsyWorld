package com.kuwai.ysy.module.circle.api;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.DyDetailBean;
import com.kuwai.ysy.module.circle.bean.DyLikeListBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.circle.bean.HoleCommentListBean;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CircleService {

    /**
     * 圈子分类
     */
    @GET("v4/categories")
    Observable<ArrayList<CategoryBean>> getCircleData();

    /**
     * 动态列表
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicList")
    Observable<DyMainListBean> getDyMainListData(@Field("page") int page, @Field("uid") String uid);

    /**
     * 好友动态列表
     */
    @FormUrlEncoded
    @POST("Dynamic/MyFriendsDynamicList")
    Observable<DyMainListBean> getDyMyFriendListData(@Field("page") int page, @Field("uid") String uid);

    /**
     * 动态详情
     * d_id  动态id
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicDetails")
    Observable<DyDetailBean> getDyDetailData(@Field("d_id") String d_id, @Field("uid") String uid);

    /**
     * 发布动态
     */
    @FormUrlEncoded
    @POST("Dynamic/ReleaseDynamic")
    Observable<SimpleResponse> publishDy(@FieldMap Map<String, String> map);

    /**
     * 删除动态
     */
    @FormUrlEncoded
    @POST("Dynamic/DelDynamic")
    Observable<SimpleResponse> deleteDy(@Field("d_id") String d_id, @Field("uid") String uid);

    /**
     * 动态点赞列表
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicGoodList")
    Observable<DyLikeListBean> getDyLikeListData(@Field("d_id") String d_id, @Field("uid") String uid, @Field("page") int page);

    /**
     * 动态打赏列表
     */
    @FormUrlEncoded
    @POST("Dynamic/getDynamicRewardList")
    Observable<DyRewardlistBean> getDyRewardListData(@Field("d_id") String t_id, @Field("page") int page);

    /**
     * 动态评论列表
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicCommentList")
    Observable<DyCommentListBean> getDyCommentListData(@Field("d_id") String d_id, @Field("uid") String uid, @Field("page") int page);

    /**
     * 动态点赞
     * status 1点赞 2取消点赞
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicGood")
    Observable<SimpleResponse> dyLikeOrNot(@Field("d_id") String d_id, @Field("uid") String uid, @Field("other_uid") String other_uid, @Field("status") int status);

    /**
     * 动态评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicComment")
    Observable<SimpleResponse> dyComment(@Field("d_id") String d_id, @Field("uid") String uid, @Field("text") String text);

    /**
     * 动态二级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicCommentSub")
    Observable<SimpleResponse> dySecComment(@Field("d_clid") String d_clid, @Field("uid") String uid, @Field("text") String text, @Field("other_uid") int other_uid);

    /**
     * 删除一级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DelDynamicComment")
    Observable<SimpleResponse> deleteDyComment(@Field("d_c_id") String d_c_id, @Field("uid") String uid);

    /**
     * 删除二级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DelDynamicCommentSub")
    Observable<SimpleResponse> deleteDySecComment(@Field("d_clid") String d_clid, @Field("uid") String uid);

    /**
     * 动态评论点赞
     * status 1点赞 2取消点赞
     */
    @FormUrlEncoded
    @POST("Dynamic/DynamicCommentGood")
    Observable<SimpleResponse> dyLikeOrNot(@Field("d_id") String d_id, @Field("uid") String uid, @Field("status") int status, @Field("d_clid") int d_clid, @Field("other_uid") int other_uid);

    /**
     * 发布树洞
     */
    @FormUrlEncoded
    @POST("Dynamic/TreeHole")
    Observable<SimpleResponse> publishHole(@FieldMap Map<String, String> map);

    /**
     * 树洞列表
     */
    @FormUrlEncoded
    @POST("Dynamic/TreeHoleList")
    Observable<HoleMainListBean> getHoleMainListData(@Field("page") int page, @Field("uid") String uid);

    /**
     * 树洞详情
     * d_id  动态id
     */
    @FormUrlEncoded
    @POST("Dynamic/TreeHoleDetails")
    Observable<HoleDetailBean> getHoleDetailData(@Field("t_id") String t_id, @Field("uid") String uid);

    /**
     * 开启关闭匿名聊天
     * anonymous_chat 0关闭  1打开
     */
    @FormUrlEncoded
    @POST("Dynamic/UpdateAnonymousChat")
    Observable<SimpleResponse> openAnonyChat(@Field("t_id") String t_id, @Field("uid") String uid, @Field("anonymous_chat") int anonymous_chat);

    /**
     * 开启关闭评论
     * open_comment 0关闭  1打开
     */
    @FormUrlEncoded
    @POST("Dynamic/UpdateOpenComment")
    Observable<SimpleResponse> openComment(@Field("t_id") String t_id, @Field("uid") String uid, @Field("open_comment") int open_comment);

    /**
     * 删除树洞
     */
    @FormUrlEncoded
    @POST("Dynamic/DelTreeHole")
    Observable<SimpleResponse> deleteHole(@Field("t_id") String t_id, @Field("uid") String uid);

    /**
     * 动态打赏列表
     */
    @FormUrlEncoded
    @POST("Dynamic/getTreeHoleRewardList")
    Observable<DyRewardlistBean> getDyHoleListData(@Field("t_id") String t_id, @Field("page") int page);

    /**
     * 删除树洞一级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DelTreeHoleComment")
    Observable<SimpleResponse> deleteHoleComment(@Field("t_clid") String t_clid, @Field("uid") String uid);

    /**
     * 删除树洞二级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/DelTreeHoleCommentSub")
    Observable<SimpleResponse> deleteHoleSecComment(@Field("d_c_sid") String d_c_sid, @Field("uid") String uid);

    /**
     * 树洞评论
     */
    @FormUrlEncoded
    @POST("Dynamic/InsertTreeHoleComment")
    Observable<SimpleResponse> holeComment(@Field("t_id") String t_id, @Field("uid") String uid, @Field("text") String text);

    /**
     * 树洞二级评论
     */
    @FormUrlEncoded
    @POST("Dynamic/InsertTreeHoleCommentSub")
    Observable<SimpleResponse> holeSecComment(@Field("t_clid") String t_clid, @Field("other_uid") String other_uid, @Field("uid") String uid, @Field("text") String text);

    /**
     * 树洞评论列表
     */
    @FormUrlEncoded
    @POST("Dynamic/TreeHoleCommentList")
    Observable<HoleCommentListBean> getHoleCommentListData(@Field("t_id") String t_id, @Field("uid") String uid, @Field("page") int page);

    /**
     * 树洞评论列表点赞
     * status 1点赞 2取消点赞
     */
    @FormUrlEncoded
    @POST("Dynamic/TreeHoleCommentGood")
    Observable<SimpleResponse> holeLikeOrNot(@FieldMap Map<String, String> map);
}