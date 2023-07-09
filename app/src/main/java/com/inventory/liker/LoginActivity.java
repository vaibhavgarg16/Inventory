package com.inventory.liker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginId;
    EditText etUserNameId, etUserPassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btnLoginId = findViewById(R.id.btnLoginId);
        btnLoginId.setOnClickListener(this);
        etUserNameId = findViewById(R.id.etUserNameId);
        etUserPassId = findViewById(R.id.etUserPassId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginId:
//                validation();
                startActivity(new Intent(LoginActivity.this, DashboardNavigationDrawer.class));
                break;
        }
    }

    public void validation(){
        if (etUserNameId.getText().toString().equalsIgnoreCase("")){
            etUserNameId.setError("Enter Name");
        } else if (etUserPassId.getText().toString().equalsIgnoreCase("")){
            etUserPassId.setError("Enter Password");
        } else {
            //Call other activity in this block
            startActivity(new Intent(LoginActivity.this, DashboardNavigationDrawer.class));
        }
    }
}