package com.gentrio.zhrb.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.gentrio.zhrb.R;
import com.gentrio.zhrb.adapter.NewsListAdapter;
import com.gentrio.zhrb.app.BaseApplication;
import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.service.Service;
import com.gentrio.zhrb.service.ServiceClient;
import com.gentrio.zhrb.service.SimpleCallBack;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_refresh;
    private LatestBean data;
    private NewsListAdapter adapter;
    private boolean isLoading;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        getData();
    }

    //变量初始化
    private void initVariable() {
        data = new LatestBean(new String(),new ArrayList<LatestBean.StoriesBean>(),new ArrayList<LatestBean.TopStoriesBean>());
        service = ServiceClient.getService();
        SharedPreferences sp = getSharedPreferences("nightMode", MODE_PRIVATE);
        BaseApplication.setIsNight(sp.getBoolean("isNight", false));
        if (BaseApplication.getIsNight()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //控件初始化
    private void initView() {

        setTitle(R.string.main_name);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);


        //设置下拉刷新监听器
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        adapter = new NewsListAdapter(MainActivity.this, data);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);

        //上拉加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition+1==adapter.getItemCount()){
                    //获取是否在下拉刷新
                    boolean isRefresh = swipe_refresh.isRefreshing();
                    if (isRefresh){
                        return;
                    }
                    if (!isLoading){
                        isLoading = true;
                        //没有处于加载状态，然后需要加载数据，就可以分页加载了
                        service.getBefore(Integer.parseInt(data.getDate())+"").enqueue(new SimpleCallBack<LatestBean>() {
                            @Override
                            public void onResponse(LatestBean result, int code, String msg) {
                                data.setDate(result.getDate());
                                data.getStories().addAll(result.getStories());
                                adapter.notifyDataSetChanged();
                                isLoading = false;
                            }
                        });
                    }
                }
            }
        });
    }


    //加载数据
    private void getData() {
        service.getLatest().enqueue(new SimpleCallBack<LatestBean>() {
            @Override
            public void onResponse(LatestBean result, int code, String msg) {
                data.getTop_stories().clear();
                data.getStories().clear();
                data.setDate(result.getDate());
                data.getTop_stories().addAll(result.getTop_stories());
                data.getStories().addAll(result.getStories());
                adapter.notifyDataSetChanged();
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (BaseApplication.getIsNight()) {
            menu.findItem(R.id.action_night).setTitle("日间模式");
        }else{
            menu.findItem(R.id.action_night).setTitle("夜间模式");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            //关于我
            case R.id.action_about:
                return AboutMeActivity.start(this);
            //收藏
            case R.id.action_collect:
                return CollectActivity.start(this);
            //夜间模式
            case R.id.action_night:
                //更改配置信息
                BaseApplication.setIsNight(!BaseApplication.getIsNight());
                SharedPreferences sp = getSharedPreferences("nightMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isNight", BaseApplication.getIsNight());
                editor.commit();
                if (BaseApplication.getIsNight()) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
