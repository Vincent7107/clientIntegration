package com.example.jacky;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.jacky.Common.Commons;
import com.example.jacky.Model.Food;
import com.example.jacky.Database.Database;
import com.example.jacky.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    TextView food_name, food_price, food_description;
    TextView txtFullname;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId ="";

    Toolbar toolbar;

    FirebaseDatabase database;
    DatabaseReference food;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Firebase
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");

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

        //Init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice()
                ));
                Toast.makeText(FoodDetail.this, "Add to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_description =  (TextView)findViewById(R.id.food_description);
        food_name =  (TextView)findViewById(R.id.food_name);
        food_price =  (TextView)findViewById(R.id.food_price);
        food_image =  (ImageView)findViewById(R.id.img_food);
        
        //Get Food Id from Intent
        if(getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty() && foodId != null)
        {
            loadListFood(foodId);
        }
    }

    private void loadListFood(String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                toolbar.setTitle(currentFood.getName());
                //Set Image
                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_image);
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
            Intent menuIntent = new Intent(FoodDetail.this, Home.class);
            startActivity(menuIntent);
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(FoodDetail.this, Cart.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(FoodDetail.this, OrderStatus.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_comments) {
            Intent comment = new Intent(FoodDetail.this, ClientComment.class);
            startActivity(comment);
        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(FoodDetail.this, MainActivity.class);
            //CLEAR_TASK清除此Task的Activity, FLAG_ACTIVITY_NEW_TASK從歷史stack的Task開始
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
