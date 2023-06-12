package tv.flixbox.admin.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.textfield.TextInputEditText;

import tv.flixbox.admin.R;
import tv.flixbox.admin.handler.calls.ResponseCallback;
import tv.flixbox.admin.handler.calls.SignCall;
import tv.flixbox.admin.libs.json.variables.JsonObject;
import tv.flixbox.admin.libs.json.variables.JsonVariable;
import tv.flixbox.admin.ui.MainActivity;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class SigninFragment extends Fragment implements ResponseCallback {

    private boolean isLoading;
    private Handler handler;
    private SignCall call;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_signin, parent, false);
        //((LinearLayout.LayoutParams) v.findViewById(R.id.logo).getLayoutParams())
        //        .topMargin += ((FApplication) getContext().getApplicationContext()).getStatusBarHeight();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.signup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((ViewPager2) getActivity().findViewById(R.id.view_pager)).setCurrentItem(1);
            }
        });

        TextInputEditText email = getView().findViewById(R.id.email);
        TextInputEditText password = getView().findViewById(R.id.password);

        getView().findViewById(R.id.signin).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isLoading){
                    setResponse("Please wait, still loading...");
                    return;
                }

                if(call == null){
                    call = new SignCall(getContext(), SigninFragment.this);
                }

                call.request("signin", ("email="+email.getText().toString()+
                        "&pwd="+password.getText().toString()).getBytes());
            }
        });
    }

    private void setResponse(String r){
        TextView response = getView().findViewById(R.id.response);
        response.setText(r);
        response.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessResponse(JsonVariable j){
        handler.post(new Runnable(){
            @Override
            public void run(){
                isLoading = false;
                ((FAppCompatActivity) getActivity()).getActivityResultLauncher().launch(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onErrorResponse(JsonObject j){
        handler.post(new Runnable(){
            @Override
            public void run(){
                setResponse(j.getJsonObject("result").getString("message"));
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}