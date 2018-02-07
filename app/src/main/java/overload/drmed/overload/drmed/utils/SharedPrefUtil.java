package overload.drmed.overload.drmed.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dalbo on 11/05/2017.
 */

public class SharedPrefUtil {

    private static final String SHARED_PREF_CACHES = "shared_pref_caches";
    public static final String LAST_LOGIN = "login_cache";

    public void sharedPrefPutString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPrefs(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String sharedPrefGetString(Context context, String key) {
        return getSharedPrefs(context).getString(key, "");
    }

    private SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREF_CACHES, Context.MODE_PRIVATE);
    }

}