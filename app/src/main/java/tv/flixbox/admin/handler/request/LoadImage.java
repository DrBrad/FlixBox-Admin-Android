package tv.flixbox.admin.handler.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tv.flixbox.admin.FApplication;
import tv.flixbox.admin.libs.fengine.FRequest;
import tv.flixbox.admin.libs.fengine.FResponse;
import tv.flixbox.admin.libs.fengine.FSocket;
import tv.flixbox.admin.libs.fengine.FSocketCallback;

public class LoadImage extends FSocketCallback {

    private ImageView v;
    private String url;

    public LoadImage(ImageView v){
        this.v = v;
    }

    public void execute(){
        url = (String) v.getTag();//v.getContext().getString(R.string.image_uri)+v.getTag();

        //VERSION 2.0
        ((FApplication) v.getContext().getApplicationContext()).getExecutor().submit(new Runnable(){
            private Bitmap bitmap;

            @Override
            public void run(){
                bitmap = ((FApplication) v.getContext().getApplicationContext()).getBitmapFromMemCache(url);
                if(bitmap == null){
                    bitmap = getFileCache(v.getContext(), url);
                    if(bitmap == null){
                        new FSocket(v.getContext(), url, LoadImage.this).connect();
                        return;
                    }
                }

                v.post(new Runnable(){
                    @Override
                    public void run(){
                        v.setImageBitmap(bitmap);
                    }
                });
            }
        }, 0);


        /*
        //VERSION 1.0
        if(((FApplication) v.getContext().getApplicationContext()).containsBitmapInMemoryCache(url)){
            v.setImageBitmap(((FApplication) v.getContext().getApplicationContext()).getBitmapFromMemCache(url));
        }else{
            new FSocket(v.getContext(), url, LoadImage.this).async(((FApplication) v.getContext().getApplicationContext()).getExecutor());
        }
        */
    }

    @Override
    public void onResponse(FRequest request, FResponse response, InputStream in)throws IOException {
        if(response.getStatusCode() == 200){
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            saveFileCache(v.getContext(), bitmap, url);

            ((FApplication) v.getContext().getApplicationContext()).addBitmapToMemoryCache(url, bitmap);
            v.post(new Runnable(){
                @Override
                public void run(){
                    if(url.equals(v.getTag())){
                        v.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap getFileCache(Context context, String url){
        File cache = new File(context.getCacheDir(), "cache");
        File f = new File(cache, url.substring(url.lastIndexOf("/")+1));

        if(f.exists()){
            return BitmapFactory.decodeFile(f.getPath());
        }
        return null;
    }

    private void saveFileCache(Context context, Bitmap bitmap, String url){
        try{
            File cache = new File(context.getCacheDir(), "cache");
            cache.mkdirs();

            File f = new File(cache, url.substring(url.lastIndexOf("/")+1));

            if(!f.exists()){
                f.createNewFile();
                OutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
