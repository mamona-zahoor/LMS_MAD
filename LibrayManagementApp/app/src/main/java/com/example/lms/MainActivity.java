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
import com.google.firebase.auth.FirebaseAuth;

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

      //  Toast.makeText(getApplicationContext(), "in main name is" + loginUser_modelClass.name + "role is" + loginUser_modelClass.userrole, Toast.LENGTH_SHORT).show();
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
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new DashboardFragment()).commit();
                break;
            case R.id.nav_allmembers:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new AllMember_Fragment()).commit();
                break;
            case R.id.nav_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new ViewAllBooks_Fragment()).commit();
                break;
//            case R.id.nav_issuedbooks:
//                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new IssueBooks_Fragment()).commit();
//                break;
            case R.id.nav_changepassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new ChangePasswordActivity()).commit();
                break;
            case R.id.nav_logout:
                //    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, new ChangePasswordActivity()).commit();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break; }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}
