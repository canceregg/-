package com.example.skyreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyreach.Ad.Ad_Message;
import com.example.skyreach.Brvah.BaseQuickAdapter;
import com.example.skyreach.Ob.EOne;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.connect.tool.Post;
import com.example.skyreach.connect.work.PostIF;
import com.example.skyreach.tkrefreshlayout.IHeaderView;
import com.example.skyreach.tkrefreshlayout.RefreshListenerAdapter;
import com.example.skyreach.tkrefreshlayout.TwinklingRefreshLayout;
import com.example.skyreach.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class messagePage extends AppCompatActivity {

    Ad_Message ad_message;

    List<Post> obPlates;
    @BindView(R.id.fight)
    TextView fight;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.megs)
    RecyclerView megs;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @BindView(R.id.zero)
    RelativeLayout zero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_message);
        ButterKnife.bind(this);
        View();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh.startRefresh();
    }

    private void ZERO() {
        if (obPlates.size() == 0) {
            zero.setVisibility(View.VISIBLE);
            megs.setVisibility(View.GONE);
        } else {
            zero.setVisibility(View.GONE);
            megs.setVisibility(View.VISIBLE);
        }
    }

    private void View() {
        obPlates=new ArrayList<>();
        ad_message = new Ad_Message(obPlates);

        IHeaderView headerView=new ProgressLayout(this);
        refresh.setHeaderView(headerView);

        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EOne("ps",PostIF.getInstance().getAllInfByUserId(new ShareP(messagePage.this).getString("uid"))));
                    }
                }).start();
            }
        });

        ad_message.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                Post post=obPlates.get(position);
                switch (view.getId()) {
                    case R.id.m_title:
                        Intent intent = new Intent(messagePage.this, articlePage.class);
                        intent.putExtra("post",post);
                        startActivity(intent);
                        break;
                    case R.id.m_content:
                        Intent intents = new Intent(messagePage.this, articlePage.class);
                        intents.putExtra("post",post);
                        startActivity(intents);
                        break;
                    case R.id.delete:
                        new AlertDialog.Builder(messagePage.this)
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
                                        PostIF.getInstance().delPost(post.getPostId(), post.getBoardId());


                                        ad_message.remove(position);
                                        ZERO();
                                        TShow.s("已清除");
                                    }
                                })
                                .create().show();

                        break;
                }
            }
        });
        megs.setLayoutManager(new LinearLayoutManager(this));
        megs.setAdapter(ad_message);
        ZERO();

        refresh.startRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eOne(EOne eone) {
        refresh.finishRefreshing();
        if (eone.getFight().equals("ps")) {
            if (eone.getObject() instanceof List) {
                obPlates.clear();
                obPlates.addAll((List<Post>) eone.getObject());

                ad_message.notifyDataSetChanged();
                ZERO();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
