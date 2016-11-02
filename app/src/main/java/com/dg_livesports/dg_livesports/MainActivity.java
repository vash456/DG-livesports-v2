package com.dg_livesports.dg_livesports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String user;
    private String password;
    private String email;
    private String sesion;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////preferencias compartidas y cerrar sesion////////

        Bundle extras;

        extras = getIntent().getExtras();

        prefs = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        editor = prefs.edit();

        refreshPrefs();
        if (extras != null) {
            sesion = extras.getString("sesion");
            Toast.makeText(this, "Sesiòn "+sesion,Toast.LENGTH_SHORT).show();
            user = "Invitado";
            password = "";
            email = "";
            savePrefs();
            editor.putString("var_sesion",sesion);
            editor.commit();
        }
        if (sesion.equals("abierta")) {
            //Intent intent3 = new Intent(this, MainActivity.class);
            //startActivity(intent3);
            //finish();
        }else if (sesion.equals("cerrada")){
            user = "Invitado";
            password = "";
            email = "";
            savePrefs();
        }


        //////navigation drawer///////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////datos usuario en navigation////////

        View hView =  navigationView.getHeaderView(0);
        TextView t_nav_user = (TextView) hView.findViewById(R.id.t_nav_user);
        t_nav_user.setText(user);
        TextView t_nav_email = (TextView) hView.findViewById(R.id.t_nav_email);
        t_nav_email.setText(email);

        ///////tabs///////
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_principal:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_resultados:
                Intent intent2 = new Intent(getApplicationContext(), ResultadosActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.nav_partidos_tablas:
                Intent intent3 = new Intent(getApplicationContext(), ParttablasActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.nav_noticias:
                Intent intent4 = new Intent(getApplicationContext(), NoticiasActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.nav_videos:
                Intent intent5 = new Intent(getApplicationContext(), VideosActivity.class);
                startActivity(intent5);
                finish();
                break;
            case R.id.nav_social:
                Intent intent6 = new Intent(getApplicationContext(), SocialActivity.class);
                startActivity(intent6);
                finish();
                break;
            case R.id.nav_notificaciones:
                Intent intent7 = new Intent(getApplicationContext(), NotificacionesActivity.class);
                startActivity(intent7);
                finish();
                break;
            case R.id.nav_configuracion:
                Intent intent8 = new Intent(getApplicationContext(), ConfiguracionesActivity.class);
                startActivity(intent8);
                finish();
                break;
            case R.id.nav_cerrar_sesion:
                Intent intent9 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent9);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new AyerFragment();
                case 1 : return new AyerFragment();
                case 2 : return new AyerFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "AYER";
                case 1 :
                    return "HOY";
                case 2 :
                    return "MAÑANA";
            }
            return null;
        }
    }

    public void savePrefs(){
        editor.putString("var_name",user);
        editor.putString("var_pass",password);
        editor.putString("var_email",email);
        editor.putString("var_sesion",sesion);
        editor.commit();
    }
    public void refreshPrefs(){
        user = String.valueOf(prefs.getString("var_name","Nombre no definido"));
        password = String.valueOf(prefs.getString("var_pass","contraseña no definida"));
        email = String.valueOf(prefs.getString("var_email","Email no definido"));
        sesion = String.valueOf(prefs.getString("var_sesion","cerrada"));
    }

}


