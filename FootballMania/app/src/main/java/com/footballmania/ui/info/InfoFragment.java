package com.footballmania.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.footballmania.R;

public class InfoFragment extends Fragment {

    private ImageView image;
    private TextView t1,t2,t3,t4,t5,t6,t7;
    private Animation topAnim, bottomAnim;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_anim);

        image = root.findViewById(R.id.imageView4);
        t1 = root.findViewById(R.id.info_app_name);
        t2 = root.findViewById(R.id.textView4);
        t3 = root.findViewById(R.id.textView3);
        t4 = root.findViewById(R.id.textView5);
        t5 = root.findViewById(R.id.textView6);
        t6 = root.findViewById(R.id.textView7);
        t7 = root.findViewById(R.id.textView8);

        image.setAnimation(topAnim);
        t1.setAnimation(bottomAnim);
        t2.setAnimation(bottomAnim);
        t3.setAnimation(bottomAnim);
        t4.setAnimation(bottomAnim);
        t5.setAnimation(bottomAnim);
        t6.setAnimation(bottomAnim);
        t7.setAnimation(bottomAnim);

        return root;
    }

/*    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }*/

}