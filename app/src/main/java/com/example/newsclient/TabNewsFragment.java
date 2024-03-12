package com.example.newsclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsclient.adapter.NewsListAdapter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TabNewsFragment extends Fragment {

    //550d0e58320fe65ffde6537c645a2d53
    //b6527106fa4e66a226b5b923d2a8b711
    private String url="http://v.juhe.cn/toutiao/index?key=550d0e58320fe65ffde6537c645a2d53&type=";

    private View rootView;
    private static final String ARG_PARAM = "title";
    private String title;
    private NewsListAdapter mNewsListAdapter;
    private RecyclerView recyclerView;
    private Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                String data = (String)msg.obj;
                NewsInfo newsInfo=new Gson().fromJson(data,NewsInfo.class);
                if(newsInfo!=null && newsInfo.getError_code()==0){
                    //做逻辑处理
                    if(mNewsListAdapter!=null){
                        mNewsListAdapter.setListData(newsInfo.getResult().getData());
                    }
                }else{
                    Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public TabNewsFragment() {

    }


    public static TabNewsFragment newInstance(String param) {
        TabNewsFragment fragment = new TabNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_table_news,container,false);
        //初始化控件
        recyclerView= rootView.findViewById(R.id.recycleView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化适配器
        mNewsListAdapter=new NewsListAdapter(getActivity());

        //设置Adapter
        recyclerView.setAdapter(mNewsListAdapter);

        //recycleView列表点击事件
        mNewsListAdapter.setmOnItemClickListener(new NewsListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(NewsInfo.ResultDTO.DataDTO dataDTO, int position) {
                //跳转到详情页
                Intent intent=new Intent(getActivity(), NewsDetails.class);
                //传递对象的时候，该类一定要实现Serializable
                intent.putExtra("dataDTO",dataDTO);
                startActivity(intent);
            }
        });

        //获取数据
        GetHttpData();
    }

    private void GetHttpData(){
        //创建OKHttpClient对象
        OkHttpClient okHttpClient=new OkHttpClient();
        //构造request对象
        Request request = new Request.Builder()
                .url(url+title)
                .get()
                .build();
        //通过OKhttpClient和Request构建Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("----------","fail:"+e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
//                Log.d("---------","onResponse"+data);
                //不能在耗时操作里面更新UI,那么需要使用handler
                Message message=new Message();
                message.what=100;//给个标识，说明从哪个请求过来的
                message.obj=data;
                //发送到handler里面去
                mHandler.sendMessage(message);
            }
        });

    }
}