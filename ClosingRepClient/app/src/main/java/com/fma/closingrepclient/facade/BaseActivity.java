package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.controller.ControllerSetting;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Boolean doCheckLogin = Boolean.TRUE;
    protected FrameLayout mainframe;
    long lastPress;
    private ControllerSetting controllerSetting;
    private TextView txtUserName;
    private TextView txtArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        mainframe =  findViewById(R.id.mainframe);
        setSupportActionBar(toolbar);
        controllerSetting = new ControllerSetting(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        checkLogin();

        View headerView = navigationView.getHeaderView(0);
        txtUserName = headerView.findViewById(R.id.txtUserName);
        txtArea = headerView.findViewById(R.id.txtArea);

        txtUserName.setText(controllerSetting.getUserName());
        txtArea.setText(controllerSetting.getArea());

    }

    private void checkLogin() {
        if (!doCheckLogin) return;

        if (controllerSetting.getUserID() == 0){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (this.isTaskRoot()) {
                exitApplication();
            }else{
                super.onBackPressed();
            }
        }
    }

    private void exitApplication(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_LONG).show();
            lastPress = currentTime;
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_logout) {
            intent = new Intent(this, LoginActivity.class);
        } else if (id == R.id.nav_area) {
            intent = new Intent(this, AreaActivity.class);
        } else if (id == R.id.nav_customer) {
            intent = new Intent(this, CustomerActivity.class);
        } else if (id == R.id.nav_product) {
            intent = new Intent(this, ProductActivity.class);
        } else if (id == R.id.nav_material) {
            intent = new Intent(this, MaterialActivity.class);
        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_tracking_customer) {

        } else if (id == R.id.nav_sync) {
            intent = new Intent(this, SyncActivity.class);
        } else if (id == R.id.nav_daily_report) {

        }

//        if (intent != null){
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(intent);
//        }

        startActivity(intent);
        this.finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
