package tv.flixbox.admin.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.ui.fragments.TorrentFragment;
import tv.flixbox.admin.ui.fragments.UserFragment;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class MainActivity extends FAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        /*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new TorrentFragment(), "Home");
        //transaction.addToBackStack("Home");
        transaction.commit();
        */



        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                //Intent intent;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch(item.getItemId()){
                    case R.id.dashboard:
                        break;

                    case R.id.users:
                        //intent = new Intent(MainActivity.this, LatestActivity.class);
                        //activityResultLauncher.launch(intent);
                        //overridePendingTransition(0, 0);
                        transaction.add(R.id.fragment_container, new UserFragment(), "Users");
                        break;

                    case R.id.torrents:
                        //intent = new Intent(MainActivity.this, LatestActivity.class);
                        //activityResultLauncher.launch(intent);
                        //overridePendingTransition(0, 0);
                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.fragment_container, new TorrentFragment(), "Torrents");
                        //transaction.commit();
                        break;

                        /*
                    case R.id.flicks:
                        intent = new Intent(MainActivity.this, LatestActivity.class);
                        activityResultLauncher.launch(intent);
                        overridePendingTransition(0, 0);
                        break;
                        */

                    case R.id.settings:
                        //intent = new Intent(MainActivity.this, SettingsActivity.class);
                        //activityResultLauncher.launch(intent);
                        //overridePendingTransition(0, 0);
                        break;
                }

                transaction.commit();

                return false;
            }
        });


    }

    @Override
    public ActivityResultLauncher<Intent> getActivityResultLauncher(){
        return activityResultLauncher;
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == RESULT_OK){
                        if(result.getData().hasExtra("sign-out")){
                            onSignOut();
                        }
                    }
                }
            });

    @Override
    public void onSignOut(){
        /*
        new CookieStore(this).removeAll();
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("skip-splash", true);
        activityResultLauncher.launch(intent);
        finish();
        overridePendingTransition(0, 0);
        */
    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            return;
        }

        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}