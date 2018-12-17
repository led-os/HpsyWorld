package com.kuwai.ysy.listener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.utils.ListDataSave;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.address.AddressPicker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.functions.Consumer;

public class AddressPickTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private WeakReference<Activity> activityReference;// 2018/6/1 StaticFieldLeak
    private ProgressDialog dialog;
    private Callback callback;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideProvince = false;
    private boolean hideCounty = false;

    ListDataSave dataSave;

    public AddressPickTask(Activity activity) {
        this.activityReference = new WeakReference<>(activity);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        Activity activity = activityReference.get();
        if (activity == null) {
            return;
        }
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        final ArrayList<Province> data = new ArrayList<>();
        try {
            final Activity activity = activityReference.get();
            if (activity != null) {
                //String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
                //data.addAll(JSON.parseArray(json, Province.class));
                dataSave = new ListDataSave(activity, "addressList");
                if (dataSave.getDataList("addressList", Province.class).size() > 0) {
                    //ArrayList<Province> list = dataSave.getDataList("addressList", Province.class);
                    //back(list);
                } else {
                    FoundApiFactory.getProvinceList().subscribe(new Consumer<ProvincesAndCityBean>() {
                        @Override
                        public void accept(ProvincesAndCityBean provincesAndCityBean) throws Exception {
                            for (int i = 0; i < provincesAndCityBean.getData().size(); i++) {
                                Province province = new Province();
                                province.setAreaId(String.valueOf(provincesAndCityBean.getData().get(i).getRegion_id()));
                                province.setAreaName(provincesAndCityBean.getData().get(i).getRegion_name());
                                ArrayList<City> area = new ArrayList<>();
                                for (int j = 0; j < provincesAndCityBean.getData().get(i).getCity().size(); j++) {
                                    City city = new City(String.valueOf(provincesAndCityBean.getData().get(i).getCity().get(j).getRegion_id()), provincesAndCityBean.getData().get(i).getCity().get(j).getRegion_name());
                                    city.setProvinceId(String.valueOf(provincesAndCityBean.getData().get(i).getRegion_id()));
                                    area.add(city);
                                }
                                province.setCities(area);
                                data.add(province);
                            }
                            dataSave.setDataList("addressList", data);
                            back(data);
                            //mView.setHomeData(provincesAndCityBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //Log.i(TAG, "accept: "+throwable);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result) {

        if (dataSave.getDataList("addressList", Province.class).size() > 0) {
            ArrayList<Province> list = dataSave.getDataList("addressList", Province.class);
            back(list);
        }
    }

    public interface Callback extends AddressPicker.OnAddressPickListener {

        void onAddressInitFailed();

    }

    private void back(ArrayList<Province> result) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (result.size() > 0) {
            Activity activity = activityReference.get();
            if (activity == null) {
                return;
            }
            AddressPicker picker = new AddressPicker(activity, result);
            picker.setHideProvince(hideProvince);
            picker.setHideCounty(hideCounty);
            if (hideCounty) {
                picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);//省级、地级和县级的比例为2:3:3
            }
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(callback);
            picker.show();
        } else {
            callback.onAddressInitFailed();
        }
    }

}
