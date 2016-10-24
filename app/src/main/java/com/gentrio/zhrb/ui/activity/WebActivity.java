package com.gentrio.zhrb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.gentrio.zhrb.R;
import com.gentrio.zhrb.bean.NewsBean;
import com.gentrio.zhrb.service.Service;
import com.gentrio.zhrb.service.ServiceClient;
import com.gentrio.zhrb.service.SimpleCallBack;

public class WebActivity extends BaseActivity {

    private WebView webView;
    private String id;

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
                String content = result.getBody();
                String css = "<link rel=\"stylesheet\" href="+result.getCss().get(0)+" type=\"text/css\" />";
                webView.loadDataWithBaseURL("about:blank", css+content, "text/html", "utf-8", null);
            }
        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }

    private void initVariable() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        return true;
    }
}
