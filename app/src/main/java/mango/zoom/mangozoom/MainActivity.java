package mango.zoom.mangozoom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
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
        Log.d(this.toString(), String.format("onCreate. State: %s", savedInstanceState));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initZoomSDK();
    }

    public void onBtnWithoutLogin(View v) {
        Intent intent = new Intent(this, JoinMeetingActivity.class);
        startActivity(intent);
    }

    public void onBtnLogIn(View v) {
        showLogInActivity();
    }

    private void visibleControls(int visible) {
        findViewById(R.id.btnLoginIn).setVisibility(visible);
        findViewById(R.id.btnJoinMeeting).setVisibility(visible);
    }

    private void showLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    // ZOOM
    private void initZoomSDK() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        if (zoomSDK.isInitialized()){
            if(zoomSDK.isLoggedIn()) {
                showLogInActivity();
                return;
            }
            visibleControls(View.VISIBLE);
            return;
        }
        ZoomSDKInitParams initParams = new ZoomSDKInitParams();
        initParams.appKey = APP_KEY;
        initParams.appSecret = APP_SECRET;
        zoomSDK.initialize(this, this, initParams);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        String resTxt;
        if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
            resTxt = "SDK initialized succesfully";
            visibleControls(View.VISIBLE);
        }
        else {
            resTxt = String.format("SDK initialization failed(error:%d internalError:%d)", errorCode, internalErrorCode);
            visibleControls(View.GONE);
        }
        Tools.ShowToast(this, resTxt, Toast.LENGTH_SHORT);
//        ZoomSDK zoomSDK = ZoomSDK.getInstance();
//        if(zoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
//            zoomSDK.addAuthenticationListener(this);
//            visibleControls(View.GONE);
//        } else {
//            visibleControls(View.VISIBLE);
//        }
    }

    @Override
    public void onZoomAuthIdentityExpired() {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onZoomSDKLoginResult(long result) {
        if((int)result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            visibleControls(View.GONE);
            Tools.ShowToast(this, String.format("Login failed(error:%d)", result), Toast.LENGTH_SHORT);
        } else {
            visibleControls(View.VISIBLE);
        }
    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {

    }
}
