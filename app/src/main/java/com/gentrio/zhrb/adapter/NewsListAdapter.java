package com.gentrio.zhrb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gentrio.zhrb.R;
import com.gentrio.zhrb.bean.LatestBean;
import com.gentrio.zhrb.ui.activity.WebActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gentrio on 2016/10/21.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LatestBean datas;
    private final int TOP_VIEW = 0;
    private final int ITEM_VIEW = 1;
    private LayoutInflater inflater;

    public NewsListAdapter(Context context, LatestBean datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (viewType == TOP_VIEW) {
            itemView = inflater.inflate(R.layout.top_vp, parent, false);
            final TopViewHolder holder = new TopViewHolder(itemView);
            List<View> topViews = new ArrayList<View>();
            final int storySize = datas.getTop_stories().size();
            for (int i=0; i<storySize;i++) {
                View view = inflater.inflate(R.layout.item_topvp, null);
                ImageView vp_iv = (ImageView) view.findViewById(R.id.vp_iv);
                TextView vp_tv = (TextView) view.findViewById(R.id.vp_tv);
                Picasso.with(context).load(datas.getTop_stories().get(i).getImage()).into(vp_iv);
                vp_tv.setText(datas.getTop_stories().get(i).getTitle());
                view.setTag(datas.getTop_stories().get(i).getId());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("id", v.getTag().toString());
                        context.startActivity(intent);
                    }
                });
                topViews.add(view);
            }
            TopPagerAdapter adapter = new TopPagerAdapter(topViews);
            holder.vp.setAdapter(adapter);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.vp.setCurrentItem((holder.vp.getCurrentItem()+1) % storySize);
                    handler.postDelayed(this, 6000);
                }
            }, 6000);
            return holder;
        }else{
            itemView = inflater.inflate(R.layout.item_card, parent, false);
            ItemViewHolder holder = new ItemViewHolder(itemView);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder tHolder = (TopViewHolder) holder;
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder iHolder = (ItemViewHolder) holder;
            iHolder.cardView.setTag(datas.getStories().get(position).getId());
            iHolder.title.setText(datas.getStories().get(position).getTitle());
            Picasso.with(context).load(datas.getStories().get(position).getImages().get(0)).into(iHolder.images);
        }
    }

    @Override
    public int getItemCount() {
        return datas.getStories().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP_VIEW;
        }else{
            return ITEM_VIEW;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        ImageView images;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            images = (ImageView) itemView.findViewById(R.id.images);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("id", v.getTag().toString());
                    context.startActivity(intent);
                }
            });
        }
    }


    class TopViewHolder extends RecyclerView.ViewHolder{

        ViewPager vp;

        public TopViewHolder(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.vp);
        }
    }

}
