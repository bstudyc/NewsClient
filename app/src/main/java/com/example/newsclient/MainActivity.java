package com.example.newsclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CntoEn> titles=new ArrayList<>();
    private TabLayout tab_layout;
    private ViewPager2 viewPager;
    private ImageButton person;

    private Banner banner;
    private List<BannerDateInfo> mBannerDateInfos = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //初始化titles数据
        titles.add(new CntoEn("头条","top"));
        titles.add(new CntoEn("娱乐","yule"));
        titles.add(new CntoEn("热点","guonei"));
        titles.add(new CntoEn("体育","tiyu"));
        titles.add(new CntoEn("军事","junshi"));
        titles.add(new CntoEn("科技","keji"));
        titles.add(new CntoEn("健康","jiankang"));


        tab_layout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager);
        person=findViewById(R.id.person);
        banner = findViewById(R.id.banner);

        mBannerDateInfos.add(new BannerDateInfo(R.drawable.slide1));
        mBannerDateInfos.add(new BannerDateInfo(R.drawable.slide2));
        mBannerDateInfos.add(new BannerDateInfo(R.drawable.slide3));
        mBannerDateInfos.add(new BannerDateInfo(R.drawable.slide4));
        mBannerDateInfos.add(new BannerDateInfo(R.drawable.slide5));

        //person点击事件
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,person.class);
                startActivity(intent);
            }
        });

        //viewPager需要一个适配器
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //根据位置取出对应的页面中文标题
                String title=titles.get(position).getPy_title();
                //利用Instance给碎片传入信息，确保传递给碎片的信息不会丢失
                //如果使用碎片构造函数给碎片传递信息如果旋转屏幕会发生什么？活动的销毁与创建与此对应碎片也会销毁与创建，系统会自动帮忙创建一个碎片，但在碎片创建的时候只会调用碎片的无参构造函数，此时你向碎片传递的信息就会丢失
                TabNewsFragment tabNewsFragment = TabNewsFragment.newInstance(title);
                return tabNewsFragment;
            }

            @Override
            public int getItemCount() {
                return titles.size();
            }
        });

        //tab_layout点击事件
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //设置viewpager选中当前页
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //tab_layout和viewpager关联在一起
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tab_layout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position).getTitle());
            }
        });

        //这句一定不能少
        tabLayoutMediator.attach();


        banner.setAdapter(new BannerImageAdapter<BannerDateInfo>(mBannerDateInfos) {
                    @Override
                    public void onBindView(BannerImageHolder holder,BannerDateInfo data,int position,int size) {
                        //设置数据
                        holder.imageView.setImageResource(data.getImg());
                    }
                })
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));


        //banner点击事件触发
        banner.setOnBannerListener(new OnBannerListener<BannerDateInfo>() {
            @Override
            public void OnBannerClick(BannerDateInfo data, int position) {
                if(position==0){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,slide1.class);
                    startActivity(intent);
                }else if(position==1){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,slide2.class);
                    startActivity(intent);
                }else if(position==2){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,slide3.class);
                    startActivity(intent);
                }else if(position==3){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,slide4.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,slide5.class);
                    startActivity(intent);
                }
            }
        });

    }
}