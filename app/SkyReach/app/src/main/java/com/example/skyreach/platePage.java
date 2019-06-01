package com.example.skyreach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyreach.Ad.Ad_Plate;
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
import butterknife.OnClick;


public class platePage extends AppCompatActivity {


    Ad_Plate ad_plate;
    List<Post> obPlates;
    @BindView(R.id.plateName)
    TextView plateName;
    @BindView(R.id.pla)
    RecyclerView pla;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;

    private String bid, bname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_plate);
        ButterKnife.bind(this);
        bid = getIntent().getStringExtra("bid");
        bname = getIntent().getStringExtra("bname");
        plateName.setText(bname);
        View();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            refresh.startRefresh();
        }
    }

    private void View() {
        IHeaderView headerView = new ProgressLayout(this);
        refresh.setHeaderView(headerView);

        obPlates = new ArrayList<>();
        ad_plate = new Ad_Plate(obPlates);
        ad_plate.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                if (view.getId() == R.id.del) {
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

                                    TShow.s("帖子已删除");
                                    Post post = obPlates.get(position);
                                    PostIF.getInstance().delPost(post.getPostId(), bid);

                                    ad_plate.remove(position);
                                }
                            })
                            .create().show();
                } else {
                    Post post = obPlates.get(position);
                    PostIF.getInstance().increaseClickCnt(post.getPostId());
                    Intent intent = new Intent(platePage.this, articlePage.class);
                    intent.putExtra("post", post);
                    startActivity(intent);
                }
            }
        });
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EOne("ps", PostIF.getInstance().getAllInfByBoardId(bid)));
                    }
                }).start();
            }
        });
        pla.setLayoutManager(new LinearLayoutManager(this));
        pla.setAdapter(ad_plate);

        refresh.startRefresh();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eOne(EOne eone) {
        refresh.finishRefreshing();
        if (eone.getFight().equals("ps")) {

            if (eone.getObject() instanceof List) {

                obPlates.clear();
                obPlates.addAll((List<Post>) eone.getObject());

                ad_plate.notifyDataSetChanged();
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

    @OnClick({R.id.write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.write:
                Intent intents = new Intent(platePage.this, Ac_Write_Plate.class);
                intents.putExtra("bid", bid);
                startActivityForResult(intents, 1);
                break;
        }
    }
}
