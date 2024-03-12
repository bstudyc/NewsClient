package com.example.newsclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 功能：
 * 作者：吴蓓
 * 日期：2023年12月12日
 */
public class slide2 extends AppCompatActivity {
    private int i=0;
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide2);

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
