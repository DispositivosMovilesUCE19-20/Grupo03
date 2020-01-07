package ec.edu.uce.optativa3.vista;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;
import uce.g3.registro_personas.R;

public class RegistroPersonas extends AppCompatActivity {

    //capturo las variables del formulario
    EditText nombre, apellido, telefono, email;
    Button ingresarPersona, ingresarFoto;
    CheckBox c1,c2,c3,c4,c5;
    RadioButton masculino, femenino;
    Switch s;
    Spinner dia,mes,anio;
    ImageView foto;
    TextView servicio;

    BaseDeDatos base = new BaseDeDatos(this,"optativa3",null,1);

    private static final String CARPETA_RAIZ="misImagenes/";
    private static final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    private static final String DIRECTORIO_IMAGEN=CARPETA_RAIZ+RUTA_IMAGEN;
    private String path;
    File fileImagen;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_personas);

        nombre = (EditText)findViewById(R.id.txtNombre);
        apellido = (EditText)findViewById(R.id.txtApellido);
        telefono = (EditText)findViewById(R.id.txtTelefono);
        email = (EditText)findViewById(R.id.txtEmail);
        //usuario = (EditText)findViewById(R.id.txtUsuario);
        //passwd = (EditText)findViewById(R.id.txtClave);
        //fechaNacimiento = (EditText)findViewById(R.id.txtFechaNacimiento);
        ingresarPersona = (Button)findViewById(R.id.btnInsertar);
        c1 = (CheckBox) findViewById(R.id.boxOptativa3);
        c2 = (CheckBox) findViewById(R.id.boxProgramacionDistribuida);
        c3 = (CheckBox) findViewById(R.id.boxGestionTics);
        c4 = (CheckBox) findViewById(R.id.boxGestionProyectos);
        c5 = (CheckBox) findViewById(R.id.boxMineria);
        masculino = (RadioButton)findViewById(R.id.radioBtnMasculino);
        femenino = (RadioButton)findViewById(R.id.radioBtnFemenino);
        s = (Switch) findViewById(R.id.switch1);
        dia = (Spinner)findViewById(R.id.spinnerDia);
        mes = (Spinner)findViewById(R.id.spinnerMes);
        anio = (Spinner)findViewById(R.id.spinnerAnio);
        foto = (ImageView)findViewById(R.id.imgFoto);
        ingresarFoto = (Button) findViewById(R.id.btnAgregarImg);
        servicio = (TextView) findViewById(R.id.txtServicio3);

        ArrayAdapter<CharSequence> dias=ArrayAdapter.createFromResource(this, R.array.dia, android.R.layout.simple_spinner_item);
        dia.setAdapter(dias);
        ArrayAdapter<CharSequence> meses=ArrayAdapter.createFromResource(this, R.array.mes, android.R.layout.simple_spinner_item);
        mes.setAdapter(meses);
        ArrayAdapter<CharSequence> anios=ArrayAdapter.createFromResource(this, R.array.anio, android.R.layout.simple_spinner_item);
        anio.setAdapter(anios);

        ingresarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.abrirBase();
                validarCheckBox();

                    if (!validarCheckBox().isEmpty()) {
                        base.insertarEstudiante(String.valueOf(nombre.getText()), String.valueOf(apellido.getText()), String.valueOf(telefono.getText()),
                                String.valueOf(email.getText()), mostrarFecha(), validarRadioButon(), validarCheckBox(), validarSwith(),convertirImagen(bitmap));
                        base.cerrarBase();

                        Toast.makeText(getApplicationContext(), "Se ingresaron los datos con exito", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(RegistroPersonas.this, ListarPersonas.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Debe ingresar al menos 2 materias", Toast.LENGTH_LONG).show();
                    }

            }
        });

        // recibir datos
        /*
        Bundle extra= getIntent().getExtras();
        String service = extra.getString("servicio");
        servicio.setText(service);
*/
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

        final CharSequence[] op = {"Tomar Foto","Galeria","Cancelar"};
        final AlertDialog.Builder aletOp = new AlertDialog.Builder(this);
        aletOp.setTitle("Seleccione una opcion");
        aletOp.setItems(op, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (op[i].equals("Tomar Foto")){
                    tomarFoto();
                }else{
                    if (op[i].equals("Galeria")){
                        Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        in.setType("image/");
                        startActivityForResult(in.createChooser(in,"Seleccione la Aplicacion"),10);

                    }else{
                        dialogInterface.dismiss();
                    }

                }
            }
        });

        aletOp.show();


    }

    private void tomarFoto() {
        File fileImg = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean  creada = fileImg.exists();
        String nombre="";

        if(creada==false){
            creada = fileImg.mkdirs();
        }

        if (creada==true){
             Long consecutivo = System.currentTimeMillis()/1000;
             nombre = consecutivo.toString()+".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN+
                File.separator+nombre;

        fileImagen = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

        startActivityForResult(intent,20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch(requestCode){
                case 10:
                    final Uri miPath=data.getData();
                    foto.setImageURI(miPath);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),miPath);
                        foto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 20:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener(){
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);

                                }
                            });

                    bitmap = BitmapFactory.decodeFile(path);
                    foto.setImageBitmap(bitmap);
                    break;
            }

            bitmap = redimensionarImagen(bitmap,600,800);
          //  Toast.makeText(getApplicationContext(), "bitmap "+bitmap, Toast.LENGTH_LONG).show();

        }
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float i, float i1) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > i || alto > i1){
            float anchoNuevo = i/ancho;
            float altoNuevo = i1/alto;

            Matrix matrix= new Matrix();
            matrix.postScale(anchoNuevo,altoNuevo);
            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);
        }else {
            return bitmap;
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


    private String convertirImagen(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imgString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imgString;
    }

/*
    public boolean validarPasswd(){

        boolean var;
        String clave = passwd.getText().toString();
        int codigoAcci;
        char[] caracteres = clave.toCharArray();
        int existe = 0;
        for (int i = 0; i < caracteres.length; i++) {
            codigoAcci=caracteres[i];
            if (codigoAcci>=32 && codigoAcci<=47 || codigoAcci>=58 && codigoAcci<=64 || codigoAcci>=91 && codigoAcci<=96 || codigoAcci>=123 && codigoAcci<=255) {
                if(clave.length()==8) {
                    existe += 1;
                }
            }
        }
        if (existe>0) {
            var = true;
        }else{
            var=false;
        }
        return var;
    }
*/
}
