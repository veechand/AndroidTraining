package com.veechand.menuexample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    RelativeLayout parentLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            Log.d("Version tag", "Gingerbeard version");
        }
        parentLayout = (RelativeLayout) findViewById(R.id.layoutParent);
        imageView = (ImageView) findViewById(R.id.imageView);
        registerForContextMenu(imageView);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        getMenuInflater().inflate(R.menu.context_menu,menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        itemSelected(item);
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                    }
                });
                return true;
            }
        });
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_black) {
            parentLayout.setBackgroundColor(Color.BLACK);
        }
        if (id == R.id.menu_green){
            parentLayout.setBackgroundColor(Color.GREEN);
        }
        if (id == R.id.menu_red){
            parentLayout.setBackgroundColor(Color.RED);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.imageView) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        itemSelected(item);
        return super.onContextItemSelected(item);
    }

    private void itemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.image_1:
                imageView.setBackgroundResource(R.drawable.a1);
                break;
            case R.id.image_2:
                imageView.setBackgroundResource(R.drawable.a2);

                break;
            case R.id.image_3:
                imageView.setBackgroundResource(R.drawable.a3);
                break;
        }
    }
}
