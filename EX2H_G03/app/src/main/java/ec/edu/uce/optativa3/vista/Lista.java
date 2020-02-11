package ec.edu.uce.optativa3.vista;

import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import ec.edu.uce.optativa3.modelo.Estudiante;
import uce.g3.registro_personas.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    ArrayList<Estudiante>  listarPorFecha, listarPorApellido;
    ArrayAdapter adaptador;
    BaseDeDatos base = new BaseDeDatos(this, "optativa3",null,1);
    ListView listaEstudiantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listarPorFecha = base.listarEstudiantesFechaAsc();
        listaEstudiantes = (ListView) findViewById(R.id.listaEstudiantes);



        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listarPorFecha);
        listaEstudiantes.setAdapter(adaptador);
        listaEstudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Estudiante pers = listarPorFecha.get(i);


                //String informacion= "******Informacion****** "+"\n"+listarPers.get(i)+"\n";
               // Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
             // Intent intent = new Intent(ListarPersonas.this, modificar_eliminar.class);

                    //pers = (Estudiante) arreglo.getSerializable("persona");
                /*
                    nombre.setText(pers.getNombre());
                    apellido.setText(pers.getApellido());
                    telefono.setText(pers.getTelefono());
                    correo.setText(pers.getEmail());
                    fecha.setText(pers.getFechaNacimiento());
                    genero.setText(pers.getGenero());
                    asignatura.setText(pers.getAsignatura());
                    beca.setText(pers.getBecado());
                */

            }
        });
    }
}
