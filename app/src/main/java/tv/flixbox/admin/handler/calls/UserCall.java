package tv.flixbox.admin.handler.calls;

import android.content.Context;

import java.io.InputStream;

import tv.flixbox.admin.FApplication;
import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.fengine.FRequest;
import tv.flixbox.admin.libs.fengine.FResponse;
import tv.flixbox.admin.libs.fengine.FSocket;
import tv.flixbox.admin.libs.fengine.FSocketCallback;
import tv.flixbox.admin.libs.json.io.JsonReader;
import tv.flixbox.admin.libs.json.variables.JsonObject;

public class UserCall extends FSocketCallback {

    private Context context;
    private ResponseCallback callback;

    public UserCall(Context context, ResponseCallback callback){
        this.context = context;
        this.callback = callback;
    }

    public void request(){
        String uri = context.getString(R.string.admin_api_uri)+"user";

        new FSocket(context, uri, this).async(((FApplication) context.getApplicationContext()).getExecutor(), 2);
    }

    @Override
    public void onResponse(FRequest request, FResponse response, InputStream in)throws Exception {
        if(response.getStatusCode() == 200){
            JsonReader r = new JsonReader(in);
            JsonObject j = r.readJsonObject();

            switch(j.getInteger("type")){
                case 0:
                    callback.onSuccessResponse(j.getJsonArray("result"));
                    break;

                default:
                    callback.onErrorResponse(j);
                    break;
            }
        }
    }

    @Override
    public void onException(Exception e){
        e.printStackTrace();
    }
}
