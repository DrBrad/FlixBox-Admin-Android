package tv.flixbox.admin.ui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.ui.fragments.PreferenceFragment;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class SettingsActivity extends FAppCompatActivity {

    //private FChromeCast chromeCast;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        //chromeCast = new FChromeCast(this);
        //CastContext.getSharedInstance().getSessionManager().addSessionManagerListener(new FCastSessionManager(chromeCast, this));
        //CastContext.getSharedInstance().addCastStateListener(new FCastStateListener(this));

        /*
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.settings);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Intent intent;

                switch(item.getItemId()){
                    case R.id.home:
                        intent = new Intent(SettingsActivity.this, MainActivity.class);
                        getActivityResultLauncher().launch(intent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.latest:
                        intent = new Intent(SettingsActivity.this, LatestActivity.class);
                        getActivityResultLauncher().launch(intent);
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.settings:
                        break;
                }
                return false;
            }
        });
        */

        getSupportFragmentManager().beginTransaction().replace(R.id.preferences, new PreferenceFragment()).commit();
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(0, 0);
    }
}
