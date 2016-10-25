package com.gentrio.zhrb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.gentrio.zhrb.R;
import com.gentrio.zhrb.adapter.NewsListAdapter;
import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.service.DbService;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CollectActivity extends BaseActivity {

    private LatestBean data;
    private NewsListAdapter adapter;
    private RecyclerView rc;
    private DbService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initVariable();
        initView();
    }

    private void initVariable() {
        data = new LatestBean();
        List<LatestBean.StoriesBean> stories = new ArrayList<>();
        data.setStories(stories);
        dbService = new DbService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        setTitle("收藏");
        rc = (RecyclerView) findViewById(R.id.rc);
        adapter = new NewsListAdapter(CollectActivity.this, data);
        rc.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(CollectActivity.this);
        rc.setLayoutManager(manager);
    }

    private void getData() {

        dbService.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LatestBean.StoriesBean>>() {
                    @Override
                    public void call(List<LatestBean.StoriesBean> storiesBeen) {
                        data.setStories(storiesBeen);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public static boolean start(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity, CollectActivity.class);
        activity.startActivity(intent);
        return true;
    }
}
