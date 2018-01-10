package com.example.hellomvpworld.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.hellomvpworld.R;
import com.example.hellomvpworld.data.source.UsersRepository;
import com.example.hellomvpworld.data.source.remote.UsersRemoteDataSource;
import com.example.hellomvpworld.util.ActivityUtils;

/**
 * Created by Hwang on 2018-01-04.
 *
 * Description :
 */
public class MainActivity extends AppCompatActivity {
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();

        // Create the fragment
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        if (fragment == null) {
            fragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_main);
        }

        // Create the presenter
        presenter = new MainPresenter(
                UsersRepository.getInstance(UsersRemoteDataSource.getInstance()),
                fragment
        );

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            savedInstanceState.getSerializable("");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("", "");
        super.onSaveInstanceState(outState);
    }
}
