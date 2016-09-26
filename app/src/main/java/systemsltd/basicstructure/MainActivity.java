package systemsltd.basicstructure;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import systemsltd.basicstructure.recyclerview.RecyclerViewSample;
import systemsltd.basicstructure.slidemenu.SlidingMenuFragment;

public class MainActivity extends AppCompatActivity {

    SlidingMenu menu;
    public static DrawerLayout Drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //////////// Slider Menu ////////////////
        ImageView sliderBtn = (ImageView) findViewById(R.id.sliderBtn);
        sliderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidemenu_toggleMenu();
            }
        });

        initializeSlideMenu(SlidingMenu.LEFT);


        //////// Navigation Drawer ///////////////

        ImageView naviBtn = (ImageView) findViewById(R.id.naviBtn);
        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigation_toggleMenu();
            }
        });

        initializeNavigationDrawer();


        replaceMainContainer(new RecyclerViewSample());
    }

    public void slidemenu_toggleMenu() {
        menu.toggle();
    }

    public void initializeSlideMenu(int value)
    {
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(16);
        menu.setBehindOffset(200);
        menu.setFadeDegree(0.35f);
        menu.setMode(value);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.fragment_sliding_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, SlidingMenuFragment.newInstance()).commit();
    }


    public void navigation_toggleMenu() {
        if(!Drawer.isDrawerOpen(Gravity.RIGHT))
            Drawer.openDrawer(Gravity.RIGHT);
        else
            Drawer.closeDrawer(Gravity.RIGHT);
    }

    public void initializeNavigationDrawer()
    {
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        Drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, SlidingMenuFragment.newInstance()).commit();
    }


    public void replaceMainContainer(Fragment frag)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
    }
}
