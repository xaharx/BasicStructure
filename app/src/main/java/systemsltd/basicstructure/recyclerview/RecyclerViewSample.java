package systemsltd.basicstructure.recyclerview;

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
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;
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
import systemsltd.basicstructure.utils.BaseEndlessAdapter;
import systemsltd.basicstructure.utils.CommonObjects;

/**
 * Created by Raziuddin.Shaikh on 9/22/2016.
 */

public class RecyclerViewSample extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    View mView;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    private RAAdapter adapter;

    LinearLayoutManager mLayoutManager;

    NestedScrollView sv;

    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;

    int pageCount = -1;
    boolean pageData=true;

    Snackbar snack;

    View parentLayout;

    List<UserModel> tempList;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1)
            {
                List<UserModel> sampleList = new ArrayList<UserModel>();
                sampleList = setDummyData(20);

                for(int i=0;i<sampleList.size();i++) {
                    adapter.addData(sampleList.get(i));
                }
                // FINISH LOADING
                endlessRecyclerViewAdapter.onDataReady(false);
            }
            else if (msg.what == 2)
            {
                // Errors
                snackMsgs("Error, Please try again later");
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = getActivity().getLayoutInflater().inflate(R.layout.fragment_recyclerview, null);

        parentLayout = mView.findViewById(R.id.rl);

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);

        sv = (NestedScrollView) mView.findViewById(R.id.scrollView);

        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);

        tempList = new ArrayList<UserModel>();

        adapter = new RAAdapter(getActivity(), tempList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setNestedScrollingEnabled(false);

        endlessRecyclerViewAdapter = new BaseEndlessAdapter(getActivity(), adapter,
                new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                pageCount++;
                                // if(pageCount < 3)

                                if(pageData)
                                {
                                    WebService();
                                }
                                else
                                {
                                    endlessRecyclerViewAdapter.onDataReady(false);
                                }

                            }
                        }, 2000);
                    }
                }
        );

        recyclerView.setAdapter(endlessRecyclerViewAdapter);

        settingsFn();

        return mView;
    }


    public void settingsFn()
    {
        sv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                scrollY = sv.getScrollY();
                if(scrollY == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);

                View view = (View) sv.getChildAt(sv.getChildCount() - 1);
                int diff = (view.getBottom() - (sv.getHeight() + sv.getScrollY()));

                if (diff <= 10) {
                    endlessRecyclerViewAdapter.onDataReady(true);
                }
            }
        });

        setupPullToRefresh();
    }

    void setupPullToRefresh(){

        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue,       //This method will rotate
                R.color.red,        //colors given to it when
                R.color.yellow,     //loader continues to
                R.color.green);     //refresh.

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

        pageData = true;
        pageCount = -1;

        adapter.clearData();
        adapter.notifyDataSetChanged();
        endlessRecyclerViewAdapter.onDataReady(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

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


    public void WebService()
    {
        try
        {
            android.os.Message alertMessage = new android.os.Message();
            alertMessage.what = 1;
            handle.sendMessage(alertMessage);
        }catch(Exception e)
        {
            e.printStackTrace();
            android.os.Message alertMessage = new android.os.Message();
            alertMessage.what = 2;
            handle.sendMessage(alertMessage);
        }
    }

    public List<UserModel> setDummyData(int count)
    {
        List<UserModel> sampleList = new ArrayList<UserModel>();

        for(int i=0;i<count;i++) {

            UserModel uObj = new UserModel();
            uObj.name = "Testing Name";
            sampleList.add(uObj);
        }

        return sampleList;
    }
}
