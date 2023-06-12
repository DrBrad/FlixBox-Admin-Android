package tv.flixbox.admin.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TooltipCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FBottomNavigationView extends BottomNavigationView {

    public FBottomNavigationView(@NonNull Context context){
        super(context);
    }

    public FBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    public FBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        for(int i = 0; i < getMenu().size(); i++){
            TooltipCompat.setTooltipText(findViewById(getMenu().getItem(i).getItemId()), null);
        }
    }
}
