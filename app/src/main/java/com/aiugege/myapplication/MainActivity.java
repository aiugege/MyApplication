package com.aiugege.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.to_webview_button){
            startActivity(new Intent(MainActivity.this, SnapShotActivity.class));





//            Intent intent = new Intent();
//            intent.setClassName("com.bonc.mobile.bj.sale", "com.bonc.mobile.bj.sale.Start");
//            intent.putExtra("mx_sso_token","mx_sso_token");
//            this.startActivity(intent);
        }

    }
}
