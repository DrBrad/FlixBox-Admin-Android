package tv.flixbox.admin.handler;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.fengine.cookie.CookieStore;

public class User {

    public static boolean isSignedIn(Context context){
        try{
            CookieStore cookieStore = new CookieStore(context);
            CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);

            List<HttpCookie> cookies = cookieManager.getCookieStore().get(new URI(context.getString(R.string.api_uri)));
            if(cookies.size() > 0){
                for(HttpCookie cookie : cookies){
                    if(cookie.getName().equals("token")){
                        return isJWTValid(cookie.getValue());
                    }
                }
            }
        }catch(URISyntaxException e){
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isJWTValid(String jwt){
        try{
            String[] tokenParts = jwt.split("\\.");
            return new JSONObject(new String(Base64.decode(tokenParts[1], Base64.DEFAULT))).getLong("exp") > System.currentTimeMillis()/1000;

        }catch(JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}
