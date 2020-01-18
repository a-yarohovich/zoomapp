package mango.zoom.mangozoom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.ZoomSDK;

public class JoinMeetingActivity
        extends
            AppCompatActivity
        implements
            MeetingServiceListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBtnJoinMeeting(View v) {
        String meetingNo = ((EditText)findViewById(R.id.editMeetingNumber)).getText().toString().trim();
        String displayName = ((EditText)findViewById(R.id.editDisplayName)).getText().toString();
        if(meetingNo.length() == 0) {
            Tools.ShowToast(this, "You need to enter a meeting number/ vanity id which you want to join.");
            return;
        }
        if (displayName.length() == 0) {
            displayName = "Anonymous";
        }
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        if(!zoomSDK.isInitialized()) {
            Tools.ShowToast(this, "ZoomSDK has not been initialized successfully");
            return;
        }
        MeetingService meetingService = zoomSDK.getMeetingService();
        JoinMeetingOptions opts = new JoinMeetingOptions();
        JoinMeetingParams params = new JoinMeetingParams();
        params.displayName = displayName;
        params.meetingNo = meetingNo;
        meetingService.joinMeetingWithParams(this, params, opts);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {
        Tools.ShowToast(
                this, String.format("Meeting status changed(status:%s, error:%d, internalError:%d)", meetingStatus, errorCode, internalErrorCode)
        );
    }

}
