package com.example.newsclient;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsDetails extends AppCompatActivity {
    private int i=0;
    private ImageView like;
    private NewsInfo.ResultDTO.DataDTO dataDTO;
    private WebView mWebView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        //初始化控件
        toolbar=findViewById(R.id.toolbar);
        mWebView= findViewById(R.id.webView);

        //获取传递数据
        dataDTO= (NewsInfo.ResultDTO.DataDTO) getIntent().getSerializableExtra("dataDTO");

        //设置数据
        if(dataDTO!=null){
            toolbar.setTitle(dataDTO.getTitle());
            mWebView.loadUrl(dataDTO.getUrl());
        }

        //返回
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //点赞与评论
        final ImageView like=findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                i=i%2;
                if(i==1){
                    like.setImageResource(R.drawable.like);
                }else{
                    like.setImageResource(R.drawable.nolike);
                }
            }
        });

        EditText comment=findViewById(R.id.comment);
        TextView showcomment=findViewById(R.id.showcomment);
        Button btn=findViewById(R.id.yes);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"评论不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    showcomment.setText("我: "+comment.getText());
                }
            }
        });


    }

}