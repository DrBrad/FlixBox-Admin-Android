package tv.flixbox.admin.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.handler.calls.TorrentCall;
import tv.flixbox.admin.handler.calls.ResponseCallback;
import tv.flixbox.admin.libs.json.variables.JsonArray;
import tv.flixbox.admin.libs.json.variables.JsonObject;
import tv.flixbox.admin.libs.json.variables.JsonVariable;
import tv.flixbox.admin.ui.adapters.TorrentAdapter;
import tv.flixbox.admin.ui.views.FAppCompatActivity;

public class TorrentFragment extends Fragment implements ResponseCallback {

    private TorrentCall call;
    private RecyclerView listView;
    private Handler handler;
    private boolean isLoading = true;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_torrents, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        call = new TorrentCall(getContext(), this);

        listView = getView().findViewById(R.id.list_view);
        TorrentAdapter adapter = new TorrentAdapter(getContext());
        listView.setAdapter(adapter);

        /*
        listView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                if(!isLoading){
                    if(((GridLayoutManager) listView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == adapter.getItemCount()-4){
                        isLoading = true;
                        call.request(adapter.size());
                    }
                }
            }
        });
        */

        call.request();
    }

    @Override
    public void onSuccessResponse(JsonVariable j){
        handler.post(new Runnable(){
            @Override
            public void run(){
                Log.e("info", j.toString());
                ((TorrentAdapter) listView.getAdapter()).setJson(((JsonObject) j).getJsonArray("torrents"));

                /*
                View loader = getView().findViewById(R.id.loader);
                if(loader != null){
                    ((ViewGroup) loader.getParent()).removeView(loader);
                }
                */

                isLoading = false;
            }
        });
    }

    @Override
    public void onErrorResponse(JsonObject j){
        switch(j.getInteger("type")){
            case 1:
                /*
                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        //((TorrentAdapter) listView.getAdapter()).stopShimmer();
                    }
                });
                */
                break;

            case 2:
                ((FAppCompatActivity) getActivity()).onSignOut();
                break;

            default:
                //throw new Exception(j.getJsonObject("result").getString("message"));
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}