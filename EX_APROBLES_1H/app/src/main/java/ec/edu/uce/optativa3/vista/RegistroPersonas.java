package ec.edu.uce.optativa3.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;
import uce.g3.registro_personas.R;

public class RegistroPersonas extends AppCompatActivity {

    //capturo las variables del formulario
    EditText nombre, apellido, telefono, email, usuario, passwd, fechaNacimiento;
    Button ingresarPersona, ingresarFoto;
    CheckBox c1,c2,c3,c4,c5;
    RadioButton masculino, femenino;
    Switch s;
    Spinner dia,mes,anio;
    ImageView foto;
    TextView servicio;

    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_personas);

        nombre = (EditText) findViewById(R.id.txtNombre);
        apellido = (EditText) findViewById(R.id.txtApellido);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        email = (EditText) findViewById(R.id.txtEmail);
        usuario = (EditText) findViewById(R.id.txtUsuario);
        passwd = (EditText) findViewById(R.id.txtClave);
        //fechaNacimiento = (EditText)findViewById(R.id.txtFechaNacimiento);
        ingresarPersona = (Button) findViewById(R.id.btnInsertar);
        c1 = (CheckBox) findViewById(R.id.boxOptativa3);
        c2 = (CheckBox) findViewById(R.id.boxProgramacionDistribuida);
        c3 = (CheckBox) findViewById(R.id.boxGestionTics);
        c4 = (CheckBox) findViewById(R.id.boxGestionProyectos);
        c5 = (CheckBox) findViewById(R.id.boxMineria);
        masculino = (RadioButton) findViewById(R.id.radioBtnMasculino);
        femenino = (RadioButton) findViewById(R.id.radioBtnFemenino);
        s = (Switch) findViewById(R.id.switch1);
        dia = (Spinner) findViewById(R.id.spinnerDia);
        mes = (Spinner) findViewById(R.id.spinnerMes);
        anio = (Spinner) findViewById(R.id.spinnerAnio);
        foto = (ImageView) findViewById(R.id.imgFoto);
        ingresarFoto = (Button) findViewById(R.id.btnAgregarImg);
        servicio = (TextView) findViewById(R.id.txtServicio3);

        ArrayAdapter<CharSequence> dias = ArrayAdapter.createFromResource(this, R.array.dia, android.R.layout.simple_spinner_item);
        dia.setAdapter(dias);
        ArrayAdapter<CharSequence> meses = ArrayAdapter.createFromResource(this, R.array.mes, android.R.layout.simple_spinner_item);
        mes.setAdapter(meses);
        ArrayAdapter<CharSequence> anios = ArrayAdapter.createFromResource(this, R.array.anio, android.R.layout.simple_spinner_item);
        anio.setAdapter(anios);

        ingresarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.abrirBase();
                validarCheckBox();
                if (!validarCheckBox().isEmpty()) {
                    base.insertarRegistro(String.valueOf(nombre.getText()), String.valueOf(apellido.getText()), String.valueOf(telefono.getText()),
                            String.valueOf(email.getText()), mostrarFecha(), String.valueOf(usuario.getText()),
                            String.valueOf(passwd.getText()), validarRadioButon(), validarCheckBox(), validarSwith());
                    base.cerrarBase();

                    Toast.makeText(getApplicationContext(), "Se ingresaron los datos con exito", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar a lmenos 2 materias", Toast.LENGTH_LONG).show();
                }


            }
        });

        // Validar contraseña

        public void validarContra(){

            String clave = passwd.getText().toString();
            String passvalue="";

            char c = clave.charAt(0);
            byte  contNumero = 0, contLetraMay = 0, contLetraMin=0;

            for (byte i = 0; i < passwd.length(); i++) {x|x|
                    passvalue= String.valueOf(c);
                if (passvalue.matches("[A-Z]")) {
                    contLetraMay++;
                } else if (passvalue.matches("[a-z]")) {
                    contLetraMin++;
                } else if (passvalue.matches("[0-9]")) {
                    contNumero++;
                }
            }
        }



        // recibir datos
        Bundle extra= getIntent().getExtras();
        String service = extra.getString("servicio");
        servicio.setText(service);

    }
        private String validarCheckBox(){
            String cadena = "";
            int cont =0;

                if(c1.isChecked()){
                    cadena += c1.getText()+" ";
                    cont++;
                }
                if(c2.isChecked()){
                    cadena += c2.getText()+" ";
                    cont++;
                }
                if(c3.isChecked()){
                    cadena += c3.getText()+" ";
                    cont++;
                }
                if(c4.isChecked()){
                    cadena += c4.getText()+" ";
                    cont++;
                }
                if(c5.isChecked()){
                    cadena += c5.getText()+" ";
                    cont++;
                }

             if(cont>1){
                    return cadena;
            }else{

                cadena="";
                return cadena;
            }


    }
        private String validarRadioButon(){
        String cadena = "";
        if (masculino.isChecked()){
            cadena = masculino.getText().toString();
        }else if(femenino.isChecked()){
            cadena = femenino.getText().toString();
        }
        return cadena;
        }


    private String validarSwith() {
        String cadena = "";
        if (s.isChecked()){
            cadena="Becado";
        }else{
            cadena="No Becado";
        }

        return cadena;
    }

    private String mostrarFecha(){
         String diaC = "";
         String mesC = "";
         String anioC = "";

         diaC = dia.getSelectedItem().toString();
         mesC = mes.getSelectedItem().toString();
         anioC = anio.getSelectedItem().toString();

        return diaC +"/"+ mesC +"/"+ anioC;
    }

    public void onClick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(i.createChooser(i,"Seleccione la Aplicacion"),10);

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path=data.getData();
            foto.setImageURI(path);
        }
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
        if (id== R.id.CerrarSesion){

            Intent i = new Intent(RegistroPersonas.this,Login.class);
            startActivity(i);
        }if(id== R.id.Salir){
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
