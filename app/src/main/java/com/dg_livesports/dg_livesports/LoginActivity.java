package com.dg_livesports.dg_livesports;

import android.app.ActionBar;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //DominosSQLiteHelper usuarios;
    SQLiteDatabase dbUsuarios;
    ContentValues dataBD;

    Button b_entrar, b_registro;
    EditText et_usuario, et_password;
    TextView t_Registro;

    private String user;
    private String password;
    private String email;
    private String sesion;
    private boolean flag = false;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //usuarios = new DominosSQLiteHelper(this, "DominosBD", null, 1);
        //dbUsuarios = usuarios.getWritableDatabase();

        Bundle extras;

        extras = getIntent().getExtras();

        ////////////////prefencias compartidas/////////////////

        prefs = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        editor = prefs.edit();

        refreshPrefs();
        if (extras != null) {
            sesion = extras.getString("sesion");
            Toast.makeText(this, "Sesiòn "+sesion,Toast.LENGTH_SHORT).show();
            editor.putString("var_sesion",sesion);
            editor.commit();
            flag = true;
        }
        if (sesion.equals("abierta")) {
            Intent intent3 = new Intent(this, MainActivity.class);
            //intent3.putExtra("usuario", user);
            //intent3.putExtra("password", password);
            //intent3.putExtra("email", email);
            startActivity(intent3);
            finish();
        }else if (sesion.equals("cerrada")){
            flag = true;
        }

        ////////////////////////////////////////

        //b_registro = (Button) findViewById(R.id.b_registro);
        b_entrar = (Button) findViewById(R.id.b_entrar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);
        t_Registro = (TextView) findViewById(R.id.t_Registro);


        ////////////////////////////esta parte es solo para recibir
       /* Bundle extras = getIntent().getExtras();//para mandar datos
        String user = extras.getString("usuario");
        String contrasena = extras.getString("contrasena");
        /////////////////////////
        Toast.makeText(this, "user: "+user+"contrasena: "+contrasena,Toast.LENGTH_SHORT).show();
        *///1

        SpannableString content = new SpannableString(t_Registro.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        t_Registro.setText(content);

        t_Registro.setOnClickListener(this);

        //b_registro.setOnClickListener(this);//si aparece error seleccionar en la segunda opcion
        b_entrar.setOnClickListener(this);

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
                if (flag == true){
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

                    Cursor c = dbUsuarios.rawQuery("SELECT * FROM DatosUsuarios WHERE nombre='"+
                            user+"'",null);

                    //if (user.equals(et_usuario.getText().toString())){
                    if (c.moveToFirst()){
                        if (password.equals(c.getString(2))){
                            email = c.getString(3);
                            sesion = "abierta";
                            savePrefs();
                            Intent intent2 = new Intent(this, MainActivity.class);
                            //intent2.putExtra("usuario", user);
                            //intent2.putExtra("password", password);
                            //intent2.putExtra("email", email);
                            startActivity(intent2);
                            finish();
                        }else {
                            Toast.makeText(this, "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        Toast.makeText(this, "Usuario o contraseña incorrecta.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else Toast.makeText(this, "No hay usuarios registrados",Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//2
        if (requestCode == 1234 && resultCode == RESULT_OK){
            //user = data.getExtras().getString("usuario");
            //password = data.getExtras().getString("contrasena");
            //email = data.getExtras().getString("email");

            flag = true;
            /////////////////////////

            //Log.d("user",user);//para mostrar datos en consola android
            //Log.d("contraseña",password);

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
    public void clearPrefs(){
        editor.clear();
        editor.commit();
    }
}