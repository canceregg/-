package com.example.skyreach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.skyreach.Ad.Ad_Plate;
import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Ob.ObPlate;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.work.PostIF;

import java.util.List;


public class platePage extends AppCompatActivity {

    private TextView boardName;
    private ImageView writes;

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
        writes = findViewById(R.id.write);
        writes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(platePage.this, Ac_Write_Plate.class);
                startActivityForResult(intents,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&data!=null){
            ObPlate post=(ObPlate) data.getSerializableExtra("plate");
            ad_plate.add(0,post);
        }
    }

    private void View(){
        pla=(RecyclerView)findViewById(R.id.pla);
        plaHead=(View) LayoutInflater.from(this).inflate(R.layout.pla_head,null,true);
        obPlates=TData.getPlate();
        ad_plate=new Ad_Plate(obPlates);
        ad_plate.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                if(view.getId()==R.id.del){
                    new AlertDialog.Builder(platePage.this)
                            .setMessage("确定清除这个帖子嘛?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Main.root_board_postlist.remove(position);
                                    ad_plate.remove(position);
                                    TShow.s("帖子已删除");
                                    Main.clickPost = Main.root_board_postlist.get(position);
                                    PostIF.getInstance().delPost(Main.clickPost.getPostId(),Main.clickPost.getBoardId());
                                }
                            })
                            .create().show();
                }else{
                    Main.clickPost = Main.root_board_postlist.get(position);
                    PostIF.getInstance().increaseClickCnt(Main.clickPost.getPostId());
                    Intent intent = new Intent(platePage.this,articlePage.class);
                    startActivity(intent);
                }
            }
        });

        ad_plate.setHeaderView(plaHead);
        pla.setLayoutManager(new LinearLayoutManager(this));
        pla.setAdapter(ad_plate);
    }
}
