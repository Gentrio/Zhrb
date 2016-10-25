package com.gentrio.zhrb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gentrio.zhrb.R;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }

    public static boolean start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, AboutMeActivity.class);
        activity.startActivity(intent);
        return true;
    }
}
