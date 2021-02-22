package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagramclone.FrameAll.AddFragment;
import com.example.instagramclone.FrameAll.HomeFragment;
import com.example.instagramclone.FrameAll.ProfileFragment;
import com.example.instagramclone.FrameAll.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameall,
                new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                    }
                    switch (item.getItemId()){
                        case R.id.nav_search:
                            selectedFragment=new SearchFragment();
                            break;

                    }
                    switch (item.getItemId()){
                        case R.id.nav_add:
                            selectedFragment=new AddFragment();
                            break;

                    }

                    switch (item.getItemId()){
                        case R.id.nav_person:
                            SharedPreferences.Editor editor=getSharedPreferences("PRESS",MODE_PRIVATE).edit();
                             editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    if (selectedFragment!=null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameall,selectedFragment).commit();



                    return true;
                }
            };


}