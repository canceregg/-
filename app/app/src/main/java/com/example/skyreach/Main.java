package com.example.skyreach;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.skyreach.Tool.TBar;
import com.example.skyreach.Tool.UtilPer;
import com.example.skyreach.connect.tool.Board;
import com.example.skyreach.connect.tool.Comment;
import com.example.skyreach.connect.tool.Post;
import com.example.skyreach.connect.tool.User;

import java.util.ArrayList;
import java.util.List;


public class Main extends TabActivity implements View.OnClickListener{
    //定义Tab选项卡标示符
    private static final String One = "one";
    private static final String Two = "two";
    private static final String Three = "three";
    public static List<Comment> clickComment;//当前点击帖子的评论

    //定义Intent对象
    private Intent intentOne,intentTwo,intentThree;

    //定义TabHost对象
    private TabHost tabHost;

    private LinearLayout tab1,tab2,tab3;

    ImageView image1;

    ImageView image2;

    ImageView image3;


    TextView text1;

    TextView text2;

    TextView text3;

    ShareP shareP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        TBar.compat(this);
        UtilPer.Per(Main.this,new Handler());
        shareP=new ShareP(this);
        View();
        Data();
    }
    private void View(){
        tabHost = getTabHost();

        intentOne = new Intent(this,interestPage.class);
        intentTwo = new Intent(this, messagePage.class);
        intentThree = new Intent(this, minePage.class);

        tab1=(LinearLayout) findViewById(R.id.tab1);
        tab2=(LinearLayout) findViewById(R.id.tab2);
        tab3=(LinearLayout) findViewById(R.id.tab3);

        image1=(ImageView)findViewById(R.id.image1);
        image2=(ImageView)findViewById(R.id.image2);
        image3=(ImageView)findViewById(R.id.image3);

        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);


        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */


    private void Data(){

        tabHost.addTab(buildTabSpec(One, intentOne));
        tabHost.addTab(buildTabSpec(Two, intentTwo));
        tabHost.addTab(buildTabSpec(Three, intentThree));
        tabHost.setCurrentTabByTag(One);

        image(1);
    }
    private void image(int pos){
        TextView textView[]=new TextView[]{text1,text2,text3};
        ImageView imageView[]=new ImageView[]{image1,image2,image3};
        for(int i=0;i<3;i++){
            textView[i].setTextColor(getResources().getColor(R.color.no));
            imageView[i].setSelected(false);
        }
        textView[pos-1].setTextColor(getResources().getColor(R.color.yes));
        imageView[pos-1].setSelected(true);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tab1:
                image(1);
                tabHost.setCurrentTabByTag(One);
                break;
            case R.id.tab2:
                tabHost.setCurrentTabByTag(Two);
                image(2);
                break;
            case R.id.tab3:
                image(3);
                tabHost.setCurrentTabByTag(Three);
                break;
        }
    }
    private TabHost.TabSpec buildTabSpec(String tag, Intent intent) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setContent(intent).setIndicator("");

        return tabSpec;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
