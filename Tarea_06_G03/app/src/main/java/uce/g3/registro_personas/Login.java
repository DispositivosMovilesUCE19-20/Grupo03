package uce.g3.registro_personas;

import OpenHelper.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


    TextView txtRegistrese ;
    Button btnLogin;
    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);
    EditText usuario, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permisoParaEscribirArchivos();
        usuario = (EditText) findViewById(R.id.edtUsuario);
        clave = (EditText) findViewById(R.id.edtPassword);
        txtRegistrese = (TextView) findViewById(R.id.txtRegistrese);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usuario = (EditText) findViewById(R.id.edtUsuario);
                EditText passwd = (EditText) findViewById(R.id.edtPassword);
                try{
                    Cursor cursor =  base.login(usuario.getText().toString(),passwd.getText().toString());
                    if (cursor.getCount()>0){
                        archivoPreferenciasCompartidas();
                        Intent i = new Intent(Login.this,ListarPersonas.class);
                        //i.putExtra("usuario",usuario.getText().toString());
                       // i.putExtra("clave",clave.getText().toString());
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(),"Usuario o Contraseña Incorrectos..!!!",Toast.LENGTH_LONG).show();
                    }

                    usuario.setText("");
                    passwd.setText("");
                    usuario.findFocus();
                }catch(SQLException e){
                    e.printStackTrace();
                }
                }
        });

        txtRegistrese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Login.this,RegistroPersonas.class);
                startActivity(i);
            }
        });

     }

    public void archivoPreferenciasCompartidas() {
        SharedPreferences preferencia = getSharedPreferences("Datos_Usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editar= preferencia.edit();
        editar.putString("usuario",usuario.getText().toString());
        editar.putString("contraseña",clave.getText().toString());
        editar.commit();
    }

    private void permisoParaEscribirArchivos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

            // If the user previously denied this permission then show a message explaining why
            // this permission is needed
            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }

    }

}


