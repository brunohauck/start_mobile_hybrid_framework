package com.mentorandroid.startmhf;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mentorandroid.startmhf.fragments.BlankFragment;
import com.mentorandroid.startmhf.fragments.HelloFragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instancia o menu navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


    /*  Este codigo esta comentando pois eu nao tenho necessidade de outro menu na barra principal do cabecalho
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Nesta parte do codigo voce deve tratar as acoes do menu lateral
        int id = item.getItemId();
        // Esse ID ou identificador esta diretamente ligado ao arquivo /res/menu/activity_main_drawer
        /* copia do arquivo de menu
         <item
            android:id="@+id/nav_about" ---> esse seria o ID que esta no if que vai diferenciar qual acao tomar ao clicar no menu
            android:icon="@drawable/ic_info_black_24dp"
            android:title="Sobre" />
         */
        if (id == R.id.nav_about) {
            //Instancia o fragment
            Fragment fragment = new BlankFragment();
            String title = "HTML 5 Local";
            if (fragment != null) {
                //Faz a chamada ao fragmente e mostra o mesmo na tela principal apos clicar no menu
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, fragment);
                ft.commit();
                if (getSupportActionBar() != null) {
                    //Troca o titulo do cabecalho da barra de menu
                    getSupportActionBar().setTitle(title);
                }
            }
        } else if (id == R.id.nav_share) {
            Fragment fragment = new HelloFragment();
            String title = "HELLO FRAGMENT";
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, fragment);
                ft.commit();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }
            }

        }
        else if (id == R.id.nav_share1) {
            Fragment fragment = new HelloFragment();
            String title = "HELLO FRAGMENT";
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, fragment);
                ft.commit();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(title);
                }
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //------------------ this asynctask is to ping to network before loading the webview ----------

    public static boolean pingURL(String url, int timeout) {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public interface MyInterface {
        public void myMethod(boolean result);
    }

    public class NetworkUtil extends AsyncTask<String, Void, Boolean> {
        private MyInterface mListener;

        String _url;
        public NetworkUtil(String url, MyInterface mListener) {
            _url = url;
            this.mListener  = mListener;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            if (pingURL(_url, 1000)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (mListener != null)
                mListener.myMethod(result);

        }

    }

    //----------------- end of asynctask to ping google -----------------------------

}
