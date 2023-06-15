package tv.flixbox.admin.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import tv.flixbox.admin.BuildConfig;
import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.fengine.cookie.CookieStore;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey){
        setPreferencesFromResource(R.xml.preferences, rootKey);

        findPreference("version").setSummary(BuildConfig.VERSION_NAME);
        findPreference("fengine").setSummary("f16.14");
        findPreference("json").setSummary("j2.4");

        findPreference("signout").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference){
                new CookieStore(getContext()).removeAll();

                Intent intent = getActivity().getIntent();
                intent.putExtra("sign-out", true);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);

                return false;
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}