package tv.flixbox.admin.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import tv.flixbox.admin.R;

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {

    private float radius = 18.0f;
    private RectF rect;

    public RoundImageView(Context context){
        super(context);
        setWillNotDraw(false);
    }

    public RoundImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, 0, 0);
        radius = t.getDimensionPixelSize(R.styleable.RoundImageView_android_radius, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImageView, 0, 0);
        radius = t.getDimensionPixelSize(R.styleable.RoundImageView_android_radius, 0);
    }

    @Override
    public void setBackground(Drawable background){
        super.setBackground(background);
        invalidate();
    }

    @Override
    public void setBackgroundResource(int resid){
        super.setBackgroundResource(resid);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        Path path = new Path();
        rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
