package com.example.skyreach;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skyreach.connect.tool.User;
import com.example.skyreach.connect.work.LoginIF;


public class userManagePage extends AppCompatActivity {

    private Button PC;
    private EditText oP;
    private EditText nP;
    private ShareP shareP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage_page);
        shareP=new ShareP(this);
        oP = findViewById(R.id.m_oldPassword);
        nP = findViewById(R.id.m_newPassword);
        PC = findViewById(R.id.passwordChange);
        PC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldP = oP.getText().toString();
                String newP = nP.getText().toString();
                Boolean res = LoginIF.getInstance().updatePassword(shareP.getString("email"), oldP, newP);
                if(res){
                    Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_LONG).show();
                    for(User user : LoginIF.getInstance().getAllInf()){
                        if(user.getEmail().equals(shareP.getString("email"))){
                            shareP.putString("pas",newP);
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
