package tv.flixbox.admin.ui.views;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FAppCompatActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), null);

    public ActivityResultLauncher<Intent> getActivityResultLauncher(){
        return activityResultLauncher;
    }

    public void onSignOut(){
    }
}
