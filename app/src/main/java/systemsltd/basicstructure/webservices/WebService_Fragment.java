package systemsltd.basicstructure.webservices;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import systemsltd.basicstructure.R;
import systemsltd.basicstructure.user.UserModel;
import systemsltd.basicstructure.utils.AsyncHttpClient;
import systemsltd.basicstructure.utils.CommonObjects;

/**
 * Created by Raziuddin.Shaikh on 9/22/2016.
 */

public class WebService_Fragment extends Fragment {

    View mView;

    Snackbar snack;

    ProgressDialog progressDialog;

    AsyncHttpClient asyncHttpClient;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1)
            {
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
            else if (msg.what == 2)
            {
                if (progressDialog != null)
                    progressDialog.dismiss();
                // Errors
                snackMsgs("Error, Please try again later");
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = getActivity().getLayoutInflater().inflate(R.layout.fragment, null);

        //// Retrofit
        Retrofit_WebServiceFN("xyz","1");


        /// AsyncTask
        asyncHttpClient = new AsyncHttpClient();
        Async_WebServiceFN("xyz","1");

        return mView;
    }


    public void Retrofit_WebServiceFN(String token,String id)
    {
        if (progressDialog == null) {
            progressDialog = CommonObjects.createProgressDialog(getActivity());
            progressDialog.show();
        } else {
            progressDialog.show();
        }

        HashMap params = new HashMap<String,String>();
        params.put("token",token);
        params.put("id",id);

        List TempList = new ArrayList<UserModel>();

        Gson gson = new Gson();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(params));

        Call<ResponseBody> call = CommonObjects.retrofitSettings(CommonObjects.base_url).login(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                try
                {
                    Log.e("Response",response.body().string().toString()+" ");

                    JSONObject jObj = new JSONObject(response.body().string().toString());

                    Gson gson = new Gson();

                    if(jObj.get("error").equals("false"))
                    {
                        android.os.Message alertMessage = new android.os.Message();
                        alertMessage.what = 1;
                        handle.sendMessage(alertMessage);
                    }
                    else
                    {
                        android.os.Message alertMessage = new android.os.Message();
                        alertMessage.what = 2;
                        handle.sendMessage(alertMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    android.os.Message alertMessage = new android.os.Message();
                    alertMessage.what = 2;
                    handle.sendMessage(alertMessage);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                android.os.Message alertMessage = new android.os.Message();
                alertMessage.what = 2;
                handle.sendMessage(alertMessage);
            }
        });

    }


    public void Async_WebServiceFN(String token,String id)
    {
        List<UserModel> TempList = new ArrayList<UserModel>();

        String url = CommonObjects.base_url+"/getNewsfeed";

        HashMap params = new HashMap<String,String>();
        params.put("token",token);
        params.put("id",id);

        asyncHttpClient.executeWithoutProgressDialog(getActivity(), "News Feed All", url, params, new AsyncHttpClient.AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try
                {
                    JSONObject jObj = new JSONObject(response);

                    Gson gson = new Gson();

                    if(jObj.get("error").equals("false"))
                    {
                        android.os.Message alertMessage = new android.os.Message();
                        alertMessage.what = 1;
                        handle.sendMessage(alertMessage);
                    }
                    else
                    {
                        android.os.Message alertMessage = new android.os.Message();
                        alertMessage.what = 2;
                        handle.sendMessage(alertMessage);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    android.os.Message alertMessage = new android.os.Message();
                    alertMessage.what = 2;
                    handle.sendMessage(alertMessage);
                }
            }

            @Override
            public void onFailure(String response) {
                android.os.Message alertMessage = new android.os.Message();
                alertMessage.what = 2;
                handle.sendMessage(alertMessage);
            }
        });
    }

    public void snackMsgs(String msg)
    {
        snack = Snackbar.make(mView, msg, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

       /* snack.setAction("Refresh",new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                snack.dismiss();

            }
        });*/

        snack.show();
    }
}
