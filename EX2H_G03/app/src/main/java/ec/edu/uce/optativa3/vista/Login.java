package ec.edu.uce.optativa3.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import ec.edu.uce.optativa3.controlador.HttpClient;
import ec.edu.uce.optativa3.controlador.OnHttpRequestComplete;
import ec.edu.uce.optativa3.controlador.Response;
import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.modelo.Estudiante;
import ec.edu.uce.optativa3.modelo.Logs;
import uce.g3.registro_personas.R;

public class Login extends AppCompatActivity {


    SensorEventListener proximidadEventListener;
   // LinearLayout b;
    SensorManager sensorManager;
    Sensor proximidad;

    TextView txtRegistrese, txtServicios ;
    Button btnLogin;
    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);
    EditText usuario, clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  b = (LinearLayout) findViewById(R.id.layoutBorde);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        brillo();


        setContentView(R.layout.activity_login);
        permisoParaEscribirArchivos();
        permisoParaTomarFoto();
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
                base.abrirBase();


                try{
                    Cursor cursor =  base.login(usuario.getText().toString(),passwd.getText().toString());
                    if (cursor.getCount()>0){
                        //base.insertarLogs(fechaInicio(),"null",nombre(),modelo(),version());
                        base.insertarLogs(usuario.getText().toString(),fechaInicio(),"null",nombre(),modelo(),version());
                        base.cerrarBase();
                        HttpClient client  = new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public void onComplete(Response status) {
                                if(status.isSuccess()){
                                    Gson gson = new GsonBuilder().create();
                                    try{
                                        JSONObject jo = new JSONObject(status.getResult());
                                        JSONArray ja = jo.getJSONArray("servicio");
                                        ArrayList<Estudiante> estudiante = new ArrayList<Estudiante>();
                                        for(int i =0; i< ja.length();i++){
                                            String pe = ja.getString(i);
                                            Estudiante p = gson.fromJson(pe, Estudiante.class);
                                            estudiante.add(p);
                                            //txtServicios.setText();
                                            Toast.makeText(getApplicationContext(),p.getMensaje(),Toast.LENGTH_SHORT).show();
                                        }


                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });


                        client.excecute("http://optativa3.herokuapp.com/mensajeExamen");
                        archivoPreferenciasCompartidas();
                        Intent i = new Intent(Login.this, ListarPersonas.class);
                        i.putExtra("usuario",usuario.getText().toString());
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
                Intent i = new Intent(Login.this, RegistroUsuario.class);
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
                        ArrayList<Estudiante> estudiante = new ArrayList<Estudiante>();
                        for(int i =0; i< ja.length();i++){
                            String pe = ja.getString(i);
                            Estudiante p = gson.fromJson(pe, Estudiante.class);
                            estudiante.add(p);
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

    private void permisoParaTomarFoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.CAMERA;
            if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }

    }

    public String fechaInicio(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String fecha = simpleDateFormat.format(date);

        return fecha;
    }

    public String modelo(){
        String modelo = Build.MODEL;
        return modelo;
    }

    public String version(){
        String version = Build.VERSION.RELEASE;
        return version;
    }

    public String nombre(){
        String nombre = Build.BRAND;
        return nombre;
    }


    public void brillo(){

        if (proximidad == null){
            Toast.makeText(this,"El dispositivo no tiene sensor de proximidad",Toast.LENGTH_SHORT).show();
            finish();
        }

        proximidadEventListener = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent event) {
                float valor = event.values[0];

                try {
                    if (valor == 0.0) {
                        // getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                        WindowManager.LayoutParams l = getWindow().getAttributes();
                        l.screenBrightness = 0.1f;
                        getWindow().setAttributes(l);
                    }else{
                        WindowManager.LayoutParams l = getWindow().getAttributes();
                        l.screenBrightness = 1.0f;
                        getWindow().setAttributes(l);
                       // getWindow().getDecorView().setBackgroundColor(Color.BLUE);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }


    public void start(){
        sensorManager.registerListener(proximidadEventListener,proximidad,sensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        sensorManager.unregisterListener(proximidadEventListener);
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        stop();
        // sensorManager.unregisterListener(this);
        super.onPause();
    }
}


