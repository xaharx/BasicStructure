package systemsltd.basicstructure;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

/**
 * Created by Raziuddin.Shaikh on 9/22/2016.
 */

public class Application  extends android.app.Application {

    @Override
    public void onCreate() {

        MultiDex.install(this);

        super.onCreate();

        Log.e("Application","START");

       // Firebase.setAndroidContext(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}


