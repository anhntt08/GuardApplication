package safeobject.view.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import safeobject.Manager.TokenManager;
import safeobject.view.Fragment.FeedbackFragment;
import safeobject.view.Fragment.NotificationFragment;
import safeobject.view.PopupResetPassword;
import safeobject.view.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Boolean showFeedback = false;
    private Fragment fragment_content = null;
    private TextView nav_header_username;
    private TokenManager tokenManager;
    private Dialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông báo");

        tokenManager = new TokenManager(getApplicationContext());

        dialog = new Dialog(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);

        nav_header_username = headerview.findViewById(R.id.nav_header_username);
        nav_header_username.setText(tokenManager.getUsernameFromSession());

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PopupResetPassword popupResetPassword = new PopupResetPassword(v.getContext());
            }
        });

        fragment_content = new NotificationFragment();
        showFeedback = false;
        switchFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(showFeedback){
            getMenuInflater().inflate(R.menu.feedback, menu);
            menu.findItem(R.id.action_bar_sendFeedback).setVisible(showFeedback);
        }

        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.navigationSendFeedback).setVisible(showFeedback);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_noti) {
            fragment_content = new NotificationFragment();
            showFeedback = false;
        } else if (id == R.id.nav_feed) {
            fragment_content = new FeedbackFragment();
            showFeedback = true;
        }else if (id == R.id.nav_logout){
            SharedPreferences preferences =getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
        }


        switchFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(){
        invalidateOptionsMenu();
        if (fragment_content != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.home_content_layout, fragment_content);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
