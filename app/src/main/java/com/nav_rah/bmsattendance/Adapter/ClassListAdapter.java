package com.nav_rah.bmsattendance.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.nav_rah.bmsattendance.R;
import com.nav_rah.bmsattendance.realm.Class_Names;
import com.nav_rah.bmsattendance.realm.Students_List;
import com.nav_rah.bmsattendance.viewholders.ViewHolder;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ClassListAdapter extends RealmRecyclerViewAdapter<Class_Names, ViewHolder> {

    private final Activity mActivity;
    RealmResults<Class_Names> mList;

    Realm realm;
    RealmChangeListener realmChangeListener;

    public ClassListAdapter(RealmResults<Class_Names> list, Activity context) {

        super(context, list, true);
        Realm realm = Realm.getDefaultInstance();
        mActivity = context;
        mList = list;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_adapter, parent, false);
        return new ViewHolder(itemView, mActivity, mList);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Class_Names temp = getItem(position);

        Realm.init(mActivity);
        realm = Realm.getDefaultInstance();
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                long count = realm.where(Students_List.class)
                        .equalTo("class_id", temp.getId())
                        .count();
                holder.total_students.setText("Students : " + count);
            }
        };
        realm.addChangeListener(realmChangeListener);

        long count = realm.where(Students_List.class)
                .equalTo("class_id", temp.getId())
                .count();
        holder.total_students.setText("Students : " + count);
        holder.class_name.setText(temp.getName_class());
        holder.subject_name.setText(temp.getName_subject());

        switch (temp.getPosition_bg()) {
            case "0":
                holder.imageView_bg.setImageResource(R.drawable.asset_blue_bg);
//                holder.frameLayout.setBackgroundResource(R.drawable.gradient_color_1);
                break;
            case "1":
                holder.imageView_bg.setImageResource(R.drawable.asset_green_bg);
//                holder.frameLayout.setBackgroundResource(R.drawable.gradient_color_2);
                break;
            case "2":
                holder.imageView_bg.setImageResource(R.drawable.asset_yellow_bg);
//                holder.frameLayout.setBackgroundResource(R.drawable.asset_yellow_bg);
                break;
            case "3":
                holder.imageView_bg.setImageResource(R.drawable.asset_pale_green);
//                holder.frameLayout.setBackgroundResource(R.drawable.gradient_color_4);
                break;
            case "4":
                holder.imageView_bg.setImageResource(R.drawable.asset_orange_bg);
//                holder.frameLayout.setBackgroundResource(R.drawable.gradient_color_5);
                break;
            case "5":
                holder.imageView_bg.setImageResource(R.drawable.asset_pale_white_bg);
//                holder.frameLayout.setBackgroundResource(R.drawable.gradient_color_6);
                holder.subject_name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_color_secondary));
                holder.class_name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_color_secondary));
                holder.total_students.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_color_secondary));
                break;
        }

    }
}
