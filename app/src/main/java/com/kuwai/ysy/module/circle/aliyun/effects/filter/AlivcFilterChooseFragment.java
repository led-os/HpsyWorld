package com.kuwai.ysy.module.circle.aliyun.effects.filter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.aliyun.Common;
import com.kuwai.ysy.module.circle.aliyun.dialog.IPageTab;
import com.kuwai.ysy.module.circle.aliyun.effects.filter.interfaces.OnFilterItemClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 滤镜对话框页面
 *
 * @author xlx
 */
public class AlivcFilterChooseFragment extends Fragment implements IPageTab {

    private OnFilterItemClickListener onFilterItemClickListener;
    private FilterLoadingView filterLoadingView;
    private AsyncTask<Void, String, List<String>> filterLoadTask;
    private List<String> filterData;
    private int filterPosition;

    @Override
    public String getTabTitle() {
        return "滤镜";
    }

    @Override
    public int getTabIcon() {
        return R.mipmap.alivc_svideo_icon_tab_filter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        filterLoadingView = new FilterLoadingView(getContext());
        return filterLoadingView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (filterData == null || filterData.size() == 1) {
            filterLoadTask = new FilterDataLoadingTask(this);
            filterLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            filterLoadingView.notifyDataChange(filterData);
        }

        filterLoadingView.setOnFilterListItemClickListener(new OnFilterItemClickListener() {
            @Override
            public void onItemClick(EffectInfo effectInfo, int index) {
                if (onFilterItemClickListener != null) {
                    onFilterItemClickListener.onItemClick(effectInfo, index);
                }
            }
        });
    }

    public void setOnFilterItemClickListener(OnFilterItemClickListener listener) {
        this.onFilterItemClickListener = listener;
    }

    public void setFilterPosition(int filterPosition) {
        this.filterPosition = filterPosition;
    }

    @Override
    public void onStart() {
        super.onStart();
        filterLoadingView.setFilterPosition(filterPosition);
    }

    private static class FilterDataLoadingTask extends AsyncTask<Void, String, List<String>> {

        private WeakReference<AlivcFilterChooseFragment> contextWeakReference;

        FilterDataLoadingTask(AlivcFilterChooseFragment fragment) {
            contextWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return Common.getColorFilterList();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            AlivcFilterChooseFragment fragment = contextWeakReference.get();
            if (fragment != null) {
                fragment.notifyDataChanged(strings);
            }
        }
    }

    private void notifyDataChanged(List<String> strings) {
        // index 0的位置添加一条空数据, 让"无效果"显示出来
        this.filterData = strings;
        strings.add(0, "");
        filterLoadingView.notifyDataChange(filterData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (filterLoadTask != null) {
            filterLoadTask.cancel(true);
            filterLoadTask = null;
        }
    }
}
