package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.findtwo.adapter.CinemaAdapter;
import com.kuwai.ysy.module.findtwo.adapter.MovieAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.FloatRatingBar;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import www.linwg.org.lib.LCardView;

public class CinemaChooseFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener, AMapLocationListener {

    private ImageView mImgLeft;
    private RelativeLayout mTopLay;
    private ImageView mImgMovie;
    private TextView mTvName;
    private RelativeLayout mScoreLay;
    private FloatRatingBar mRating;
    private TextView mTvScore;
    private TextView mTvDes;
    private TextView mTvDir;
    private TextView mTvActor;
    private LCardView mTvByOther;
    private RecyclerView mRlCinema;

    private CinemaAdapter cinemaAdapter;
    private LatLonPoint searchLatlonPoint;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据
    private int currentPage = 0;// 当前页面，从0开始计数

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgLeft = mRootView.findViewById(R.id.img_left);

        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mTopLay = mRootView.findViewById(R.id.top_lay);
        mImgMovie = mRootView.findViewById(R.id.img_movie);
        mTvName = mRootView.findViewById(R.id.tv_name);
        mScoreLay = mRootView.findViewById(R.id.score_lay);
        mRating = mRootView.findViewById(R.id.rating);
        mTvScore = mRootView.findViewById(R.id.tv_score);
        mTvDes = mRootView.findViewById(R.id.tv_des);
        mTvDir = mRootView.findViewById(R.id.tv_dir);
        mTvActor = mRootView.findViewById(R.id.tv_actor);
        mTvByOther = mRootView.findViewById(R.id.tv_by_other);
        mTvByOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("place", "任意电影院");//电影院名字
                //bundle.putParcelable("cinema",cinemaAdapter.getData().get(position));
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
        mRlCinema = mRootView.findViewById(R.id.rl_cinema);

        cinemaAdapter = new CinemaAdapter();
        mRlCinema.setAdapter(cinemaAdapter);
        cinemaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("place", cinemaAdapter.getData().get(position).getTitle());//电影院名字
                bundle.putParcelable("cinema", cinemaAdapter.getData().get(position));
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });

        cinemaAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage = currentPage + 1;
                doSearchQuery();
            }
        }, mRlCinema);
        GlideUtil.load(getActivity(), SPManager.get().getStringValue("cine_pic"), mImgMovie);
        mTvName.setText(SPManager.get().getStringValue("cine_name"));
        float score = SPManager.get().getFloatValue("cine_rate");
        mTvScore.setText(score + "");
        mRating.setRate(Float.parseFloat(Utils.format1Number(score / 2)));
        mTvDes.setText(SPManager.get().getStringValue("cine_des"));
        mTvDir.setText("导演：" + SPManager.get().getStringValue("cine_dir"));
        mTvName.setText("主演：" + SPManager.get().getStringValue("cine_actor"));
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_cinema;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getLocation();
    }

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private void getLocation() {

        mlocationClient = new AMapLocationClient(getActivity());
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置为高精度定位模式
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }

    private void getallMovie() {
       /* addSubscription(Appoint2ApiFactory.getAllMovie("苏州").subscribe(new Consumer<MovieBean>() {
                    @Override
                    public void accept(@NonNull MovieBean movieBean) throws Exception {
                        sportChooseAdapter.replaceData(movieBean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.error(throwable);
                    }
                }));*/
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        currentPage++;
                        cinemaAdapter.addData(poiItems);
                        cinemaAdapter.loadMoreComplete();
                    } else {
                        cinemaAdapter.loadMoreEnd();
                        Toast.makeText(getActivity(), "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getActivity(), "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");
        query = new PoiSearch.Query("电影院", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(false);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(getActivity(), query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 100000, true));//
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //mAmapLocation = amapLocation;
            //getInfo(amapLocation);
            searchLatlonPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
            doSearchQuery();
        } else {
            String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            //Log.e("AmapErr", errText);
        }
    }
}
