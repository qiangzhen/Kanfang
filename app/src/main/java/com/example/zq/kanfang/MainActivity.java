package com.example.zq.kanfang;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zq.kanfang.fragment.DiscoverFragment;
import com.example.zq.kanfang.fragment.FirstPageFragment;

/**
 * 测试
 */

public class MainActivity extends FragmentActivity {

    private LinearLayout ll_discover;
    private LinearLayout ll_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ll_discover = (LinearLayout) findViewById(R.id.ll_discover);
        ll_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new DiscoverFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fm_container, f).addToBackStack(null).commit();
            }
        });

        ll_index = (LinearLayout) findViewById(R.id.ll_index);
        ll_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FirstPageFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fm_container, fragment).addToBackStack(null).commit();

            }
        });
        Fragment fragment = new FirstPageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fm_container, fragment).commit();


    }


}
