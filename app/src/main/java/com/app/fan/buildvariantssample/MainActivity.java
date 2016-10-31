package com.app.fan.buildvariantssample;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                try {
                    ApplicationInfo appInfo = this.getPackageManager()
                            .getApplicationInfo(getPackageName(),
                                    PackageManager.GET_META_DATA);
                    String msg = appInfo.metaData.getString("TEST_KEY");
                    Toast.makeText(this, msg + "--------" + Constant.APP_HOST + Constant.APP_PORT, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
