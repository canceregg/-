package com.skyreach.yinliu.Main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skyreach.yinliu.Main.connect.tool.User;
import com.skyreach.yinliu.Main.connect.work.LoginIF;
import com.skyreach.yinliu.R;


public class userManagePage extends AppCompatActivity {

    private Button PC;
    private EditText oP;
    private EditText nP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage_page);
        oP = findViewById(R.id.m_oldPassword);
        nP = findViewById(R.id.m_newPassword);
        PC = findViewById(R.id.passwordChange);
        PC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldP = oP.getText().toString();
                String newP = nP.getText().toString();
                Boolean res = LoginIF.getInstance().updatePassword(Main.root_user.getEmail(), oldP, newP);
                if(res){
                    Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_LONG).show();
                    for(User user : LoginIF.getInstance().getAllInf()){
                        if(user.getEmail().equals(Main.root_user.getEmail())){
                            Main.root_user.setUserPassword(newP);
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"密码输入错误！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
