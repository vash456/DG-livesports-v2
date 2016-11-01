package com.dg_livesports.dg_livesports;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private Button b_aceptar, b_cancelar;
    private EditText et_usuario, et_password1, et_password2, et_email;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        b_aceptar = (Button) findViewById(R.id.b_aceptar);
        b_cancelar = (Button) findViewById(R.id.b_cancelar);

        b_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        b_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
