package tv.flixbox.admin.handler.calls;

import android.content.Context;

import java.io.InputStream;
import java.io.OutputStream;

import tv.flixbox.admin.FApplication;
import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.fengine.FRequest;
import tv.flixbox.admin.libs.fengine.FResponse;
import tv.flixbox.admin.libs.fengine.FSocket;
import tv.flixbox.admin.libs.fengine.FSocketCallback;
import tv.flixbox.admin.libs.fengine.headers.RequestHeaders;
import tv.flixbox.admin.libs.json.io.JsonReader;
import tv.flixbox.admin.libs.json.variables.JsonObject;

public class SignCall extends FSocketCallback {

    private Context context;
    private ResponseCallback callback;

    private byte[] post;

    public SignCall(Context context, ResponseCallback callback){
        this.context = context;
        this.callback = callback;
    }

    public void request(String type, byte[] post){
        this.post = post;
        String uri = context.getString(R.string.api_uri)+type;

        FSocket socket = new FSocket(context, uri, this);
        socket.getRequest().setMethod(RequestHeaders.Method.POST);
        socket.getRequest().addHeader("Content-Length", post.length+"");
        socket.async(((FApplication) context.getApplicationContext()).getExecutor(), 2);
    }

    @Override
    public void onRequest(FRequest request, OutputStream out)throws Exception {
        out.write(post);
        out.flush();
    }

    @Override
    public void onResponse(FRequest request, FResponse response, InputStream in)throws Exception {
        if(response.getStatusCode() == 200){
            JsonReader r = new JsonReader(in);
            JsonObject j = r.readJsonObject();

            switch(j.getInteger("type")){
                case 0:
                    callback.onSuccessResponse(j.getJsonObject("result"));
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
