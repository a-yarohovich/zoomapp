package mango.zoom.mangozoom;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

class Tools {
    static void ShowToast(Context ctx, String txt) {
        Tools.ShowToast(ctx, txt, Toast.LENGTH_LONG);
    }

    static void ShowToast(Context ctx, String txt, int lenght) {
        Toast toast = Toast.makeText(ctx, txt, lenght);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}