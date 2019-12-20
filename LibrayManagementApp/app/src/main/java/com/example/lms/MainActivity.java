package com.example.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginUser_ModelClass loginUser_modelClass = new LoginUser_ModelClass();
        loginUser_modelClass.name = getIntent().getStringExtra("name");
        loginUser_modelClass.userrole = getIntent().getStringExtra("userrole");
        //FrameLayout frameLayout=(FrameLayout)findViewById(R.id.mainframe);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainframe, new DashboardFragment());
        fragmentTransaction.commit();

        Toast.makeText(getApplicationContext(), "in main name is" + loginUser_modelClass.name + "role is" + loginUser_modelClass.userrole, Toast.LENGTH_SHORT).show();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.dashboardToolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_allmembers:

                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new AllMember_Fragment()).commit();

                break;
            case R.id.nav_books:
                //   getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,new AllMember_Fragment()).commit();
                break;


        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}
