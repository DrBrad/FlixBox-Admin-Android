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

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;

import tv.flixbox.admin.FApplication;
import tv.flixbox.admin.ui.MainActivity;
import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.fengine.FRequest;
import tv.flixbox.admin.libs.fengine.FResponse;
import tv.flixbox.admin.libs.fengine.FSocket;
import tv.flixbox.admin.libs.fengine.FSocketCallback;
import tv.flixbox.admin.libs.fengine.headers.RequestHeaders;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class SigninFragment extends Fragment {

    private boolean isLoading;
    private Handler handler;

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

                call(("email="+email.getText().toString()+
                        "&pwd="+password.getText().toString()).getBytes());
            }
        });
    }

    private void setResponse(String r){
        TextView response = getView().findViewById(R.id.response);
        response.setText(r);
        response.setVisibility(View.VISIBLE);
    }

    private void call(byte[] post){
        isLoading = true;
        String uri = getString(R.string.api_uri)+"signin";

        FSocket socket = new FSocket(getContext(), uri, new FSocketCallback(){
            @Override
            public void onRequest(FRequest request, OutputStream out)throws Exception {
                out.write(post);
                out.flush();
            }

            @Override
            public void onResponse(FRequest request, FResponse response, InputStream in)throws Exception {
                if(response.getStatusCode() == 200){
                    StringBuilder b = new StringBuilder();

                    byte[] buf = new byte[4096];
                    int l;
                    while((l = in.read(buf)) > 0){
                        b.append(new String(buf, 0, l));
                    }

                    JSONObject j = new JSONObject(b.toString());

                    switch(j.getInt("type")){
                        case 0:
                            handler.post(new Runnable(){
                                @Override
                                public void run(){
                                    isLoading = false;
                                    ((FAppCompatActivity) getActivity()).getActivityResultLauncher().launch(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();
                                    getActivity().overridePendingTransition(0, 0);
                                }
                            });
                            break;

                        default:
                            throw new Exception(j.getJSONObject("result").getString("message"));
                    }
                }
            }

            @Override
            public void onException(Exception e){
                e.printStackTrace();
                isLoading = false;
                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        setResponse(e.getMessage());
                    }
                });
            }
        });
        socket.getRequest().setMethod(RequestHeaders.Method.POST);
        socket.getRequest().addHeader("Content-Length", post.length+"");
        socket.async(((FApplication) getContext().getApplicationContext()).getExecutor());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}