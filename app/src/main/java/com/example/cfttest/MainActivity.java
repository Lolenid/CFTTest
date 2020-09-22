package com.example.cfttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_BUTTON = "button";
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }

    public void onClickLeftButton(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BUTTON, "left");
        navController.navigate(R.id.action_convertor_to_valuteList, bundle);
    }

    public void onClickRightButton(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BUTTON, "right");
        navController.navigate(R.id.action_convertor_to_valuteList, bundle);
    }
}