package com.example.jacky;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jacky.Common.Commons;
import com.example.jacky.Model.Comments;


import java.util.List;

public class ClientComment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullname;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("評分");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for User, 讓nav的名稱設為User的name
        View headerView = navigationView.getHeaderView(0);
        txtFullname = (TextView)headerView.findViewById(R.id.txtFullname);
        txtFullname.setText(Commons.currentUser.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_comments);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        new FirebaseDatabaseHelper().readMenu(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Comments> comment, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, ClientComment.this, comment, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foodlist_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_comment:
                startActivity(new Intent(new Intent(this, NewComment.class)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent menuIntent = new Intent(ClientComment.this, Home.class);
            startActivity(menuIntent);
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(ClientComment.this, Cart.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(ClientComment.this, OrderStatus.class);
            startActivity(orderIntent);
        }else if (id == R.id.nav_comments) {
            Intent comment = new Intent(ClientComment.this, ClientComment.class);
            startActivity(comment);
        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(ClientComment.this, MainActivity.class);
            //CLEAR_TASK清除此Task的Activity, FLAG_ACTIVITY_NEW_TASK從歷史stack的Task開始
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
