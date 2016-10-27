package com.gentrio.zhrb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gentrio.zhrb.R;
import com.gentrio.zhrb.app.BaseApplication;
import com.gentrio.zhrb.bean.ThemesBean;
import com.gentrio.zhrb.ui.activity.WebActivity;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by Gentrio on 2016/10/26.
 */
public class ThemeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context context;
    private ThemesBean data;
    private final int TOP_VIEW = 0;
    private final int ITEM_VIEW = 1;
    private LayoutInflater inflater;

    public ThemeListAdapter(Context context,ThemesBean data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOP_VIEW) {
            View view = inflater.inflate(R.layout.item_topvp,parent,false);
            TopViewHolder holder = new TopViewHolder(view);
            return holder;
        }else{
            View view = inflater.inflate(R.layout.item_card, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder tHolder = (TopViewHolder) holder;
            if (BaseApplication.getIsLoadImg()) {
                Picasso.with(context).load(data.getBackground()).placeholder(R.drawable.image_top_default).into(tHolder.background);
            }else{
                Picasso.with(context).load(data.getBackground()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_top_default).into(tHolder.background);
            }
            tHolder.description.setText(data.getDescription());
        }else{
            ItemViewHolder iHolder = (ItemViewHolder) holder;
            iHolder.cardView.setTag(data.getStories().get(position).getId());
            iHolder.title.setText(data.getStories().get(position).getTitle());
            if (data.getStories().get(position).getImages() != null) {
                if (BaseApplication.getIsLoadImg()){
                    Picasso.with(context).load(data.getStories().get(position).getImages().get(0)).placeholder(R.drawable.image_small_default).into(iHolder.images);
                }else{
                    Picasso.with(context).load(data.getStories().get(position).getImages().get(0)).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.image_small_default).into(iHolder.images);
                }
            }else{
                iHolder.img_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP_VIEW;
        }else{
            return ITEM_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        return data.getStories().size();
    }

    static class TopViewHolder extends RecyclerView.ViewHolder{

        ImageView background;
        TextView description;

        public TopViewHolder(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.vp_iv);
            description = (TextView) itemView.findViewById(R.id.vp_tv);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        ImageView images;
        ImageView home;
        View img_layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            images = (ImageView) itemView.findViewById(R.id.images);
            home = (ImageView) itemView.findViewById(R.id.home);
            img_layout = itemView.findViewById(R.id.img_layout);
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
}
