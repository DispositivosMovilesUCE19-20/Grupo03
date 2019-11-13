package uce.g3.registro_personas;

import OpenHelper.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class ListarPersonas extends AppCompatActivity {

    ListView listaViewUsuarios, listaTotal;
    ArrayList<String>listaInformacion, listarTodo,listarTodo1;
    ArrayList<Persona> listarPers;
    ArrayAdapter adaptador;
    BaseDeDatos base = new BaseDeDatos(this, "optativa3",null,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);
        listaViewUsuarios = (ListView)findViewById(R.id.listaUsuarios);
        listaInformacion = base.llenarListView();
        listarTodo = base.llenarListInfo();
        listarTodo1 = base.llenarListInfo1();
        listarPers = base.listarPersonas();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listaViewUsuarios.setAdapter(adaptador);
        listaViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   String informacion= "******Informacion****** "+"\n"+listarTodo1.get(i)+"\n";
                   Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
            }
        });

        crearArchivo();
    }

    private void crearArchivo() {
        Gson gson = new Gson();
        try{

            File file = Environment.getExternalStorageDirectory();
            File file_path1 = new File(Environment.DIRECTORY_DOWNLOADS);
            File file_path = new File(file +"/"+ file_path1);
            File local_file = new File(file_path.getPath(),"Datos_Usuarios.txt");
            if(local_file.exists()){
                local_file.delete();
            }

            local_file.createNewFile();
            FileWriter fw = new FileWriter(local_file);
            BufferedWriter escritura = new BufferedWriter(fw);

            escritura.write(gson.toJson(listarPers));
            escritura.flush();
            escritura.close();
            Toast.makeText(getApplicationContext(),"Se creo el archivo en la ruta: "+file_path.getAbsolutePath(),Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(this,"No se pudo crear el archivo",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /*
            // recibir datos
            Bundle extra= getIntent().getExtras();
            String us = extra.getString("usuario");
            String psswd = extra.getString("clave");*/
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_en_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.CerrarSesion){

            Intent i = new Intent(ListarPersonas.this,Login.class);
            startActivity(i);
        }if(id==R.id.Salir){
            SharedPreferences preferencia = getSharedPreferences("Datos_Usuarios", Context.MODE_PRIVATE);
            SharedPreferences.Editor editar= preferencia.edit();
            editar.clear().apply();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


}
