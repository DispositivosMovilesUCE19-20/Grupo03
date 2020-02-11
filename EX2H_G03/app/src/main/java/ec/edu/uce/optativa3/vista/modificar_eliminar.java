package ec.edu.uce.optativa3.vista;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import ec.edu.uce.optativa3.controlador.HttpClient;
import ec.edu.uce.optativa3.controlador.OnHttpRequestComplete;
import ec.edu.uce.optativa3.controlador.Response;
import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.modelo.Estudiante;
import uce.g3.registro_personas.R;

import android.content.Intent;
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

public class modificar_eliminar extends AppCompatActivity {

   // private RequestQueue queue;
    EditText  apellido, telefono, correo, fecha, genero, asignatura, beca;
    TextView nombre, serv;
    Button eliminar, actualizar;
    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  queue= Volley.newRequestQueue(this);
        setContentView(R.layout.activity_modificar_eliminar);
        nombre = (TextView) findViewById(R.id.vistaNombre);
        apellido = (EditText) findViewById(R.id.vistaApellido);
        telefono = (EditText) findViewById(R.id.vistaCelular);
        correo = (EditText) findViewById(R.id.vistaCorreo);
        fecha = (EditText) findViewById(R.id.vistaFecha);
        genero = (EditText) findViewById(R.id.vistaGenero);
        asignatura = (EditText) findViewById(R.id.vistaAsignaturas);
        beca = (EditText) findViewById(R.id.vistaBeca);
        serv = (TextView) findViewById(R.id.vistaServicio);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        actualizar = (Button) findViewById(R.id.btnActualizar);

        // recibir datos
        recibirServicio();
        recibirArreglo();



        eliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = nombre.getText().toString();
                String mensaje = base.eliminarPersona(name);
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
                                for(int i =0; i< ja.length();i++) {
                                    String pe = ja.getString(i);
                                    Estudiante p = gson.fromJson(pe, Estudiante.class);
                                    estudiante.add(p);
                                    Toast.makeText(getApplicationContext(), p.getMensaje(), Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

                if (mensaje.equals("si")){
                    client.excecute("http://optativa3.herokuapp.com/eliminar");
                }else{
                    client.excecute("http://optativa3.herokuapp.com/error");
                }

                Intent i = new Intent(modificar_eliminar.this, Login.class);
                startActivity(i);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nombre.getText().toString();
                String ap = apellido.getText().toString();
                String tel = telefono.getText().toString();
                String corr= correo.getText().toString();
                String fech= fecha.getText().toString();
                String gen = genero.getText().toString();
                String asig = asignatura.getText().toString();
                String bec = beca.getText().toString();
                String men=null;

                /* peticiones web*/
                HttpClient client  = new HttpClient(new OnHttpRequestComplete() {
                    @Override
                    public void onComplete(Response status) {
                        if(status.isSuccess()){
                            Gson gson = new GsonBuilder().create();
                            try{
                                JSONObject jo = new JSONObject(status.getResult());

                                String mensaje = jo.getString("grupo");
                                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
//
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

                if (!bec.equals("Becado")){
                    men =  base.actualizarPersona(nom,ap,tel,corr,fech,gen,asig,bec);
                    client.excecute("https://servicioexamenoptativa3.herokuapp.com/exito");
                }else{
                    client.excecute("https://servicioexamenoptativa3.herokuapp.com/error");
                }

                Intent i = new Intent(modificar_eliminar.this, ListarPersonas.class);
                startActivity(i);
            }
        });
    }

    public void recibirArreglo(){
        Bundle arreglo = getIntent().getExtras();
        Estudiante per = null;

        if (arreglo!=null){
            per = (Estudiante) arreglo.getSerializable("persona");
            nombre.setText(per.getNombre());
            apellido.setText(per.getApellido());
            telefono.setText(per.getTelefono());
            correo.setText(per.getEmail());
            fecha.setText(per.getFechaNacimiento());
            genero.setText(per.getGenero());
            asignatura.setText(per.getAsignatura());
            beca.setText(per.getBecado());
        }else{
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    }

    public void  recibirServicio(){
        Bundle extra= getIntent().getExtras();
        String service = extra.getString("servicio");
        serv.setText(service);
    }

/*
    public void peticionHttpGet(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new com.android.volley.Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try{

                    JSONArray ja = response.getJSONArray("servicio");
                    for(int i =0; i< ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        String pe = ja.getString(i);
                        Toast.makeText(modificar_eliminar.this, pe,Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){

            }
        });
        queue.add(request);
    }*/
}
