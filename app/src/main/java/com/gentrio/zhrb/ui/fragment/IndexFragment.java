package com.gentrio.zhrb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gentrio.zhrb.R;
import com.gentrio.zhrb.adapter.NewsListAdapter;
import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.service.Service;
import com.gentrio.zhrb.service.ServiceClient;
import com.gentrio.zhrb.service.SimpleCallBack;

import java.util.ArrayList;

/**
 * Created by Gentrio on 2016/10/26.
 */
public class IndexFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_refresh;
    private LatestBean data;
    private NewsListAdapter adapter;
    private Service service;
    private boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        initVariable();
        initView(view);
        getData();
        return view;
    }

    private void initVariable() {
        data = new LatestBean(new String(),new ArrayList<LatestBean.StoriesBean>(),new ArrayList<LatestBean.TopStoriesBean>());
        service = ServiceClient.getService();
    }

    private void initView(View view) {

        getActivity().setTitle(R.string.main_name);

        swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);

        //设置下拉刷新监听器
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        adapter = new NewsListAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
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


}
