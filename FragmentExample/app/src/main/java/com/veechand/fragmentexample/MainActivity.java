package com.veechand.fragmentexample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements TopFragmentMessagePasser{
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.topFragment,new TopFragment());
        transaction.add(R.id.bottomFragment,new BottomFragment());

        transaction.commit();

    }

    @Override
    public void setTitle(String title) {
        BottomFragment bottomFragment = (BottomFragment) fragmentManager.findFragmentById(R.id.bottomFragment);
        bottomFragment.setTitle(title);
    }
}
