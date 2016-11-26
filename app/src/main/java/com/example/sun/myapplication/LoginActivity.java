package com.example.sun.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsernameET;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameET = (EditText) findViewById(R.id.username_ET);

        mLoginBtn = (Button) findViewById(R.id.login_Btn);
        mLoginBtn.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_Btn:
                Intent i = new Intent(LoginActivity.this, QuestionActivity.class);
                i.putExtra("username", mUsernameET.getText().toString());
                startActivity(i);
                break;
            default:
                break;
        }
    }

}
