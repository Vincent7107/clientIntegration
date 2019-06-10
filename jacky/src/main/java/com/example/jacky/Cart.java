package com.example.jacky;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

import com.example.jacky.Common.Commons;
import com.example.jacky.Model.Order;
import com.example.jacky.Model.Request;
import com.example.jacky.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.example.jacky.Database.Database;

public class Cart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtFullname;
    public TextView txtTotalPrice;
    Button btnOrderSure;
    ArrayList<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("購物車");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

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

        recyclerView = (RecyclerView)findViewById(R.id.recycler_Cart);
        recyclerView.setHasFixedSize(true);  //unchange RecyclerView size
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnOrderSure = (Button)findViewById(R.id.orderSure);

        btnOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size() == 0)  Toast.makeText(Cart.this, "你還沒點餐啦幹!", Toast.LENGTH_SHORT).show();
                else    showAlertDialog();
            }
        });
        loadListFood();
    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Enter your description");
        //alertDialog.setMessage("Enter your address");

        final EditText editDescription = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        editDescription.setLayoutParams(layoutParams);
        alertDialog.setView(editDescription);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Commons.currentUser.getPhone(),
                        Commons.currentUser.getName(),
                        txtTotalPrice.getText().toString(),
                        cart,
                        editDescription.getText().toString());

                Date date = new Date();
                //submit to firebase
                requests.child(String.valueOf(date)).setValue(request);

                //delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank you for order", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood(){
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart, this);  //取得所有order list
        recyclerView.setAdapter(adapter);

        //count total price
        int total = 0;
        for(Order order:cart){
            total = total + (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            txtTotalPrice.setText(Integer.toString(total));
        }
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
            Intent menuIntent = new Intent(Cart.this, Home.class);
            startActivity(menuIntent);
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Cart.this, Cart.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Cart.this, OrderStatus.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_comments) {
            Intent comment = new Intent(Cart.this, ClientComment.class);
            startActivity(comment);
        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(Cart.this, MainActivity.class);
            //CLEAR_TASK清除此Task的Activity, FLAG_ACTIVITY_NEW_TASK從歷史stack的Task開始
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
