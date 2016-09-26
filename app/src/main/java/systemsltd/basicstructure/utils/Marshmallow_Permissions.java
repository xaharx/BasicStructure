package systemsltd.basicstructure.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import systemsltd.basicstructure.MainActivity;
import systemsltd.basicstructure.Splash;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Raziuddin.Shaikh on 9/22/2016.
 */

public class Marshmallow_Permissions{

    private final static int WRITE_CALENDAR_RESULT = 100;
    private final static int READ_CALENDAR_RESULT  = 101;
    private final static int CAMERA_RESULT  = 102;
    private final static int READ_CONTACTS_RESULT  = 103;
    private final static int WRITE_CONTACTS_RESULT  = 104;
    private final static int ACCESS_FINE_LOCATION_RESULT  = 105;
    private final static int ACCESS_COARSE_LOCATION_RESULT  = 106;
    private final static int CALL_PHONE_RESULT  = 107;
    private final static int WRITE_EXTERNAL_STORAGE_RESULT  = 108;
    private final static int ALL_PERMISSIONS_RESULT  = 109;

    private SharedPreferences sharedPreferences;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected;

    Activity act;

    public Marshmallow_Permissions(Activity act) {

        this.act = act;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(act);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void CheckPermission() {

        ArrayList<String> permissions = new ArrayList<>();
        int resultCode = 0;
        permissions.add(WRITE_CALENDAR);
        permissions.add(READ_CALENDAR);
        permissions.add(CAMERA);
        permissions.add(READ_CONTACTS);
        permissions.add(WRITE_CONTACTS);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(CALL_PHONE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        resultCode = ALL_PERMISSIONS_RESULT;

        permissionsToRequest = findUnAskedPermissions(permissions);

        permissionsRejected = findRejectedPermissions(permissions);

        if (permissionsToRequest.size() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                act.requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), resultCode);
            }

            for (String perm : permissionsToRequest) {
                markAsAsked(perm);
            }
        }
        else
        {
            Log.e("Mars Permissions","ALL ACCEPTED");
            ThreadFn(act);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case WRITE_CALENDAR_RESULT:
                if (hasPermission(WRITE_CALENDAR)) {

                } else {
                    permissionsRejected.add(WRITE_CALENDAR);
                    makePostRequestSnack();
                }
                break;
            case READ_CALENDAR_RESULT:
                if (hasPermission(READ_CALENDAR)) {

                } else {
                    permissionsRejected.add(READ_CALENDAR);
                    makePostRequestSnack();
                }
                break;
            case CAMERA_RESULT:
                if (hasPermission(CAMERA)) {

                } else {
                    permissionsRejected.add(CAMERA);
                    makePostRequestSnack();
                }
                break;
            case READ_CONTACTS_RESULT:
                if (hasPermission(READ_CONTACTS)) {

                } else {
                    permissionsRejected.add(READ_CONTACTS);
                    makePostRequestSnack();
                }
                break;
            case WRITE_CONTACTS_RESULT:
                if (hasPermission(WRITE_CONTACTS)) {

                } else {
                    permissionsRejected.add(WRITE_CONTACTS);
                    makePostRequestSnack();
                }
                break;
            case ACCESS_FINE_LOCATION_RESULT:
                if (hasPermission(ACCESS_FINE_LOCATION)) {

                } else {
                    permissionsRejected.add(ACCESS_FINE_LOCATION);
                    makePostRequestSnack();

                }
                break;
            case ACCESS_COARSE_LOCATION_RESULT:
                if (hasPermission(ACCESS_COARSE_LOCATION)) {

                } else {
                    permissionsRejected.add(ACCESS_COARSE_LOCATION);
                    makePostRequestSnack();
                }
                break;
            case CALL_PHONE_RESULT:
                if (hasPermission(CALL_PHONE)) {

                } else {
                    permissionsRejected.add(CALL_PHONE);
                    makePostRequestSnack();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_RESULT:
                if (hasPermission(WRITE_EXTERNAL_STORAGE)) {

                } else {
                    permissionsRejected.add(WRITE_EXTERNAL_STORAGE);
                    makePostRequestSnack();
                }
                break;
            case ALL_PERMISSIONS_RESULT:
                boolean someAccepted = false;
                boolean someRejected = false;
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {
                        someAccepted = true;
                    } else {
                        someRejected = true;
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    someRejected = true;
                }

                if (someAccepted || someRejected) {

                    Log.e("Mars Permissions","SOME ACCEPTED");

                    ThreadFn(act);

                }
                /*if (someRejected) {
                    makePostRequestSnack();
                }*/
                break;
        }

    }

    private void makePostRequestSnack() {
        for(String perm: permissionsRejected){
            clearMarkAsAsked(perm);
        }
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (act.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean shouldWeAsk(String permission) {
        return (sharedPreferences.getBoolean(permission, true));
    }

    private void markAsAsked(String permission) {
        sharedPreferences.edit().putBoolean(permission, false).apply();
    }

    private void clearMarkAsAsked(String permission) {
        sharedPreferences.edit().putBoolean(permission, true).apply();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
//            if (!hasPermission(perm) && shouldWeAsk(perm)) {
//                result.add(perm);
//            }
            if (!hasPermission(perm) && true) {
                result.add(perm);
            }
        }

        return result;
    }

    private ArrayList<String> findRejectedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm) && !shouldWeAsk(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    public void ThreadFn(Activity act)
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            FirebaseCrash.report(new Exception("My first Android non-fatal error"));
            FirebaseCrash.log("Activity created");
        }

        Intent intent = new Intent(act, MainActivity.class);
        act.startActivity(intent);
        act.finish();
    }
}
