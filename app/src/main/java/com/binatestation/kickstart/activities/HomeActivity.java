package com.binatestation.kickstart.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.binatestation.kickstart.R;
import com.binatestation.kickstart.fragments.AlbumListFragment;
import com.binatestation.kickstart.fragments.CommentListFragment;
import com.binatestation.kickstart.fragments.PhotoListFragment;
import com.binatestation.kickstart.fragments.PostListFragment;
import com.binatestation.kickstart.fragments.TodoListFragment;
import com.binatestation.kickstart.fragments.UserListFragment;
import com.binatestation.kickstart.utils.Session;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mLoadedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_posts);
        if (navigationView.getCheckedItem() != null)
            onNavigationItemSelected(navigationView.getCheckedItem());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Session.logout(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_posts) {
            loadFragment(PostListFragment.newInstance(), PostListFragment.TAG, false);
        } else if (id == R.id.nav_comments) {
            loadFragment(CommentListFragment.newInstance(), CommentListFragment.TAG, false);
        } else if (id == R.id.nav_albums) {
            loadFragment(AlbumListFragment.newInstance(), AlbumListFragment.TAG, false);
        } else if (id == R.id.nav_photos) {
            loadFragment(PhotoListFragment.newInstance(), PhotoListFragment.TAG, false);
        } else if (id == R.id.nav_todos) {
            loadFragment(TodoListFragment.newInstance(), TodoListFragment.TAG, false);
        } else if (id == R.id.nav_users) {
            loadFragment(UserListFragment.newInstance(), UserListFragment.TAG, false);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
        mLoadedFragment = fragment;
    }
}
