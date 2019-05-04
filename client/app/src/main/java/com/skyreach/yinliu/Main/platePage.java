package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.skyreach.yinliu.Main.Ad.Ad_Plate;
import com.skyreach.yinliu.Main.Brvah.BaseQuickAdapter;
import com.skyreach.yinliu.Main.Ob.ObPlate;
import com.skyreach.yinliu.Main.connect.work.PostIF;
import com.skyreach.yinliu.R;

import java.util.List;


public class platePage extends AppCompatActivity {

    private TextView boardName;

    RecyclerView pla;
    Ad_Plate ad_plate;
    List<ObPlate> obPlates;

    View plaHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_plate);
        View();
        boardName = plaHead.findViewById(R.id.plateName);
        boardName.setText(Main.clickBoard.getBoardName());
    }
    private void View(){
        pla=(RecyclerView)findViewById(R.id.pla);
        plaHead=(View) LayoutInflater.from(this).inflate(R.layout.pla_head,null,true);
        obPlates=TData.getPlate();
        ad_plate=new Ad_Plate(obPlates);
        ad_plate.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Main.clickPost = Main.root_board_postlist.get(position);
                PostIF.getInstance().increaseClickCnt(Main.clickPost.getPostId());
                Intent intent = new Intent(platePage.this,articlePage.class);
                startActivity(intent);
            }
        });

        ad_plate.setHeaderView(plaHead);
        pla.setLayoutManager(new LinearLayoutManager(this));
        pla.setAdapter(ad_plate);
    }
}
