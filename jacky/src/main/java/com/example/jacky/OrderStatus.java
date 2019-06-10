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
import com.example.jacky.Model.Request;
import com.example.jacky.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("訂單");
        setSupportActionBar(toolbar);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(OrderStatus.this, Cart.class);
                startActivity(cartIntent);
            }
        });

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

        //設置recycleView排版
        recyclerView = (RecyclerView)findViewById(R.id.recycler_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);  //LinearLayoutManager 線性排列
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Commons.currentUser.getPhone());
    }

    private void loadOrders(String phone){
        //FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.item_order,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)){

                @Override
                protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position){
                    viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                    viewHolder.txtOrderStatus.setText(Commons.convertCodeToStatus(model.getStatus()));
                }
        };
        recyclerView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Intent menuIntent = new Intent(OrderStatus.this, Home.class);
            startActivity(menuIntent);
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(OrderStatus.this, Cart.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(OrderStatus.this, OrderStatus.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_comments) {
            Intent comment = new Intent(OrderStatus.this, ClientComment.class);
            startActivity(comment);
        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(OrderStatus.this, MainActivity.class);
            //CLEAR_TASK清除此Task的Activity, FLAG_ACTIVITY_NEW_TASK從歷史stack的Task開始
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
