package com.keye.router.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.keye.router.lib_common.base.AppConfig;
import com.keye.router.lib_common.base.utils.AppLogs;
import com.keye.router.lib_common.base.utils.PermissionUtils;
import com.keye.router.lib_common.base.utils.Preferences;
import com.keye.router.main.chart.ui.SheetChartActivity;
import com.keye.router.main.excel.constants.Constants;
import com.keye.router.main.excel.constants.ProcessConfig;
import com.keye.router.main.excel.db.ExcelDBHandler;

import static com.keye.router.main.excel.constants.Constants.IS_FIRST_INIT;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermission();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ExcelDBHandler.getInstance().checkInitSheet(getApplicationContext(), new ExcelDBHandler.CallBack() {
                    @Override
                    public void onAnalyzeResult(long time, boolean success) {
                        Snackbar.make(view, "重载DB数据:" + success + " ,耗时：" + time, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            }
        });
    }

    public void checkAndRequestPermission() {
        final String[] permissions = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
        PermissionUtils.checkMorePermissions(this,
                permissions,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        init();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        PermissionUtils.requestMorePermissions(SplashActivity.this, permissions, 1);
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestMorePermissions(SplashActivity.this, permissions, 1);

                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != 0 || grantResults[1] != 0) {
                Toast.makeText(SplashActivity.this, "不给权限玩几把！", Toast.LENGTH_SHORT).show();
                return;
            }
            new ProcessConfig().init();
            boolean isfirstInit = Preferences.getBoolean(IS_FIRST_INIT, true);
            if (isfirstInit) {//初始化DB数据
                Toast.makeText(SplashActivity.this, "数据初始化中...", Toast.LENGTH_LONG).show();
                ExcelDBHandler.getInstance().checkInitSheet(this, new ExcelDBHandler.CallBack() {
                    @Override
                    public void onAnalyzeResult(long time, boolean success) {
                        init();
                        if (!success) {
                            Toast.makeText(SplashActivity.this, "初始化DB数据失败", Toast.LENGTH_SHORT).show();
                        } else {
                            if (AppConfig.DEBUG)
                                Toast.makeText(SplashActivity.this, "初始化耗时：" + time, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Preferences.saveBoolean(IS_FIRST_INIT, false);
            }
        }
    }

    private void init() {
        Intent intent = new Intent(SplashActivity.this, SheetChartActivity.class);
        intent.putExtra("sheet", "上证指数");
        startActivity(intent);
    }
}
