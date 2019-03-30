package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.findtwo.adapter.MapAdapter;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.List;

public class FengjingFragment extends BaseFragment implements PoiSearch.OnPoiSearchListener, AMapLocationListener {

    SegmentTabLayout tabLayout_1;
    private String[] mTitles_3 = {"风景名胜", "观光街区", "温泉度假", "自驾出行"};
    private RecyclerView mFengRl;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private LatLonPoint searchLatlonPoint;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据
    private int currentPage = 0;// 当前页面，从0开始计数
    private String content = "";
    private MapAdapter mapAdapter;
    private ImageView imgLeft;

    private String[] arr = new String[]{"110000|110100|110200|110201|110202|110203|110205|110206|110207|110208|110209","061000|061001","080109|080401",
    "080500|080501|080502|080503|080504"};

    @Override
    public void initView(Bundle savedInstanceState) {
        tabLayout_1 = mRootView.findViewById(R.id.tl_1);
        tabLayout_1.setTabData(mTitles_3);
        imgLeft = mRootView.findViewById(R.id.img_left);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        content = arr[0];
        mFengRl = mRootView.findViewById(R.id.rl_jingdian);
        mapAdapter = new MapAdapter();
        mFengRl.setAdapter(mapAdapter);
        tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                content = arr[position];
                doSearchQuery();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mapAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", mapAdapter.getData().get(position));
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
        getLocation();
    }

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

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_fengjing;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");
        currentPage = 0;
        query = new PoiSearch.Query("", content, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
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

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        mapAdapter.replaceData(poiItems);
                    } else {
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
}
