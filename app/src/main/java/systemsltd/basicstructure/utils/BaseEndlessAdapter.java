package systemsltd.basicstructure.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import systemsltd.basicstructure.R;


/**
 * Created by sheeraz on 1/29/2016.
 */
public class BaseEndlessAdapter extends EndlessRecyclerViewAdapter {
    public BaseEndlessAdapter(Context context, RecyclerView.Adapter wrapped, RequestToLoadMoreListener requestToLoadMoreListener) {
        super(context, wrapped, requestToLoadMoreListener, R.layout.custom_item_loading, true);
    }
    /*public BaseEndlessAdapter(Context context, RecyclerView.Adapter wrapped, @LayoutRes int pendingViewResId,RequestToLoadMoreListener requestToLoadMoreListener) {
        super(context, wrapped, requestToLoadMoreListener, pendingViewResId, true);
    }
*/
}
