package uce.g3.registro_personas;

import OpenHelper.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListarPersonas extends AppCompatActivity {

    ListView listaViewUsuarios, listaTotal;
    ArrayList<String>listaInformacion, listarTodo;
    ArrayAdapter adaptador;
    BaseDeDatos base = new BaseDeDatos(this, "optativa3",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);

        listaViewUsuarios = (ListView)findViewById(R.id.listaUsuarios);
        listaInformacion = base.llenarListView();
        listarTodo = base.llenarListInfo();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listaViewUsuarios.setAdapter(adaptador);
        listaViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   String informacion= "******Informacion****** "+"\n"+listarTodo.get(i)+"\n";
                   Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
            }
        });
    }

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


        }
        return super.onOptionsItemSelected(item);
    }
}
