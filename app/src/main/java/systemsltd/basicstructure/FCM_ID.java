package systemsltd.basicstructure;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import systemsltd.basicstructure.utils.CommonObjects;


/**
 * Created by Amir.jehangir on 9/5/2016.
 */
public class FCM_ID extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

  //  protected BasePreferenceHelper prefHelper = new BasePreferenceHelper(this);
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        CommonObjects.fcm_token = refreshedToken;

    }

}
