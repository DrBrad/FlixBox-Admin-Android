package tv.flixbox.admin;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import tv.flixbox.admin.libs.fengine.pool.PBQThreadPoolExecutor;

public class FApplication extends Application {

    /**
    * UPDATED
    * */

    //noHistory... ?

    private PBQThreadPoolExecutor executor;
    private LruCache<String, Bitmap> memoryCache;

    @Override
    public void onCreate(){
        super.onCreate();

        PriorityBlockingQueue<Runnable> pbq = new PriorityBlockingQueue<>(1024);
        executor = new PBQThreadPoolExecutor(1024, 2048, 1000L, TimeUnit.MILLISECONDS, pbq);

        int cacheSize = (int) (Runtime.getRuntime().maxMemory()/1024)/16;
        memoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getByteCount()/1024;
            }
        };
    }

    public PBQThreadPoolExecutor getExecutor(){
        return executor;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromMemCache(key) == null){
            memoryCache.put(key, bitmap);
        }
    }

    public boolean containsBitmapInMemoryCache(String key){
        return getBitmapFromMemCache(key) != null;
    }

    public Bitmap getBitmapFromMemCache(String key){
        return memoryCache.get(key);
    }
}
