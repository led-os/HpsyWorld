package com.kuwai.ysy.module.mine.api;

import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.find.api.AppointService;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.mine.bean.BlackListBean;
import com.kuwai.ysy.module.mine.bean.ChangeHeadBean;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.ESUser;
import com.kuwai.ysy.module.mine.bean.EarlierBean;
import com.kuwai.ysy.module.mine.bean.FootCity;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.GiftWithdrawalsBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.bean.JobBean;
import com.kuwai.ysy.module.mine.bean.LikeEach;
import com.kuwai.ysy.module.mine.bean.LikeParent;
import com.kuwai.ysy.module.mine.bean.LikeParentBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.kuwai.ysy.module.mine.bean.NoticeSetBean;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.VisitorMore;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.module.mine.bean.place.LatestPlace;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.net.ApiClient;
import com.kuwai.ysy.utils.HuPuHelper;
import com.kuwai.ysy.utils.security.MD5;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;


public class MineApiFactory {

    public static Observable<UserInfo> getUserInfo(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUsetInfo(uid)
                .compose(RxSchedulers.<UserInfo>ioMain());
    }

    public static Observable<SimpleResponse> setReminder(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getReminder(uid, otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> setCancelReminder(String uid, String otherId) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getCancelReminder(uid, otherId)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<JobBean> getAllJob() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getAllJob()
                .compose(RxSchedulers.<JobBean>ioMain());
    }

    public static Observable<PersolHomePageBean> getOtherHomepageInfo(String uid, String otherid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getOtherPersonalInfo(uid, otherid)
                .compose(RxSchedulers.<PersolHomePageBean>ioMain());
    }

    public static Observable<PersolHome2PageBean> getOtherHomepage2Info(String uid, String otherid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getOtherPersonal2Info(uid, otherid)
                .compose(RxSchedulers.<PersolHome2PageBean>ioMain());
    }

    public static Observable<SimpleResponse> delVisitorsRecord(String uid, int otherid, String type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getDelVisitorsRecord(uid, otherid, type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<PersolHomePageBean> getUsetInfoMine(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUsetInfoMine(uid)
                .compose(RxSchedulers.<PersolHomePageBean>ioMain());
    }

    public static Observable<WallBean> getWall(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getWall(uid)
                .compose(RxSchedulers.<WallBean>ioMain());
    }

    public static Observable<DyMainListBean> getPersonalDynamic(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getPersonalDynamic(uid, page)
                .compose(RxSchedulers.<DyMainListBean>ioMain());
    }

    public static Observable<PersonalTreeHole> getPersonalTreeHole(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getPersonalTreeHole(uid, page)
                .compose(RxSchedulers.<PersonalTreeHole>ioMain());
    }

    public static Observable<GiftWithdrawalsBean> getUserWithdrawalsAllList(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserWithdrawalsAllList(uid)
                .compose(RxSchedulers.<GiftWithdrawalsBean>ioMain());
    }

    public static Observable<GiftAcceptBean> getGiftAccept(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftMyAccept(uid, page)
                .compose(RxSchedulers.<GiftAcceptBean>ioMain());
    }

    public static Observable<SimpleResponse> getUserLike(String uid, String otherid, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserLike(uid, otherid, type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getGiftExchangePeachCoinAll(String uid, String type1, String type2) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftExchangePeachCoinAll(uid, type1, type2)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> updateUserSig(String uid, String newSign) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .updateUserSig(uid, newSign)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<GiftAcceptBean> getGiftSend(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftMySend(uid, page)
                .compose(RxSchedulers.<GiftAcceptBean>ioMain());
    }

    public static Observable<VisitorBean> getVisitor(String uid, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVisitors(uid, type)
                .compose(RxSchedulers.<VisitorBean>ioMain());
    }

    public static Observable<VisitorMore> getVisitorEarlier(String uid, int type, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVisitorsEarlier(uid, type, page)
                .compose(RxSchedulers.<VisitorMore>ioMain());
    }

    public static Observable<TaGiftBean> getTaGift(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getTaGift(uid, page)
                .compose(RxSchedulers.<TaGiftBean>ioMain());
    }

    public static Observable<GiftBoxBean> getGiftBox(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getGiftBox(uid)
                .compose(RxSchedulers.<GiftBoxBean>ioMain());
    }

    public static Observable<MyAskBean> getAskList(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getAskList(uid)
                .compose(RxSchedulers.<MyAskBean>ioMain());
    }

    public static Observable<SimpleResponse> getDelAsk(String uid, int pid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getDelAsk(uid, pid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> inviteToUpdate(String uid, String pid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .inviteToUpdate(uid, pid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getUpdateProblem(String uid, int pid, String pro, String answer) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUpdateProblem(uid, pid, pro, answer)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getAddAsk(String uid, String pro) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getAddAsk(uid, pro)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<LikeParentBean> getLikeMe(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getLikeMe(uid)
                .compose(RxSchedulers.<LikeParentBean>ioMain());
    }

    public static Observable<LikeParentBean> getILike(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getIlike(uid)
                .compose(RxSchedulers.<LikeParentBean>ioMain());
    }

    public static Observable<EarlierBean> getIloveEarlier(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getIloveEarlier(uid, page)
                .compose(RxSchedulers.<EarlierBean>ioMain());
    }

    public static Observable<LikeEach> getLikeEachOther(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getLikeEach(uid, page)
                .compose(RxSchedulers.<LikeEach>ioMain());
    }

    public static Observable<IntegralDetailBean> getUserIntegralDetails(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserIntegralDetails(uid, page)
                .compose(RxSchedulers.<IntegralDetailBean>ioMain());
    }

    public static Observable<CreditBean> getMyAuthenticationList(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getMyAuthenticationList(uid)
                .compose(RxSchedulers.<CreditBean>ioMain());
    }

    public static Observable<VipBean> getVipList() {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVipList()
                .compose(RxSchedulers.<VipBean>ioMain());
    }

    public static Observable<AliOrderInfoBean> getAliOrderInfo(String uid, String money) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getAliOrderInfo(uid, money)
                .compose(RxSchedulers.<AliOrderInfoBean>ioMain());
    }

    public static Observable<MyWalletBean> getWallet(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getMyWallet(uid)
                .compose(RxSchedulers.<MyWalletBean>ioMain());
    }

    public static Observable<WalletDetailsBean> getWalletDetails(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getWalletList(uid, page)
                .compose(RxSchedulers.<WalletDetailsBean>ioMain());
    }

    public static Observable<SimpleResponse> walletWithdrawals(String uid, String money, String pid, String pname) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .walletWithdrawals(uid, money, pid, pname)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getRealnameAuthentication(Map<String, RequestBody> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getRealnameAuthentication(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getEducationAuthentication(Map<String, RequestBody> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getEducationAuthentication(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getHouseAuthentication(Map<String, RequestBody> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getHouseAuthentication(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getVehicleAuthentication(Map<String, RequestBody> map) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getVehicleAuthentication(map)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> updatePasswordOrParmentPassword(String uid, String psd, String newPsd, String reNewPsd, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .updatePasswordOrParmentPassword(uid, psd, newPsd, reNewPsd, type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> updateUserPhone(String uid, String phone, String newPhone, String newVcode) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .updateUserPhone(uid, phone, newPhone, newVcode)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getAddParmentPassword(String uid, String psd, String pay_psd, String re_pay_psd) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getAddParmentPassword(uid, psd, pay_psd, re_pay_psd)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<CheckInBean> getUserIntegralCheckInList(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserIntegralCheckInList(uid)
                .compose(RxSchedulers.<CheckInBean>ioMain());
    }

    public static Observable<SimpleResponse> getUserIntegralCheckIn(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserIntegralCheckIn(uid)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> getUserExchange(String uid, int exchange) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserExchange(uid, exchange)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<LatestPlace> getLatestPlace(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getLatestPlace(uid, page)
                .compose(RxSchedulers.<LatestPlace>ioMain());
    }

    public static Observable<SimpleResponse> deleteFoot(String uid, String fid,int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .deleteFoot(uid, fid,type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<NoticeSetBean> noticiSet(String uid) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .noticeSet(uid)
                .compose(RxSchedulers.<NoticeSetBean>ioMain());
    }

    public static Observable<SimpleResponse> noticeChange(String uid, int type, String set) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .noticeChange(uid, type, set)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> changeInfo(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .changeInfo(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> change2Info(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .change2Info(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<BlackListBean> getBlackList(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getBlackList(uid, page)
                .compose(RxSchedulers.<BlackListBean>ioMain());
    }

    public static Observable<FootCity> getFootCity(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getFootCity(uid, page)
                .compose(RxSchedulers.<FootCity>ioMain());
    }

    public static Observable<BlackListBean> getPingList(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getPingList(uid, page)
                .compose(RxSchedulers.<BlackListBean>ioMain());
    }

    public static Observable<SimpleResponse> ping(String uid, int t_id, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .ping(uid, t_id, type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<EarlierBean> getUserLikemeItEarlier(String uid, int page) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .getUserILikeItEarlier(uid, page)
                .compose(RxSchedulers.<EarlierBean>ioMain());
    }

    public static Observable<SimpleResponse> addPhotoWall(Map<String, RequestBody> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .addPhotoWall(params)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> addVideoWall(Map<String, RequestBody> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .addVideoWall(params)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<ChangeHeadBean> changeHead(Map<String, RequestBody> params) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .changeHead(params)
                .compose(RxSchedulers.<ChangeHeadBean>ioMain());
    }

    public static Observable<SimpleResponse> openVip(Map<String, Object> param) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .openVip(param)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }

    public static Observable<SimpleResponse> deleteVideo(String uid, int t_id, int type) {
        return ApiClient.get(C.BaseURL.BASE_URL)
                .create(MineService.class)
                .deleteVideo(uid, t_id, type)
                .compose(RxSchedulers.<SimpleResponse>ioMain());
    }
}
