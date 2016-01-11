package vn.edu.hcmiu.whatsmovie.security;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by quyvu-pc on 07/01/2016.
 */
public class securityManager {

    private SharedPreferences sharedPref;
    private Activity activity;

    public securityManager(Context context){
        activity = (Activity) context;
        sharedPref = activity.getPreferences(context.MODE_PRIVATE);
    }

    public void writeToken(String name, String token){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, token);
        editor.commit();
    }

    public String readToken(String name){
        return sharedPref.getString(name,"N/A");
    }

    public boolean removeToken(String name){
        return sharedPref.edit().remove(name).commit();
    }


}
