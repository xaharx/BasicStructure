package systemsltd.basicstructure.slidemenu;

/**
 * Created by Raziuddin.Shaikh on 9/22/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import systemsltd.basicstructure.MainActivity;
import systemsltd.basicstructure.R;
import systemsltd.basicstructure.viewpager.ViewPagerSample;
import systemsltd.basicstructure.webservices.WebService_Fragment;
import systemsltd.basicstructure.recyclerview.RecyclerViewSample;

public class SlidingMenuFragment extends Fragment {

    RecyclerView recyclerView;

    public SlidingMenuFragment() {
        // Empty constructor required
    }

    public static SlidingMenuFragment newInstance() {
        return new SlidingMenuFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sliding_menu, parent, false);


        ImageView userAvatar = (ImageView) rootView.findViewById(R.id.user_avatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "User Avatar Clicked", Toast.LENGTH_SHORT).show();

                if(MainActivity.Drawer.isDrawerOpen(Gravity.RIGHT))
                {
                    ((MainActivity)getActivity()).navigation_toggleMenu();
                }
                else
                {
                    ((MainActivity)getActivity()).slidemenu_toggleMenu();
                }
            }
        });


        recyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuRecyclerViewAdapter menuAdapter = new MenuRecyclerViewAdapter(getData(), getContext());
        recyclerView.setAdapter(menuAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "Item Num: " + position + " Clicked", Toast.LENGTH_SHORT).show();

                // Hide the menu when item clicked

                if(MainActivity.Drawer.isDrawerOpen(Gravity.RIGHT))
                {
                    ((MainActivity)getActivity()).navigation_toggleMenu();
                }
                else
                {
                    ((MainActivity)getActivity()).slidemenu_toggleMenu();
                }

                if(position == 0)
                {
                    ((MainActivity)getActivity()).replaceMainContainer(new WebService_Fragment());
                }
                else if(position == 1)
                {
                    ((MainActivity)getActivity()).replaceMainContainer(new RecyclerViewSample());
                }
                else if(position == 2)
                {
                    ((MainActivity)getActivity()).replaceMainContainer(new WebService_Fragment());
                }
                else if(position == 3)
                {
                    ((MainActivity)getActivity()).replaceMainContainer(new ViewPagerSample());
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "Item Num: " + position + " Clicked", Toast.LENGTH_SHORT).show();
            }
        }));

        return rootView;
    }

    private List<ItemMenu> getData() {
        List<ItemMenu> menuList = new ArrayList<>();
        menuList.add(new ItemMenu("WebService", R.mipmap.ic_launcher));
        menuList.add(new ItemMenu("RecyclerView", R.mipmap.ic_launcher));
        menuList.add(new ItemMenu("Map", R.mipmap.ic_launcher));
        menuList.add(new ItemMenu("ViewPager", R.mipmap.ic_launcher));

        return menuList;
    }

    // Recycler view touch lister class
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }
}
