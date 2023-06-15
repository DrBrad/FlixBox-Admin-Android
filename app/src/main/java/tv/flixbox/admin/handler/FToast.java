package tv.flixbox.admin.handler;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tv.flixbox.admin.R;

public class FToast extends Toast {

    public FToast(Context context, String message){
        this(context, message, Toast.LENGTH_SHORT);
    }

    public FToast(Context context, String message, int duration){
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        ((TextView) view.findViewById(R.id.text)).setText(message);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
        setView(view);
        setDuration(duration);
    }
}
