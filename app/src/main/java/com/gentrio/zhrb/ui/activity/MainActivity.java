package com.gentrio.zhrb.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import com.gentrio.zhrb.R;
import com.gentrio.zhrb.adapter.ThemesMenuAdapter;
import com.gentrio.zhrb.app.BaseApplication;
import com.gentrio.zhrb.bean.ThemesMenuBean;
import com.gentrio.zhrb.service.Service;
import com.gentrio.zhrb.service.ServiceClient;
import com.gentrio.zhrb.service.SimpleCallBack;
import com.gentrio.zhrb.ui.fragment.IndexFragment;
import com.gentrio.zhrb.ui.fragment.ThemeFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ThemesMenuAdapter.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private RecyclerView menu_list;
    private ThemesMenuBean themesMenuBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        setContentView(R.layout.activity_main);
        initNavigationView();
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        IndexFragment fragment = new IndexFragment();
        transaction.replace(R.id.fragment_layout,fragment);
        transaction.commit();
    }

    //初始化抽屉 = =
    private void initNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView menuView = (NavigationMenuView) navigationView.getChildAt(0);
        menuView.setVerticalScrollBarEnabled(false);

        if (BaseApplication.getIsNight()) {
            navigationView.getHeaderView(0).setBackgroundResource(R.drawable.wan);
        }else{
            navigationView.getHeaderView(0).setBackgroundResource(R.drawable.qiao);
        }

        RadioGroup textGroup = (RadioGroup) navigationView.getHeaderView(0).findViewById(R.id.font_size);
        if (BaseApplication.getIsNormal()) {
            textGroup.check(R.id.font_normal);
        }else{
            textGroup.check(R.id.font_big);
        }

        //改变字体大小
        textGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                switch (checkedId) {
                    case R.id.font_normal:
                        editor.putBoolean("isNormal", true);
                        break;
                    case R.id.font_big:
                        editor.putBoolean("isNormal", false);
                        break;
                }
                BaseApplication.setIsNormal(!BaseApplication.getIsNormal());
                editor.commit();
            }
        });

        menu_list = (RecyclerView) navigationView.findViewById(R.id.menu_list);
        getThemes();
    }

    private void getThemes() {
        Service service = ServiceClient.getService();
        service.getThemesMenu().enqueue(new SimpleCallBack<ThemesMenuBean>() {
            @Override
            public void onResponse(ThemesMenuBean result, int code, String msg) {
                themesMenuBean = result;
                ThemesMenuAdapter adapter = new ThemesMenuAdapter(MainActivity.this,result);
                adapter.setOnItemClickListener(MainActivity.this);
                menu_list.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                menu_list.setLayoutManager(manager);
            }
        });
    }


    //变量初始化
    private void initVariable() {
        //初始化配置信息 夜间模式 字体大小 是否加载图片
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        BaseApplication.setIsNight(sp.getBoolean("isNight", false));
        BaseApplication.setIsNormal(sp.getBoolean("isNormal", true));
        BaseApplication.setIsLoadImg(sp.getBoolean("isLoadImg",true));
        if (BaseApplication.getIsNight()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onBackPressed() {
        //如果存在别的Fragment 会退到IndexFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            closeDrawer();
            fragmentManager.popBackStack("fragment",1);
        } else {
            if (!closeDrawer()) {
                super.onBackPressed();
            }
        }
    }

    public boolean closeDrawer() {
        // 如果drawerLayout不为null并且已经打开则关闭
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return true;
        }
        return false;
    }

    //初始化菜单的配置信息 夜间模式和是否加载图片的图标
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (BaseApplication.getIsNight()) {
            menu.findItem(R.id.action_night).setTitle("日间模式");
        }else{
            menu.findItem(R.id.action_night).setTitle("夜间模式");
        }
        if (BaseApplication.getIsLoadImg()) {
            menu.findItem(R.id.action_loadImg).setIcon(R.drawable.ic_visibility_white_24dp);
        } else {
            menu.findItem(R.id.action_loadImg).setIcon(R.drawable.ic_visibility_off_white_24dp);
        }
        return true;
    }

    //菜单选择事件
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
                SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
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
            case R.id.action_loadImg:
                //更改是否显示图片
                BaseApplication.setIsLoadImg(!BaseApplication.getIsLoadImg());
                SharedPreferences sp1 = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                editor1.putBoolean("isLoadImg", BaseApplication.getIsLoadImg());
                editor1.commit();
                if (BaseApplication.getIsLoadImg()) {
                    item.setIcon(R.drawable.ic_visibility_white_24dp);
                }else{
                    item.setIcon(R.drawable.ic_visibility_off_white_24dp);
                }
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

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            onBackPressed();
        }else{
            ThemeFragment fragment = new ThemeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title",themesMenuBean.getOthers().get(position).getName());
            bundle.putInt("id",themesMenuBean.getOthers().get(position).getId());
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_layout,fragment);
            transaction.addToBackStack("fragment");
            transaction.commit();
            closeDrawer();
        }
    }
}
