package com.example.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsclient.NewsInfo;
import com.example.newsclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 作者：吴蓓
 * 日期：2023年12月14日
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyHolder> {
    private List<NewsInfo.ResultDTO.DataDTO> mDataDTOList=new ArrayList<>();
    private Context mContext;
    public NewsListAdapter(Context context){
        this.mContext=context;
    }

    public void setListData(List<NewsInfo.ResultDTO.DataDTO> list){
        this.mDataDTOList=list;

        //这句话不能少，一定要调用
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载布局文件
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,int position) {
        //绑定数据
        NewsInfo.ResultDTO.DataDTO dataDTO=mDataDTOList.get(position);
        //设置数据
        holder.date.setText(dataDTO.getDate());
        holder.author_name.setText(dataDTO.getAuthor_name());
        holder.title.setText(dataDTO.getTitle());
        //加载图片
        Glide.with(mContext).load(dataDTO.getThumbnail_pic_s()).error(R.drawable.img_3).into(holder.thumbnail_pic_s);

        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(dataDTO,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataDTOList.size();
    }

    static  class MyHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail_pic_s;
        TextView title;
        TextView date;
        TextView author_name;
        public MyHolder(@NonNull View itemView){
            super(itemView);
            thumbnail_pic_s=itemView.findViewById(R.id.thumbnail_pic_s);
            title=itemView.findViewById(R.id.title);
            author_name=itemView.findViewById(R.id.author_name);
            date=itemView.findViewById(R.id.date);

        }
    }

    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(NewsInfo.ResultDTO.DataDTO dataDTO,int position);
    }

}
