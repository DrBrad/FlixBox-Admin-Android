package tv.flixbox.admin.ui;

import static tv.flixbox.admin.handler.User.isSignedIn;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import tv.flixbox.admin.R;
import tv.flixbox.admin.ui.adapters.SlidePagerAdapter;
import tv.flixbox.admin.ui.fragments.SigninFragment;
import tv.flixbox.admin.ui.fragments.SignupFragment;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class SplashActivity extends FAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);


        ViewPager2 viewPager = findViewById(R.id.view_pager);

        if(!isSignedIn(getApplicationContext())){
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(new SigninFragment());
            fragments.add(new SignupFragment());

            if(!getIntent().hasExtra("skip-splash") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                viewPager.setBackgroundResource(R.drawable.ic_launcher_animation);
                AnimatedVectorDrawable animation = (AnimatedVectorDrawable) viewPager.getBackground();
                animation.start();

                viewPager.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        animation.stop();
                        viewPager.setBackground(null);

                        SlidePagerAdapter adapter = new SlidePagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
                        viewPager.setAdapter(adapter);
                    }
                }, 2500);

            }else{
                SlidePagerAdapter adapter = new SlidePagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
                viewPager.setAdapter(adapter);
            }

        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                viewPager.setBackgroundResource(R.drawable.ic_launcher_animation);
                AnimatedVectorDrawable animation = (AnimatedVectorDrawable) viewPager.getBackground();
                animation.start();

                viewPager.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        //animation.stop();
                        //viewPager.setBackground(null);

                        //WORK
                        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        getActivityResultLauncher().launch(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }, 2500);

            }else{
                //WORK
                getActivityResultLauncher().launch(new Intent(SplashActivity.this, MainActivity.class));
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(0, 0);
            }
        }
    }

    @Override
    public void onBackPressed(){
    }
}
