package com.veechand.financeapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActionChooserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button viewTransaction;
    private Button addTransaction;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_chooser);

        /*
        viewTransaction = (Button) findViewById(R.id.viewTransactionButton);
        addTransaction = (Button) findViewById(R.id.addTransactionButton);

        viewTransaction.setOnClickListener(this);
        addTransaction.setOnClickListener(this);
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actions = getResources().getStringArray(R.array.actions);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, actions));
        mActivityTitle = getTitle().toString();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });
        setupDrawer();
        displayView(1); //chosing the view transaction fragment on start
    }

    private void displayView(int position){
        Fragment fragment = null;
        switch (position) {
            case 0:
                Log.i("Cases ","Case 0");
                break;
            case 1:
                Log.i("Cases","Case 1");
                fragment = new ViewTransactionFragment();
                break;
            case 2:
                Log.i("Cases","Case 2");
                fragment = new TransactionSubTypeAdd();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            Log.i("Title set is ",actions[position]);
            getSupportActionBar().setTitle(actions[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }
    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.viewTransactionButton:
                Intent viewTransaction = new Intent(ActionChooserActivity.this,ViewTransactionFragment.class);
                startActivity(viewTransaction);
                break;
            case R.id.addTransactionButton:
                Intent addTransactionIntent = new Intent(ActionChooserActivity.this,AddTransactionActivity.class);
                startActivity(addTransactionIntent);
                break;
        }*/
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: Check do we need to show this on all the toolbar
      //  getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*switch (item.getItemId()){
            case R.id.menu_transaction_subtype_add:
                Intent intent = new Intent(ActionChooserActivity.this, TransactionSubTypeAdd.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }


}
