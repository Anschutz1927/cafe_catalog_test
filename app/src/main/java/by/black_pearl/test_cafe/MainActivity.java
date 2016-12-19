package by.black_pearl.test_cafe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.orm.SugarContext;

import by.black_pearl.test_cafe.fragments.CatalogFragment;
import by.black_pearl.test_cafe.fragments.ContactsFragment;
import by.black_pearl.test_cafe.fragments.UpgradeFragment;
import by.black_pearl.test_cafe.orm_framework.SugarManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mIsUpgrading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SugarContext.init(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(!SugarManager.isDbExist(this)) {
            this.mIsUpgrading = true;
            fragmentChanger(SetFragment.UPGRADE);
        }
        else {
            fragmentChanger(SetFragment.CATALOG);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(!this.mIsUpgrading) {
            switch (item.getItemId()) {
                case R.id.nav_catalog:
                    fragmentChanger(SetFragment.CATALOG);
                    break;
                case R.id.nav_contacts:
                    fragmentChanger(SetFragment.CONTACTS);
                    break;
            }
        }
        else {
            Toast.makeText(this, "Пожалуйста, дождитесь окончания обновления базы.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fragmentChanger(SetFragment setFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (setFragment) {
            case UPGRADE:
                Fragment fragment = UpgradeFragment.newInstance();
                fragmentTransaction.replace(R.id.content_main, fragment);
                break;
            case CATALOG:
                fragmentTransaction.replace(R.id.content_main, CatalogFragment.newInstance());
                break;
            case CONTACTS:
                fragmentTransaction.replace(R.id.content_main, ContactsFragment.newInstance());
                break;
        }
        fragmentTransaction.commit();
    }

    public void setIsUpgrading(boolean bool) {
        this.mIsUpgrading = bool;
    }

    public enum SetFragment { UPGRADE, CATALOG, CONTACTS }
}

