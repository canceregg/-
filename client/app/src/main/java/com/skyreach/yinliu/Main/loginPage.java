package com.skyreach.yinliu.Main;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.skyreach.yinliu.Main.connect.tool.User;
import com.skyreach.yinliu.R;
import com.skyreach.yinliu.Main.connect.work.*;


public class loginPage extends AppCompatActivity {

    private Button mbtnLogin;
    private Button mbtnLogon;
    private EditText loginAccount;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginAccount = findViewById(R.id.editText2);
        loginPassword = findViewById(R.id.editText3);

        mbtnLogon = findViewById(R.id.button2);
        mbtnLogin = findViewById(R.id.button);
        mbtnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginPage.this, logonPage.class);
                startActivity(intent);
            }
        });
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accoutEmail = loginAccount.getText().toString();
                String password = loginPassword.getText().toString();
                if(accoutEmail.equals("")||password.equals("")){
                    Toast.makeText(getApplicationContext(), "所有输入不能为空！", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean res = LoginIF.getInstance().check(accoutEmail, password);
                    if(res){
                        for(User user : LoginIF.getInstance().getAllInf()){
                            if(user.getEmail().equals(accoutEmail)){
                                Main.root_user = user;
                            }
                        }
                        Intent intent=new Intent(loginPage.this, Main.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"账号或密码输入有误！",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
