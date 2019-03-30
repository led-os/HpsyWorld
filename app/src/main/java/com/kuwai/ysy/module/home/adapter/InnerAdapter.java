package com.kuwai.ysy.module.home.adapter;

import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.MyApp;
import com.kuwai.ysy.callback.CardCallback;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.business.main.MainFragment;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.Collection;

public class InnerAdapter extends BaseAdapter {

    ArrayList<HomeCardBean.DataBean> objs;
    private CardCallback mCallBack = null;
    private int cardWidth;
    private int cardHeight;

    public void setCallBack(CardCallback callBack) {
        mCallBack = callBack;
    }

    public InnerAdapter() {
        objs = new ArrayList<>();
        DisplayMetrics dm = MyApp.getAppContext().getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = cardWidth;
    }

    public void addAll(Collection<HomeCardBean.DataBean> collection) {
        if (isEmpty()) {
            objs.clear();
            objs.addAll(collection);
            notifyDataSetChanged();
        } else {
            objs.addAll(collection);
        }
    }

    public void clear() {
        objs.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return objs.isEmpty();
    }

    public void remove(int index) {
        if (index > -1 && index < objs.size()) {
            objs.remove(index);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public HomeCardBean.DataBean getItem(int position) {
        if (objs == null || objs.size() == 0) return null;
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // TODO: getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        HomeCardBean.DataBean talent = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

            //holder.portraitView.getLayoutParams().width = cardWidth;
            //holder.portraitView.getLayoutParams().height = cardWidth;
            //convertView.getLayoutParams().width = cardWidth;
            //holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(talent.getNickname());
        holder.signTv.setText(talent.getSig());
        holder.heightTv.setText(talent.getHeight() + "cm");
        holder.eduView.setText(talent.getEducation());
        holder.starTv.setText(Utils.getStar(talent.getConstellation()) + "座");
        holder.sexTv.setText(talent.getAge());
        GlideUtil.loadwithNobg(parent.getContext(), talent.getAttach().get(0).getImg(), holder.portraitView);
        if (!Utils.isNullString(String.valueOf(talent.getDistance())) && talent.getDistance() > 1) {
            if (talent.getDistance() < 99) {
                holder.cityView.setText(talent.getLastcity() + Utils.format1Number(talent.getDistance()) + "km");
            } else {
                holder.cityView.setText(talent.getLastcity() + ">99km");
            }
        } else {
            holder.cityView.setText(talent.getLastcity() + "<1km");
        }
        if (talent.getGender() == 1) {
            Drawable drawableLeft = parent.getContext().getResources().getDrawable(
                    R.drawable.home_icon_male);
            holder.sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            holder.sexTv.setBackgroundResource(R.drawable.bg_sex_man);
        } else {
            Drawable drawableLeft = parent.getContext().getResources().getDrawable(
                    R.drawable.home_icon_female);
            holder.sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            holder.sexTv.setBackgroundResource(R.drawable.bg_sex_round);
        }

        //holder.portraitView.setImageResource(talent.getAvatar());
        return convertView;
    }

    public static class ViewHolder {
        ImageView portraitView;
        TextView nameView;
        TextView cityView;
        SuperButton eduView;
        TextView sexTv;
        TextView signTv;
        SuperButton starTv;
        SuperButton heightTv;

        public ViewHolder(View itemView) {
            portraitView = (ImageView) itemView.findViewById(R.id.portrait);
            nameView = itemView.findViewById(R.id.tv_name);
            cityView = itemView.findViewById(R.id.tv_location);
            eduView = itemView.findViewById(R.id.tv_edu);
            starTv = itemView.findViewById(R.id.tv_star);
            heightTv = itemView.findViewById(R.id.tv_height);
            sexTv = itemView.findViewById(R.id.tv_sex);
            signTv = itemView.findViewById(R.id.tv_sign);
        }

        public ImageView getImageView() {
            return portraitView;
        }
    }
}
