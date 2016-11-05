package com.dg_livesports.dg_livesports;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class VideosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String FIREBASE_URL="https://final-dygsports.firebaseio.com", vid, vid2;
    private Firebase firebasedatos;

    private Button b_guardar, b_quitar, b_guardar2, b_quitar2;

    private Firebase firebd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

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

        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);

        b_guardar = (Button) findViewById(R.id.bGuardar);
        b_quitar = (Button) findViewById(R.id.bQuitar);
        b_guardar2 = (Button) findViewById(R.id.bGuardar2);
        b_quitar2 = (Button) findViewById(R.id.bQuitar2);

        b_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vid = toString().valueOf(R.id.video1);
                Videos_data video = new Videos_data(vid);
                firebd = firebasedatos.child("video_"+vid);
                firebd.setValue(video);
            }
        });

        b_quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vid = toString().valueOf(R.id.video1);
                firebd = firebasedatos.child("video_"+vid);
                firebd.removeValue();
            }
        });
/////////////////////////////////////////////////////
        b_guardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vid2 = toString().valueOf(R.id.video2);
                Videos_data video2 = new Videos_data(vid2);
                firebd = firebasedatos.child("video_"+vid2);
                firebd.setValue(video2);
            }
        });

        b_quitar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vid2 = toString().valueOf(R.id.video2);
                firebd = firebasedatos.child("video_"+vid2);
                firebd.removeValue();
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

        if (id == R.id.action_login) {
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}