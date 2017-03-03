package com.rideread.rideread.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rideread.rideread.activity.MessageListActivity;
import com.rideread.rideread.activity.MineAttentionActivity;
import com.rideread.rideread.activity.MineEditMessageActivity;
import com.rideread.rideread.activity.MineFansActivity;
import com.rideread.rideread.R;
import com.rideread.rideread.activity.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/1/26.
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.mine_fragment_layout,container,false);
        initView();
        return mView;
    }

    private void initView(){
        TextView mineMessage=(TextView)mView.findViewById(R.id.mine_tv_message);
        TextView mineAttention=(TextView)mView.findViewById(R.id.mine_tv_attention);
        TextView mineFans=(TextView)mView.findViewById(R.id.mine_tv_fans);
        TextView mineAttentionNum=(TextView)mView.findViewById(R.id.mine_tv_attention_num);
        TextView mineFansNum=(TextView)mView.findViewById(R.id.mine_tv_fans_num);
        ImageView mineSetting=(ImageView)mView.findViewById(R.id.left_setting_icon);
        CircleImageView mineUserHead=(CircleImageView) mView.findViewById(R.id.mine_civ_userhead);

        mineMessage.setOnClickListener(this);
        mineAttention.setOnClickListener(this);
        mineFans.setOnClickListener(this);
        mineSetting.setOnClickListener(this);
        mineUserHead.setOnClickListener(this);
        mineAttentionNum.setOnClickListener(this);
        mineFansNum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mine_tv_message:
                //Toast.makeText(getContext(),"我的消息",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),MessageListActivity.class));
                break;
            case R.id.mine_tv_attention_num:
            case R.id.mine_tv_attention:
                startActivity(new Intent(getActivity(),MineAttentionActivity.class));
                break;
            case R.id.mine_tv_fans_num:
            case R.id.mine_tv_fans:
                startActivity(new Intent(getActivity(), MineFansActivity.class));
                break;
            case R.id.left_setting_icon:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.mine_civ_userhead:
                startActivity(new Intent(getActivity(),MineEditMessageActivity.class));
                break;
        }
    }
}
