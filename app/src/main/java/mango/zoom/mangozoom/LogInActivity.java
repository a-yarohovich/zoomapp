package mango.zoom.mangozoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;

public class LogInActivity
        extends
            AppCompatActivity
        implements
            ZoomSDKAuthenticationListener {

    private EditText mEdtUserName;
    private EditText mEdtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mEdtUserName = findViewById(R.id.editUsername);
        mEdtPassword = findViewById(R.id.editPassword);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBtnLogIn(View v) {
        String userName = mEdtUserName.getText().toString().trim();
        String password = mEdtPassword.getText().toString().trim();
        if(userName.length() == 0 || password.length() == 0) {
            Tools.ShowToast(this, "You need to enter user name and password.");
            return;
        }
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        Log.d("onBtnLogIn", "username:" + userName + " password:" + password);
        int loginRes = zoomSDK.loginWithZoom(userName, password);
        Log.d("onBtnLogIn", "Login result: " + loginRes);
        if(loginRes != ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
            Tools.ShowToast(this, "ZoomSDK has not been initialized successfully or sdk is logging in.");
        }
    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        Tools.ShowToast(this, "LogIn code = " + result);
        if(result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            Tools.ShowToast(this, "Login successfully");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Tools.ShowToast(this, "Login failed result code = " + result);
        }
    }

    @Override
    public void onZoomSDKLogoutResult(long result) {
        Tools.ShowToast(this, "LogOut code = " + result);
    }

    @Override
    public void onZoomIdentityExpired() {

    }

    @Override
    public void onZoomAuthIdentityExpired() {

    }
}
