package com.example.skyreach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.skyreach.Ad.Ad_Interest;
import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Ob.ObInterest;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.tool.Board;
import com.example.skyreach.connect.work.BoardIF;
import com.example.skyreach.connect.work.PostIF;
import com.example.skyreach.tkrefreshlayout.IHeaderView;
import com.example.skyreach.tkrefreshlayout.RefreshListenerAdapter;
import com.example.skyreach.tkrefreshlayout.TwinklingRefreshLayout;
import com.example.skyreach.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.List;


public class interestPage extends AppCompatActivity {




    RecyclerView ins;
    Ad_Interest ad_interest;
    List<ObInterest> obInterests;

    View insHead;

    TwinklingRefreshLayout refresh;

    ImageView write;
    ShareP shareP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_interest);
        shareP=new ShareP(this);
        View();
        Manager();
    }
    private void Manager(){
        if(shareP.getBoolean("manager")){
            write.setVisibility(View.VISIBLE);
        }else{
            write.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&data!=null){
            ObInterest obInterest=(ObInterest)data.getSerializableExtra("interest");
            ad_interest.add(0,obInterest);
        }
    }

    private void View(){
        ins=(RecyclerView)findViewById(R.id.ins);
        write=findViewById(R.id.write);
        insHead=(View) LayoutInflater.from(this).inflate(R.layout.ins_head,null,true);
        refresh=findViewById(R.id.refresh);
        IHeaderView headerView=new ProgressLayout(this);
        refresh.setHeaderView(headerView);
        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(interestPage.this,Ac_Write_Interest.class),2);
            }
        });
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },1000);
            }
        });
        obInterests=TData.getInterest();

        ad_interest=new Ad_Interest(obInterests);
        ad_interest.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if(view.getId()==R.id.del) {
                    new AlertDialog.Builder(interestPage.this)
                            .setMessage("确定要清除这个板块嘛？")
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
                                    ad_interest.remove(position);
                                    Board board=Main.root_boardlist.get(position);
                                    Main.root_boardlist.remove(position);
                                    BoardIF.getInstance().delBoard(board.getBoardId());
                                    TShow.s("板块已清除");
                                }
                            })
                            .create().show();
                }else{
                    Main.root_board_postlist = PostIF.getInstance().getAllInfByBoardId(Main.root_boardlist.get(position).getBoardId());
                    Main.clickBoard = Main.root_boardlist.get(position);
                    BoardIF.getInstance().increaseClickCnt(Main.clickBoard.getBoardId());
                    Intent intent = new Intent(interestPage.this,platePage.class);
                    startActivity(intent);
                }

            }
        });

        ad_interest.setHeaderView(insHead);
        ins.setLayoutManager(new LinearLayoutManager(this));
        ins.setAdapter(ad_interest);
    }
}
