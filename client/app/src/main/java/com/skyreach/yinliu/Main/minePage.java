package com.skyreach.yinliu.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.skyreach.yinliu.R;

import androidx.appcompat.app.AppCompatActivity;


public class minePage extends AppCompatActivity {

    private Button mbtnLoginout;
    private Button mbtnUserManage;
    private TextView t_nickName;
    private TextView t_e_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_page);
        mbtnUserManage = findViewById(R.id.inforBtn);
        mbtnLoginout = findViewById(R.id.loginOut);
        t_nickName = findViewById(R.id.nickName);
        t_e_address = findViewById(R.id.e_address);
        t_nickName.setText(Main.root_user.getUserName());
        t_e_address.setText(Main.root_user.getEmail());

        mbtnUserManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(minePage.this, userManagePage.class);
                startActivity(intent);
            }
        });

        mbtnLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(minePage.this, loginPage.class);
                startActivity(intent);
            }
        });

    }
}
