package by.black_pearl.test_cafe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import by.black_pearl.test_cafe.fragments.DishCardFragment;
import by.black_pearl.test_cafe.fragments.ListDishFragment;
import by.black_pearl.test_cafe.fragments.UpgradeFragment;
import by.black_pearl.test_cafe.orm_framework.SugarManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean mIsUpgrading = false;
    private Fragment mCurrentFragment;

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

        Fragment cf = (Fragment) getLastCustomNonConfigurationInstance();
        if(cf != null) {
            this.mCurrentFragment = cf;
            changeFragment(false);
            return;
        }

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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return getSupportFragmentManager().findFragmentById(R.id.content_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(!this.mIsUpgrading) {
        int n = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < n; i++) {
                getSupportFragmentManager().popBackStack();
            }
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

    private void fragmentChanger(SetFragment setFragment) {
        switch (setFragment) {
            case UPGRADE:
                mCurrentFragment = UpgradeFragment.newInstance();
                break;
            case CATALOG:
                mCurrentFragment = CatalogFragment.newInstance();
                break;
            case CONTACTS:
                mCurrentFragment = ContactsFragment.newInstance();
                break;
        }
        changeFragment(false);
    }

    private void changeFragment(boolean toBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, mCurrentFragment);
        if(toBackStack) {
            fragmentTransaction.addToBackStack(mCurrentFragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    public void setIsUpgrading(boolean bool) {
        this.mIsUpgrading = bool;
    }

    public MainActivityFragmentsCallback getFragmentsCallback() {
        return new MainActivityFragmentsCallback() {
            @Override
            public void changeFragmentToListDish(int id) {
                mCurrentFragment = ListDishFragment.newInstance(id);
                changeFragment(true);
            }

            @Override
            public void changeFragmentToCatalog() {
                fragmentChanger(SetFragment.CATALOG);
            }

            @Override
            public void changeFragmentToDishCard(String pictureUrl, String name,
                                                 String price, String weight, String description) {
                mCurrentFragment =
                        DishCardFragment.newInstance(pictureUrl, name, price, weight, description);
                changeFragment(true);
            }
        };
    }

    public enum SetFragment {
        UPGRADE, CATALOG, CONTACTS
    }

    public interface MainActivityFragmentsCallback {
        void changeFragmentToListDish(int id);

        void changeFragmentToCatalog();

        void changeFragmentToDishCard(String pictureUrl, String name,
                                      String price, String weight, String description);
    }
}

