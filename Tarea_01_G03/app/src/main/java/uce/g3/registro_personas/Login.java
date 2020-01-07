package uce.g3.registro_personas;

import OpenHelper.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                        Intent i = new Intent(Login.this,ListarPersonas.class);
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

}


