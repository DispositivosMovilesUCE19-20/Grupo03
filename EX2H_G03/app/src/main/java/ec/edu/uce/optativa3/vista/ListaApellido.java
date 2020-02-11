package ec.edu.uce.optativa3.vista;

import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import ec.edu.uce.optativa3.modelo.Estudiante;
import uce.g3.registro_personas.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaApellido extends AppCompatActivity {

    ArrayList<Estudiante> listarPorApellido;
    ArrayAdapter adaptador;
    BaseDeDatos base = new BaseDeDatos(this, "optativa3",null,1);
    ListView listaApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_apellido);


        listarPorApellido = base.listarEstudiantesApellidoAsc();
        listaApellido = (ListView) findViewById(R.id.listaEstudiantes);



        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listarPorApellido);
        listaApellido.setAdapter(adaptador);
        listaApellido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Estudiante pers = listarPorApellido.get(i);


            }
        });
    }
}
