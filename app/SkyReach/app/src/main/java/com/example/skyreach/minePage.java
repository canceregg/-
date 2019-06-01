package com.example.skyreach;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.Tool.DB;
import com.example.skyreach.Tool.FileUtil;
import com.example.skyreach.Tool.TShow;
import com.example.skyreach.Tool.UtilImage;
import com.xiaomi.mimc.MIMCUser;
import com.yalantis.ucrop.UCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class minePage extends AppCompatActivity {

    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.e_address)
    TextView eAddress;
    @BindView(R.id.inforBtn)
    Button inforBtn;
    @BindView(R.id.loginOut)
    Button loginOut;

    private ShareP shareP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_page);
        ButterKnife.bind(this);
        shareP = new ShareP(this);
        nickName.setText(shareP.getString("name"));
        eAddress.setText(shareP.getString("email"));
    }
    class CropHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    head.setImageBitmap(BitmapFactory.decodeFile(UtilImage.getPath("head", "head.jpg")));
                    TShow.s("success");
                    break;
                case 2:
                    TShow.s("fail");
                    break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            try {
                String name = "head.jpg";
                UtilImage.compress(this, FileUtil.getTempFile(this, resultUri), "head", name, new CropHandler());
            } catch (Exception e) {
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {

            final Throwable cropError = UCrop.getError(data);
            TShow.s("erro:"+cropError.toString());
        }
        switch (requestCode){
            case 1://head
                if (data != null) {
                    UCrop.of(data.getData(), Uri.parse(UtilImage.getPath() + "/loveuhead.jpg"))
                            .withAspectRatio(1, 1)
                            .start(minePage.this);
                }
                break;
            case 2:
                if (data != null) {
                    nickName.setText(data.getStringExtra("name"));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.head, R.id.nickName, R.id.e_address, R.id.inforBtn, R.id.loginOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head:
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                /**
                 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.images
                 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
                 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
                 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                 */
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent2, 1);
                break;
            case R.id.nickName:
                startActivityForResult(new Intent(this, ChangeName.class), 2);
                break;
            case R.id.e_address:
                break;
            case R.id.inforBtn:
                startActivity(new Intent(minePage.this, userManagePage.class));
                break;
            case R.id.loginOut:
                shareP.clear();
                UserManager userManager = UserManager.getInstance();
                MIMCUser user = userManager.getUser();
                if (user != null){
                    user.logout();
                }
                DB db=new DB(minePage.this,DB.DB_NAME);
                db.delAll();
                finish();
                break;
        }
    }
}
