package com.kuwai.ysy.module.mine.business.change;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.AddressPickTask;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDEditDialog;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.functions.Consumer;

public class ChangeInfoFragment extends BaseFragment implements View.OnClickListener {

    private TagAdapter zeouAdapter;
    private LayoutInflater mInflater;
    private List<PersolHome2PageBean.DataBean.SelectionBean> mValsZeou = new ArrayList<>();

    private NavigationLayout mNavigation;
    private SuperTextView mTvId;
    private SuperTextView mTvAge;
    private SuperTextView mTvHeight, tvWeight;
    private SuperTextView mTvNick;
    private SuperTextView mTvGender;
    private SuperTextView mTvBirth;
    private SuperTextView mTvEdu;
    private SuperTextView mTvIncome;
    private SuperTextView mTvPlace;
    private SuperTextView mTvHouse;
    private SuperTextView mTvCar;
    private SuperTextView mTvMarry;
    private SuperTextView mTvZongjiao;
    private SuperTextView mTvChild;
    private SuperTextView mTvZeou, mJobTv, mGoodAtTv, mAboutLoveTv, mAboutSexTv, mAgreeTv;

    private CustomDialog incomeDialog, heightDialog, eduDialog, birthDialog, zongDialog, marryDialog, childDialog;
    private String height = "165cm";
    private int incomeId = 4;
    private String income = "";
    private String mYear = "1990";
    private String mMonth = "01";
    private String mDay = "01";
    private String eduTv = "大专";
    private String cityId = "0";
    private String zongJiao = "";
    private String marryString = "";
    private String childString = "";
    private TagFlowLayout tv_tag;
    private String jobId = "";
    private StringBuffer addBuffer = new StringBuffer();
    private StringBuffer delBuffer = new StringBuffer();

    private String[] chaildArray = new String[]{"有", "无"};
    private String[] marry = new String[]{"未婚", "已婚", "离异"};
    private String[] zong = new String[]{"佛教", "道教", "基督教", "伊斯兰教"};

    public static String TYPE_MINE = "MY";
    public static String TYPE_OTHER = "OTHER";
    private PersolHome2PageBean mPersolHomePageBean;

    public static ChangeInfoFragment newInstance(Bundle bundle) {
        ChangeInfoFragment fragment = new ChangeInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_change_info;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPersolHomePageBean = (PersolHome2PageBean) getArguments().getSerializable("data");

        mNavigation = mRootView.findViewById(R.id.navigation);
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo();
            }
        });
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mTvId = mRootView.findViewById(R.id.tvId);
        mTvAge = mRootView.findViewById(R.id.tvAge);
        tv_tag = mRootView.findViewById(R.id.tv_tag);
        mTvZeou = mRootView.findViewById(R.id.tvZeou);
        mTvHeight = mRootView.findViewById(R.id.tvHeight);
        tvWeight = mRootView.findViewById(R.id.tvWeight);
        mTvNick = mRootView.findViewById(R.id.tvNick);
        mTvGender = mRootView.findViewById(R.id.tvGender);
        if (mPersolHomePageBean.getData().getInfo().getGender() == 1) {
            mTvGender.setCenterString("男");
        } else if (mPersolHomePageBean.getData().getInfo().getGender() == 2) {
            mTvGender.setCenterString("女");
        } else {
            mTvGender.setCenterString("未知");
        }
        mTvBirth = mRootView.findViewById(R.id.tvBirth);
        mTvEdu = mRootView.findViewById(R.id.tvEdu);
        mJobTv = mRootView.findViewById(R.id.tvJob);
        mGoodAtTv = mRootView.findViewById(R.id.tvGoodAt);
        mAboutLoveTv = mRootView.findViewById(R.id.tvAboutLove);
        mAboutSexTv = mRootView.findViewById(R.id.tvAboutSex);
        mAgreeTv = mRootView.findViewById(R.id.tvAgree);
        mInflater = LayoutInflater.from(getActivity());
        mTvIncome = mRootView.findViewById(R.id.tvIncome);
        mTvPlace = mRootView.findViewById(R.id.tvPlace);
        mTvHouse = mRootView.findViewById(R.id.tvHouse);
        mTvCar = mRootView.findViewById(R.id.tvCar);
        mTvMarry = mRootView.findViewById(R.id.tvMarry);
        mTvZongjiao = mRootView.findViewById(R.id.tvZongjiao);
        mTvChild = mRootView.findViewById(R.id.tvChild);

        mTvHeight.setOnClickListener(this);
        mTvNick.setOnClickListener(this);
        mTvBirth.setOnClickListener(this);
        mTvEdu.setOnClickListener(this);
        mTvIncome.setOnClickListener(this);
        mTvZeou.setOnClickListener(this);
        mTvPlace.setOnClickListener(this);
        mTvHouse.setOnClickListener(this);
        mTvCar.setOnClickListener(this);
        mTvMarry.setOnClickListener(this);
        mTvZongjiao.setOnClickListener(this);
        mTvChild.setOnClickListener(this);

        mJobTv.setOnClickListener(this);
        mGoodAtTv.setOnClickListener(this);
        mAboutLoveTv.setOnClickListener(this);
        mAboutSexTv.setOnClickListener(this);
        mAgreeTv.setOnClickListener(this);

        setData();
    }

    private void setData() {
        if (mPersolHomePageBean != null) {
            PersolHome2PageBean.DataBean.InfoBean bean = mPersolHomePageBean.getData().getInfo();
            mTvId.setCenterString(bean.getUid() + "");
            mTvHeight.setCenterString(bean.getHeight() + "cm");
            mTvNick.setCenterString(bean.getNickname());
            mTvBirth.setCenterString(bean.getBirthday());
            mTvEdu.setCenterString(bean.getEducation());
            mTvIncome.setCenterString(bean.getAnnual_income());
            mTvPlace.setCenterString(bean.getCity());
            mTvMarry.setCenterString(bean.getMarriage());
            mTvZongjiao.setCenterString(bean.getReligion());
            mTvChild.setCenterString(bean.getChildren());
            mJobTv.setCenterString(bean.getOccupation());
            mGoodAtTv.setCenterString(bean.getAdvantages());
            mAboutLoveTv.setCenterString(bean.getLove_view());
            mAboutSexTv.setCenterString(bean.getNature_view());
            mAgreeTv.setCenterString(bean.getRound());

            if(bean.getIs_house() == 1){
                mTvHouse.setCenterString("已认证");
            }else if(bean.getIs_house() == 2){
                mTvHouse.setCenterString("审核中");
            }else{
                mTvHouse.setCenterString("未认证");
            }

            if(bean.getIs_vehicle() == 1){
                mTvCar.setCenterString("已认证");
            }else if(bean.getIs_house() == 2){
                mTvCar.setCenterString("审核中");
            }else{
                mTvCar.setCenterString("未认证");
            }


            zeouAdapter = new TagAdapter<PersolHome2PageBean.DataBean.SelectionBean>(mValsZeou) {
                @Override
                public View getView(FlowLayout parent, final int position, final PersolHome2PageBean.DataBean.SelectionBean s) {
                    SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_zeou, tv_tag, false);
                    tv.setText(s.getText());
                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            new NormalAlertDialog.Builder(getActivity())
                                    .setTitleVisible(false)
                                    .setContentText("确定删除该条件？")
                                    .setLeftButtonText("确定")
                                    .setRightButtonText("取消")
                                    .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                                        @Override
                                        public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                            delBuffer.append(s.getS_id() + ',');
                                            mValsZeou.remove(s);
                                            zeouAdapter.notifyDataChanged();
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void clickRightButton(NormalAlertDialog dialog, View view) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setCanceledOnTouchOutside(true)
                                    .build().show();
                            return false;
                        }
                    });
                    return tv;
                }
            };

            tv_tag.setAdapter(zeouAdapter);

            mValsZeou.addAll(mPersolHomePageBean.getData().getSelection());
            zeouAdapter.notifyDataChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNick:
                showEdit(mTvNick.getCenterString(), "修改昵称");
                break;
            case R.id.tvAboutLove:
                showEdit("", "对爱情的看法");
                break;
            case R.id.tvAboutSex:
                showEdit("", "对性的看法");
                break;
            case R.id.tvAgree:
                showEdit("", "最满意的地方");
                break;
            case R.id.tvZeou:
                showEdit("", "添加择偶条件");
                break;
            case R.id.tvWeight:
                showEdit("", "体重");
                break;
            case R.id.tvJob:
                startForResult(JobChooseFragment.newInstance(), 0);
                break;
            case R.id.tvPlace:
                popAddressCustom(TYPE_MINE);
                break;
            case R.id.tvHeight:
                popHeightCustom();
                break;
            case R.id.tvBirth:
                popBirthCustom();
                break;
            case R.id.tvEdu:
                popEduCustom(TYPE_MINE);
                break;
            case R.id.tvIncome:
                popIncomeCustom(TYPE_MINE);
                break;
            case R.id.tvHouse:
                break;
            case R.id.tvCar:
                break;
            case R.id.tvMarry:
                popMarryCustom();
                break;
            case R.id.tvZongjiao:
                popZongCustom();
                break;
            case R.id.tvChild:
                popChildCustom();
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // 在此通过Bundle data 获取返回的数据
            if (data != null) {
                mJobTv.setCenterString(data.getString("job"));
                jobId = data.getString("id");
            }
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popHeightCustom() {
        if (heightDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择身高");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                    }
                }
            });

            final NumberPicker picker = new NumberPicker(getActivity());
            picker.setWidth(picker.getScreenWidthPixels() / 2);
            picker.setCycleDisable(true);
            picker.setDividerVisible(false);
            picker.setOffset(2);//偏移量
            picker.setRange(145, 210, 1);//数字范围
            picker.setSelectedItem(165);
            picker.setLabel("cm");
            picker.setItemWidth(66);
            picker.setLabelTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new NumberPicker.OnWheelListener() {
                @Override
                public void onWheeled(int index, Number item) {
                    height = item + "";
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                        mTvHeight.setCenterString(picker.getSelectedItem() + "cm");
                    }
                }
            });
            heightDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        heightDialog.show();
    }

    private void popBirthCustom() {
        if (birthDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (birthDialog != null) {
                        birthDialog.dismiss();
                    }
                }
            });

            final DatePicker datePicker = new DatePicker(getActivity(), DateTimePicker.YEAR_MONTH_DAY);
            datePicker.setOffset(2);
            datePicker.setRangeEnd(2020, 12, 31);
            datePicker.setRangeStart(1970, 01, 01);
            datePicker.setSelectedItem(1990, 01, 01);
            datePicker.setTopLineColor(0xFF5415f9);
            datePicker.setLabelTextColor(0xFF5415f9);
            datePicker.setDividerColor(0xFF5415f9);
            datePicker.setTextSize(22);
            datePicker.setTextPadding(20);
            datePicker.setTextColor(getResources().getColor(R.color.balck_28));
            datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
                    mYear = year;
                }

                @Override
                public void onMonthWheeled(int index, String month) {
                    mMonth = month;
                }

                @Override
                public void onDayWheeled(int index, String day) {
                    mDay = day;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(datePicker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (birthDialog != null) {
                        birthDialog.dismiss();
                    }
                    mTvBirth.setCenterString(datePicker.getSelectedYear() + "-" + datePicker.getSelectedMonth() + "-" + datePicker.getSelectedDay());
                }
            });
            birthDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        birthDialog.show();
    }

    private void popZongCustom() {
        if (zongDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择宗教");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (zongDialog != null) {
                        zongDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(zong));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<String>() {
                @Override
                public void onWheeled(int index, String item) {
                    zongJiao = item;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (zongDialog != null) {
                        zongDialog.dismiss();
                        mTvZongjiao.setCenterString(picker.getSelectedItem());
                    }
                }
            });
            zongDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        zongDialog.show();
    }

    private void popMarryCustom() {
        if (marryDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择婚姻状况");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (marryDialog != null) {
                        marryDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(marry));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<String>() {
                @Override
                public void onWheeled(int index, String item) {
                    marryString = item;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (marryDialog != null) {
                        marryDialog.dismiss();
                        mTvMarry.setCenterString(picker.getSelectedItem());
                    }
                }
            });
            marryDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        marryDialog.show();
    }

    private void popChildCustom() {
        if (childDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择子女状况");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childDialog != null) {
                        childDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(chaildArray));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<String>() {
                @Override
                public void onWheeled(int index, String item) {
                    childString = item;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childDialog != null) {
                        childDialog.dismiss();
                        mTvChild.setCenterString(picker.getSelectedItem());
                    }
                }
            });
            childDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        childDialog.show();
    }


    private void popEduCustom(final String type) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView top = pannel.findViewById(R.id.top);
        top.setText("选择学历");

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eduDialog != null) {
                    eduDialog.dismiss();
                }
            }
        });

        List<GoodsCategory> data = new ArrayList<>();
        data.add(new GoodsCategory(1, "大专以下"));
        data.add(new GoodsCategory(2, "大专"));
        data.add(new GoodsCategory(3, "本科"));
        data.add(new GoodsCategory(4, "硕士"));
        data.add(new GoodsCategory(5, "博士"));
        data.add(new GoodsCategory(6, "海外留学"));
        final SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setOffset(2);
        picker.setCycleDisable(true);
        picker.setDividerColor(0xFF5415f9);
        picker.setTextSize(26);
        picker.setTextColor(getResources().getColor(R.color.balck_28));
        picker.setTextPadding(20);
        picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
            @Override
            public void onWheeled(int index, GoodsCategory item) {
                if (TYPE_MINE.equals(type)) {
                    eduTv = item.getName();
                } else {
                    //zeeduTv = item.getName();
                }
            }
        });
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eduDialog != null) {
                    eduDialog.dismiss();
                    if (TYPE_MINE.equals(type)) {
                        mTvEdu.setCenterString(picker.getSelectedItem().getName());
                    } else {
                        //mTvZeEdu.setCenterString(picker.getSelectedItem().getName());
                    }
                }
            }
        });
        eduDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        eduDialog.show();
    }

    private void popIncomeCustom(final String type) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView top = pannel.findViewById(R.id.top);
        top.setText("选择年收入");

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomeDialog != null) {
                    incomeDialog.dismiss();
                }
            }
        });

        List<GoodsCategory> data = new ArrayList<>();
        data.add(new GoodsCategory(1, "3W以下"));
        data.add(new GoodsCategory(2, "3W-5W"));
        data.add(new GoodsCategory(3, "5W-8W"));
        data.add(new GoodsCategory(4, "8W-10W"));
        data.add(new GoodsCategory(5, "10W-15W"));
        data.add(new GoodsCategory(6, "15W-20W"));
        data.add(new GoodsCategory(7, "20W以上"));
        final SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(3);
        picker.setOffset(2);
        picker.setCycleDisable(true);
        picker.setDividerColor(0xFF5415f9);
        picker.setTextSize(26);
        picker.setTextColor(getResources().getColor(R.color.balck_28));
        picker.setTextPadding(20);
        picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
            @Override
            public void onWheeled(int index, GoodsCategory item) {

                if (TYPE_MINE.equals(type)) {
                    incomeId = item.getId();
                    income = item.getName();
                } else {
                    //zeincomeId = item.getId();
                    //zeincome = item.getName();
                }

            }
        });
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomeDialog != null) {
                    incomeDialog.dismiss();
                    if (TYPE_MINE.equals(type)) {
                        incomeId = picker.getSelectedItem().getId();
                        mTvIncome.setCenterString(picker.getSelectedItem().getName());
                    } else {
                        //zeincomeId = picker.getSelectedItem().getId();
                        //mTvZeIncome.setCenterString(picker.getSelectedItem().getName());
                    }

                }
            }
        });
        incomeDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        incomeDialog.show();
    }

    private void popAddressCustom(final String type) {
        // if (themeDialog == null) {

        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        AddressPickTask task = new AddressPickTask(getActivity());
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (TYPE_MINE.equals(type)) {
                    cityId = city.getAreaId();
                    mTvPlace.setCenterString(province.getAreaName() + "省" + city.getAreaName());
                } else {
                    //zecityId = city.getAreaId();
                    //mTvZePlace.setCenterString(province.getAreaName() + "省" + city.getAreaName());
                }
                //mBtnGetPos.setText(province.getAreaName() + "省" + city.getAreaName());
            }
        });
        task.execute("江苏", "苏州市");
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        //layout.addView(task.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtils.showShort(mYear + "年" + mMonth + "月" + mDay + "日");
            }
        });
        /*themeDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();*/
        //}
        //themeDialog.show();
    }

    private void changeInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", SPManager.get().getStringValue("uid"));
        if (!Utils.isNullString(mTvHeight.getCenterString())) {
            params.put("height", mTvHeight.getCenterString().replace("cm", ""));
        }
        if (!Utils.isNullString(mTvNick.getCenterString())) {
            params.put("nickname", mTvNick.getCenterString());
        }
        if (!Utils.isNullString(mTvEdu.getCenterString())) {
            params.put("education", mTvEdu.getCenterString());
        }
        if (!Utils.isNullString(mTvIncome.getCenterString())) {
            params.put("annual_income", incomeId);
        }
        if (!Utils.isNullString(mTvBirth.getCenterString())) {
            params.put("age", mTvBirth.getCenterString());
        }
        if (!Utils.isNullString(mTvPlace.getCenterString())) {
            params.put("city", cityId);
        }
        if (!Utils.isNullString(mTvMarry.getCenterString())) {
            params.put("marriage", mTvMarry.getCenterString());
        }
        if (!Utils.isNullString(mTvZongjiao.getCenterString())) {
            params.put("religion", mTvZongjiao.getCenterString());
        }
        if (!Utils.isNullString(mTvChild.getCenterString())) {
            params.put("children", mTvChild.getCenterString());
        }
        if (!Utils.isNullString(tvWeight.getCenterString())) {
            params.put("weight", tvWeight.getCenterString());
        }
        if (!Utils.isNullString(mGoodAtTv.getCenterString())) {
            params.put("advantages", mGoodAtTv.getCenterString());
        }
        if (!Utils.isNullString(mAboutLoveTv.getCenterString())) {
            params.put("love_view", mAboutLoveTv.getCenterString());
        }
        if (!Utils.isNullString(mAboutSexTv.getCenterString())) {
            params.put("nature_view", mAboutSexTv.getCenterString());
        }
        if (!Utils.isNullString(mAgreeTv.getCenterString())) {
            params.put("round", mAgreeTv.getCenterString());
        }
        if (!Utils.isNullString(mJobTv.getCenterString())) {
            params.put("job", jobId);
        }
        if (addBuffer.length() > 0) {
            params.put("add_selection", addBuffer.toString().substring(0, addBuffer.length() - 1));
        }
        if (delBuffer.length() > 0) {
            params.put("del_selection", delBuffer.toString().substring(0, delBuffer.length() - 1));
        }

        addSubscription(MineApiFactory.change2Info(params).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_CHANGE_INFO));
                    pop();
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void showEdit(String content, final String title) {
        new MDEditDialog.Builder(getActivity())
                .setTitleText(title)
                .setContentText(content)
                .setCanceledOnTouchOutside(true)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
                    @Override
                    public void clickLeftButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                        switch (title) {
                            case "修改昵称":
                                if (dialog.getEditTextContent().length() > 10) {
                                    ToastUtils.showShort("昵称最长10个字符");
                                    return;
                                }
                                mTvNick.setCenterString(dialog.getEditTextContent());
                                break;
                            case "对爱情的看法":
                                mAboutLoveTv.setCenterString(dialog.getEditTextContent());
                                break;
                            case "对性的看法":
                                mAboutSexTv.setCenterString(dialog.getEditTextContent());
                                break;
                            case "最满意的地方":
                                mAgreeTv.setCenterString(dialog.getEditTextContent());
                                break;
                            case "添加择偶条件":
                                addBuffer.append(dialog.getEditTextContent() + ',');
                                PersolHome2PageBean.DataBean.SelectionBean bean = new PersolHome2PageBean.DataBean.SelectionBean();
                                bean.setText(dialog.getEditTextContent());
                                mValsZeou.add(bean);
                                zeouAdapter.notifyDataChanged();
                                break;
                            case "体重":
                                tvWeight.setCenterString(dialog.getEditTextContent());
                                break;
                        }
                    }
                })
                .build().show();
    }
}
