package tv.flixbox.admin.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.json.variables.JsonArray;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.RecyclerViewAdapter> {

    public Context context;
    private JsonArray json;

    public static final int TR_STATUS_STOPPED       = 0;
    public static final int TR_STATUS_CHECK_WAIT    = 1;
    public static final int TR_STATUS_CHECK         = 2;
    public static final int TR_STATUS_DOWNLOAD_WAIT = 3;
    public static final int TR_STATUS_DOWNLOAD      = 4;
    public static final int TR_STATUS_SEED_WAIT     = 5;
    public static final int TR_STATUS_SEED          = 6;

    public static final int RPC_LT_14_TR_STATUS_CHECK_WAIT = 1;
    public static final int RPC_LT_14_TR_STATUS_CHECK      = 2;
    public static final int RPC_LT_14_TR_STATUS_DOWNLOAD   = 4;
    public static final int RPC_LT_14_TR_STATUS_SEED       = 8;
    public static final int RPC_LT_14_TR_STATUS_STOPPED    = 16;

    private int rpcVersion;

    public TorrentAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_torrent, parent, false);
        return new RecyclerViewAdapter(view);
    }

    @Override
    public int getItemViewType(int position){
        return (position >= json.size()) ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, @SuppressLint("RecyclerView") int position){
        holder.title.setText(json.getJsonObject(position).getString("title"));
        holder.description.setText((json.getJsonObject(position).getDouble("percentDone")*100)+"% complete");
        holder.status.setText(getStatusString(json.getJsonObject(position).getInteger("status")));

        holder.peers.setText(json.getJsonObject(position).getInteger("peersConnected")+" Peers");
        holder.eta.setText(formatTime(json.getJsonObject(position).getInteger("eta")));

        holder.progress.setProgress((int) (json.getJsonObject(position).getDouble("percentDone")*1000));
    }

    private String formatTime(int seconds){
        if(seconds < 1){
            return "Unknown";
        }
        double hours = Math.floor(seconds/3600);
        double mins = Math.floor((seconds%3600)/60);
        int remainingSeconds = seconds%60;

        return hours+":"+mins+":"+remainingSeconds;
    }

    @Override
    public int getItemCount(){
        return (json == null) ? 0 : json.size();
    }

    public void setJson(int rpcVersion, JsonArray json){
        this.rpcVersion = rpcVersion;
        this.json = json;
        notifyDataSetChanged();
    }

    public String getStatusString(int status){
        if(rpcVersion < 14){
            switch(status){
                case RPC_LT_14_TR_STATUS_CHECK_WAIT:
                    return "Waiting to verify local files";

                case RPC_LT_14_TR_STATUS_CHECK:
                    return "Verifying local files";

                case RPC_LT_14_TR_STATUS_DOWNLOAD:
                    return "Downloading";

                case RPC_LT_14_TR_STATUS_SEED:
                    return "Seeding";

                case RPC_LT_14_TR_STATUS_STOPPED:
                    return "Stopped";

                default:
                    return "Unknown";
            }

        }else{
            switch(status){
                case TR_STATUS_CHECK_WAIT:
                    return "Waiting to verify local files";

                case TR_STATUS_CHECK:
                    return "Verifying local files";

                case TR_STATUS_DOWNLOAD:
                    return "Downloading";

                case TR_STATUS_SEED:
                    return "Seeding";

                case TR_STATUS_STOPPED:
                    return "Stopped";

                case TR_STATUS_SEED_WAIT:
                    return "Queued for seeding";

                case TR_STATUS_DOWNLOAD_WAIT:
                    return "Queued for download";

                default:
                    return "Unknown";
            }
        }
    }

    public static class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        public View view;
        public TextView title, description, status, peers, eta;
        public ProgressBar progress;

        public RecyclerViewAdapter(View view){
            super(view);
            this.view = view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            status = view.findViewById(R.id.status);
            peers = view.findViewById(R.id.peers);
            eta = view.findViewById(R.id.eta);

            progress = view.findViewById(R.id.progress);
        }
    }
}
