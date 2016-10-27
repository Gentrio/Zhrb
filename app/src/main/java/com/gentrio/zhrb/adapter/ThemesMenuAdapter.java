package com.gentrio.zhrb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gentrio.zhrb.R;
import com.gentrio.zhrb.bean.ThemesMenuBean;

/**
 * Created by Gentrio on 2016/10/27.
 */
public class ThemesMenuAdapter extends RecyclerView.Adapter<ThemesMenuAdapter.ViewHolder> {

    private ThemesMenuBean themesMenuBean;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ThemesMenuAdapter(Context context, ThemesMenuBean themesMenuBean) {
        this.themesMenuBean = themesMenuBean;
        this.themesMenuBean.getOthers().add(0,new ThemesMenuBean.OthersBean("首页"));
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.menu_text.setTextColor(Color.rgb(244,126,23));
            holder.home_icon.setVisibility(View.VISIBLE);
        }
        holder.menu_text.setText(themesMenuBean.getOthers().get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return themesMenuBean.getOthers().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView home_icon;
        TextView menu_text;

        public ViewHolder(View itemView) {
            super(itemView);
            home_icon = (ImageView) itemView.findViewById(R.id.home_icon);
            menu_text = (TextView) itemView.findViewById(R.id.menu_text);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
