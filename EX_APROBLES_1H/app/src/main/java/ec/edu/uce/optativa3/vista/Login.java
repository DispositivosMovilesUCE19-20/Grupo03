package ec.edu.uce.optativa3.vista;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import ec.edu.uce.optativa3.controlador.HttpClient;
import ec.edu.uce.optativa3.controlador.OnHttpRequestComplete;
import ec.edu.uce.optativa3.controlador.Response;
import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.modelo.Persona;
import uce.g3.registro_personas.R;

public class Login extends AppCompatActivity {


    TextView txtRegistrese, txtServicios ;
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
        txtServicios = (TextView) findViewById(R.id.txtServicio);
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
                        Intent i = new Intent(Login.this, ListarPersonas.class);
                        i.putExtra("servicio",txtServicios.getText().toString());
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
                Intent i = new Intent(Login.this, RegistroPersonas.class);
                i.putExtra("servicio",txtServicios.getText().toString());
                startActivity(i);
            }
        });


        /* peticiones web*/
        HttpClient client  = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    Gson gson = new GsonBuilder().create();
                    try{
                        JSONObject jo = new JSONObject(status.getResult());
                        JSONArray ja = jo.getJSONArray("servicio");
                        ArrayList<Persona> persona = new ArrayList<Persona>();
                        for(int i =0; i< ja.length();i++){
                            String pe = ja.getString(i);
                            Persona p = gson.fromJson(pe, Persona.class);
                            persona.add(p);
                            txtServicios.setText(p.getMensaje());
                        }


                    }catch(Exception e){
                        e.printStackTrace();
                    }
                   // Toast.makeText(getApplicationContext(),status.getResult(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        client.excecute("http://optativa3.herokuapp.com/mensajeGrupo03");
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

            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }

    }


}


