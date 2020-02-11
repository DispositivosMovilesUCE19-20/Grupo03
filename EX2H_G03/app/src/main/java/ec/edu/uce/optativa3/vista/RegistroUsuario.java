package ec.edu.uce.optativa3.vista;

import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import uce.g3.registro_personas.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroUsuario extends AppCompatActivity {

    EditText nombre, usuario, passwd;
    Button ingresarPersona;
    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombre = (EditText)findViewById(R.id.txtNombre);
        usuario = (EditText)findViewById(R.id.txtUsuario);
        passwd = (EditText)findViewById(R.id.txtClave);
        ingresarPersona = (Button)findViewById(R.id.btnInsertar);


        ingresarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.abrirBase();

                if(!nombre.getText().toString().isEmpty() && !usuario.getText().toString().isEmpty() && !passwd.getText().toString().isEmpty()){

                    base.insertarUsuario(String.valueOf(nombre.getText()),String.valueOf(usuario.getText()),String.valueOf(passwd.getText()));
                    base.cerrarBase();

                    Toast.makeText(getApplicationContext(),"Se ingresaron los datos con exito",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No puede dejar campos vacios",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
