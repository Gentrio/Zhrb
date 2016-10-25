package com.gentrio.zhrb.ui.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gentrio.zhrb.R;
import com.gentrio.zhrb.app.BaseApplication;
import com.gentrio.zhrb.bean.NewsBean;
import com.gentrio.zhrb.service.DbService;
import com.gentrio.zhrb.service.Service;
import com.gentrio.zhrb.service.ServiceClient;
import com.gentrio.zhrb.service.SimpleCallBack;
import com.gentrio.zhrb.utils.DbHelper;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WebActivity extends BaseActivity {

    private WebView webView;
    private String id;
    private NewsBean data;
    private DbService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initVariable();
        initView();
        getData();
    }

    private void getData() {
        Service service = ServiceClient.getService();
        service.getNews(id).enqueue(new SimpleCallBack<NewsBean>() {
            @Override
            public void onResponse(NewsBean result, int code, String msg) {
                data = result;
                String body;
                //通过更改<body>的CLASS属性来设置夜间模式
                if (BaseApplication.getIsNight()) {
                    body = "<body class=\"night\">";
                }else{
                    body = "<body>";
                }
                //body体
                String content = result.getBody()+"</body>";
                //CSS样式
                String css = "<head><link rel=\"stylesheet\" href="+result.getCss().get(0)+" type=\"text/css\" /></head>";
                webView.loadDataWithBaseURL("about:blank", body+css+content, "text/html", "utf-8", null);
            }
        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }

    private void initVariable() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        dbService = new DbService(WebActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            //收藏按钮
            case R.id.action_star:
                if (data != null) {
                    boolean result;
                    if (!dbService.getOne(data.getId())){
                        result = dbService.insert(data.getId(), data.getTitle(), data.getImages().get(0));
                        if (result) {
                            item.setIcon(R.drawable.ic_star_yellow_900_24dp);
                        }
                    }else{
                        result = dbService.delete(data.getId());
                        if (result) {
                            item.setIcon(R.drawable.ic_star_white_24dp);
                        }
                    }
                }
                return true;
            case R.id.action_thumb:
//                通过JS来改变body的class属性
//                if (BaseApplication.getIsNight()) {
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                        webView.evaluateJavascript("document.body.className='night'", null);
//                    } else {
//                        webView.loadUrl("javascript:document.body.className='night';");
//                    }
//                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        //判断是否已经收藏
        if (dbService.getOne(Integer.parseInt(id))) {
            menu.findItem(R.id.action_star).setIcon(R.drawable.ic_star_yellow_900_24dp);
        }else{
            menu.findItem(R.id.action_star).setIcon(R.drawable.ic_star_white_24dp);
        }
        return true;
    }
}
