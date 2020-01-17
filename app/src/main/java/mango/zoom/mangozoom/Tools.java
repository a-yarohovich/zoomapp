package mango.zoom.mangozoom;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Tools {
    public static void ShowToast(Context ctx, String txt) {
        Toast toast = Toast.makeText(
                ctx, txt, Toast.LENGTH_LONG
        );
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
