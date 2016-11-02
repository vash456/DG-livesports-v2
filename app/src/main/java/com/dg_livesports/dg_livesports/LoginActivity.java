package com.dg_livesports.dg_livesports;

//import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Usuarios_data> info;

    private String FIREBASE_URL="https://final-dygsports.firebaseio.com";
    private Firebase firebasedatos;

    Button b_entrar;
    EditText et_usuario, et_password;
    TextView t_Registro;

    private String user;
    private String password;
    private String email;
    private String sesion;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        info = new ArrayList<>();
        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);

        Bundle extras;

        extras = getIntent().getExtras();

        ////////////////prefencias compartidas/////////////////

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


        ////////////////////////////////////////

        b_entrar = (Button) findViewById(R.id.b_entrar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);
        t_Registro = (TextView) findViewById(R.id.t_Registro);



        SpannableString content = new SpannableString(t_Registro.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        t_Registro.setText(content);

        t_Registro.setOnClickListener(this);

        b_entrar.setOnClickListener(this);

        ////// botón de atrás////////
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //actionbar transparente
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        setStatusBarTranslucent(true);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.t_Registro:
                Intent intent3 = new Intent(this, RegistroActivity.class);
                //startActivity(intent);//1
                startActivityForResult(intent3, 1234);//2
                break;
            //case R.id.b_registro:
            //Intent intent = new Intent(this, RegistroActivity.class);
            //startActivity(intent);//1
            //startActivityForResult(intent, 1234);//2
            //    break;

            case R.id.b_entrar:

                if (TextUtils.isEmpty(et_usuario.getText().toString())) {
                    et_usuario.setError("Este campo no puede estar vacio");
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    et_password.setError("Este campo no puede estar vacio");
                    return;
                }

                user = et_usuario.getText().toString();
                password = et_password.getText().toString();

                //verifica si ya existe el contacto
                final String user1 = "Usuarios_data"+user;

                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user1).exists()){
                            Log.d("DATOSREGISTRO",dataSnapshot.child(user1).getValue().toString());
                            Toast.makeText(getApplicationContext(),dataSnapshot.child(user1).getValue().toString(),Toast.LENGTH_SHORT).show();

                            Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                            //System.out.println("Author: " + newPost.get("user"));
                            //Usuarios_data usuarios_data = dataSnapshot.child(user1).getValue(Usuarios_data.class);
                            Toast.makeText(getApplicationContext(),"Author: " + newPost.getClass().toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"Author: " + dataSnapshot.child(user1).getValue(Usuarios_data.class).toString(),Toast.LENGTH_SHORT).show();

                            //usuarios_data = dataSnapshot.child(user1).getValue(Usuarios_data.class);
                            //info.add(dataSnapshot.child("Usuarios_data"+user).getValue(Usuarios_data.class));
                            //if (password.equals(info.get(0).getUser())){
                                //email = info.get(0).getEmail();
                            /*if (password.equals(usuarios_data.getUser())){
                                email = usuarios_data.getEmail();
                                sesion = "abierta";
                                savePrefs();
                                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent2);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                                return;
                            }*/

                        }else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//2
        if (requestCode == 1234 && resultCode == RESULT_OK){
            //user = data.getExtras().getString("usuario");

            editor.putString("var_sesion","cerrada");
            editor.commit();

            Toast.makeText(this, "Usuario registrado exitosamente.",Toast.LENGTH_SHORT).show();
        }

        if (requestCode==1234 && resultCode == RESULT_CANCELED){
            Log.d("mensaje","no se cargaron datos");
        }

        super.onActivityResult(requestCode, resultCode, data);
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
        sesion = String.valueOf(prefs.getString("var_sesion","sesion no definida"));
    }

    ////// botón de atrás////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///transparencias actionbar
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}