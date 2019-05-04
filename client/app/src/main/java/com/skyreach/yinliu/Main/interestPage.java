package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.skyreach.yinliu.Main.Ad.Ad_Interest;
import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Ob.ObInterest;
import com.skyreach.yinliu.Main.connect.work.BoardIF;
import com.skyreach.yinliu.Main.connect.work.PostIF;
import com.skyreach.yinliu.R;

import java.util.List;


public class interestPage extends AppCompatActivity {




    RecyclerView ins;
    Ad_Interest ad_interest;
    List<ObInterest> obInterests;

    View insHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_interest);
        View();
    }
    private void View(){
        ins=(RecyclerView)findViewById(R.id.ins);
        insHead=(View) LayoutInflater.from(this).inflate(R.layout.ins_head,null,true);

        obInterests=TData.getInterest();

        ad_interest=new Ad_Interest(obInterests);
        ad_interest.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Main.root_board_postlist = PostIF.getInstance().getAllInfByBoardId(Main.root_boardlist.get(position).getBoardId());
                Main.clickBoard = Main.root_boardlist.get(position);
                BoardIF.getInstance().increaseClickCnt(Main.clickBoard.getBoardId());
                Intent intent = new Intent(interestPage.this,platePage.class);
                startActivity(intent);
            }
        });

        ad_interest.setHeaderView(insHead);
        ins.setLayoutManager(new LinearLayoutManager(this));
        ins.setAdapter(ad_interest);
    }
}
