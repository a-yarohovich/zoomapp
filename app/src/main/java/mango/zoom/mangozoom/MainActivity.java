package mango.zoom.mangozoom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class MainActivity
        extends
            AppCompatActivity
        implements
            Constants,
            ZoomSDKInitializeListener,
            ZoomSDKAuthenticationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            initZoomSDK();
        }
    }

    public void onBtnWithoutLogin(View v) {
        Intent intent = new Intent(this, JoinMeetingActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBtnLogIn(View v) {
        //TODO
    }

    private void initZoomSDK() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        ZoomSDKInitParams initParams = new ZoomSDKInitParams();
        initParams.appKey = APP_KEY;
        initParams.appSecret = APP_SECRET;
        zoomSDK.initialize(this, this, initParams);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        String resTxt;
        if (errorCode == 0) {
            resTxt = "SDK инициализирован успешно";
        }
        else {
            resTxt = String.format("Статус инициализации SDK:(error:%d internalError:%d)", errorCode, internalErrorCode);
        }
        Tools.ShowToast(this, resTxt);
    }

    @Override
    public void onZoomAuthIdentityExpired() {

    }

    @Override
    public void onZoomSDKLoginResult(long result) {

    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {

    }
}
