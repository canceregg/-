package com.example.skyreach;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.LoginIF;


public class logonPage extends AppCompatActivity {
    private Button btn;
    private EditText loginName;
    private EditText loginPassword;
    private EditText rloginPassword;
    private EditText loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_page);
        loginName = findViewById(R.id.logonIdText);
        loginPassword = findViewById(R.id.logonPasswordText);
        rloginPassword = findViewById(R.id.surePasswordText);
        loginEmail = findViewById(R.id.logonEmailText);

        btn = findViewById(R.id.submitBtn);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = loginName.getText().toString();
                String password = loginPassword.getText().toString();
                String rpassword = rloginPassword.getText().toString();
                String email = loginEmail.getText().toString();
                Boolean a = true;
                Boolean b = true;
                if(name.equals("")||password.equals("")||rpassword.equals("")||email.equals("")){
                    Toast.makeText(getApplicationContext(), "所有输入不能为空！", Toast.LENGTH_LONG).show();
                }
                else{
                    for(User user : LoginIF.getInstance().getAllInf()) {
                        if (email.equals(user.getEmail())) {
                            Toast.makeText(getApplicationContext(), "该邮箱已注册！", Toast.LENGTH_LONG).show();
                            b = false;
                        }
                    }
                    if(b && (password.equals(rpassword))){
                        Boolean res = LoginIF.getInstance().newUser(name, password, email, 0);
                        Intent intent=new Intent(logonPage.this, loginPage.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"确认密码错误！",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}
