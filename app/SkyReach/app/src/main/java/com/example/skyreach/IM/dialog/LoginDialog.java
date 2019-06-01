package com.example.skyreach.IM.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skyreach.IM.IMain;
import com.example.skyreach.IM.common.NetWorkUtils;
import com.example.skyreach.IM.common.UserManager;
import com.example.skyreach.R;
import com.xiaomi.mimc.MIMCUser;


public class LoginDialog extends Dialog {
    Context context;

    public LoginDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);
        setCancelable(true);
        setTitle(R.string.login);

        final EditText accountEditText = (EditText) findViewById(R.id.account);
        final SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        accountEditText.setText(sp.getString("loginAccount", null));
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                sp.edit().putString("loginAccount", account).commit();

                if (!NetWorkUtils.isNetwork(getContext())) {
                    Toast.makeText(getContext(), getContext().getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
                    return;
                } else if (!TextUtils.isEmpty(account)){
                    MIMCUser user = UserManager.getInstance().newUser(account);
                    if (user != null) {
                        if (context instanceof IMain) {
                            IMain mainActivity = (IMain) context;
                            if (mainActivity.getDatas() != null) {
                                mainActivity.getDatas().clear();
                            }
                        }
                        user.login();
                    }
                    dismiss();
                }
            }
        });
    }
}
