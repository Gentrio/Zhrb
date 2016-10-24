package com.gentrio.zhrb.ui.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gentrio.zhrb.R;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected void initToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BaseActivity.this.finish();
                }
            });
            setTitle("");
        }
    }

    protected void setTitle(String title) {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }


}
