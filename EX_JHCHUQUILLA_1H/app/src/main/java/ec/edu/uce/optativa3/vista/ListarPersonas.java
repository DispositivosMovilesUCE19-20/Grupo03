package ec.edu.uce.optativa3.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import ec.edu.uce.optativa3.controlador.BaseDeDatos;
import androidx.appcompat.app.AppCompatActivity;
import ec.edu.uce.optativa3.modelo.Persona;
import uce.g3.registro_personas.R;

public class ListarPersonas extends AppCompatActivity {

    ListView listaViewUsuarios, listaTotal;
    ArrayList<String>listaInformacion, listarTodo,listarTodo1;
    ArrayList<Persona> listarPers;
    ArrayAdapter adaptador;
    BaseDeDatos base = new BaseDeDatos(this, "optativa3",null,1);
    TextView servicio ;

    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);
        servicio = (TextView) findViewById(R.id.txtServicio2);
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

                Persona pers = listarPers.get(i);
                Intent intent = new Intent(ListarPersonas.this, modificar_eliminar.class);

                bundle.putSerializable("persona", pers);
                intent.putExtras(bundle);
                intent.putExtra("servicio",servicio.getText().toString());
                startActivity(intent);
                  // String informacion= "******Informacion****** "+"\n"+listarPers.get(i)+"\n";
                  // Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
            }
        });

            // recibir datos
            Bundle extra= getIntent().getExtras();
            String service = extra.getString("servicio");
            servicio.setText(service);

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
            //writeToXmlFile();
            enviarArchivoJasonXml();
            Intent i = new Intent(ListarPersonas.this,Login.class);
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

    public void writeToXmlFile() {
        ArrayList<Persona> estudiante = listarPers;
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "estudiantes");
            serializer.attribute("", "number", String.valueOf(estudiante.size()));
            for (Persona per : estudiante) {
                serializer.startTag("", "informacion");
               // serializer.attribute("", "nombre", per.getNombre());

                serializer.startTag("", "nombre");
                serializer.text(per.getNombre());
                serializer.endTag("", "nombre");

                serializer.startTag("", "apellido");
                serializer.text(per.getApellido());
                serializer.endTag("", "apellido");

                serializer.startTag("", "telefono");
                serializer.text(per.getTelefono());
                serializer.endTag("", "telefono");

                serializer.startTag("", "correo");
                serializer.text(per.getEmail());
                serializer.endTag("", "correo");

                serializer.startTag("", "fecha_nacimiento");
                serializer.text(per.getFechaNacimiento());
                serializer.endTag("", "fecha_nacimiento");

                serializer.startTag("", "asignaturas");
                serializer.text(per.getAsignatura());
                serializer.endTag("", "asignaturas");

                serializer.startTag("", "genero");
                serializer.text(per.getGenero());
                serializer.endTag("", "genero");

                serializer.startTag("", "becado");
                serializer.text(per.getBecado());
                serializer.endTag("", "becado");

                serializer.endTag("", "informacion");
            }
            serializer.endTag("", "estudiantes");
            serializer.endDocument();
            String result = writer.toString();
            BaseDeDatos.writeToFile(this, "estudiantes.xml", result);
            Toast.makeText(getApplicationContext(), "From writeToXmlFile\n" + result, Toast.LENGTH_LONG).show();
            //txtXml.setText("From writeToXmlFile\n" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * public void readXmlPullParser(View view) {
     *         XmlPullParserFactory factory;
     *         FileInputStream fis = null;
     *         try {
     *             StringBuilder sb = new StringBuilder();
     *             factory = XmlPullParserFactory.newInstance();
     *             factory.setNamespaceAware(true);
     *             XmlPullParser xpp = factory.newPullParser();
     *             fis = openFileInput("cities.xml");
     *
     *             xpp.setInput(fis, null);
     *             //xpp.setInput(new ByteArrayInputStream(xmlString.getBytes()),null);
     *             int eventType = xpp.getEventType();
     *             while (eventType != XmlPullParser.END_DOCUMENT) {
     *                 if (eventType == XmlPullParser.START_DOCUMENT)
     *                     sb.append("[START]");
     *                 else if (eventType == XmlPullParser.START_TAG)
     *                     sb.append("\n<" + xpp.getName() + ">");
     *                 else if (eventType == XmlPullParser.END_TAG)
     *                     sb.append("</" + xpp.getName() + ">");
     *                 else if (eventType == XmlPullParser.TEXT)
     *                     sb.append(xpp.getText());
     *
     *                 eventType = xpp.next();
     *             }
     *             txtXml.setText(sb.toString());
     *         } catch (XmlPullParserException e) {
     *             e.printStackTrace();
     *         } catch (IOException e) {
     *             e.printStackTrace();
     *         } finally {
     *             if (fis != null) {
     *                 try {
     *                     fis.close();
     *                 } catch (IOException e) {
     *                     e.printStackTrace();
     *                 }
     *             }
     *         }
     *     }
     */
    public void enviarArchivoJasonXml(){

        Gson gson = new Gson();
        try{
            //definimos la URL a donde vamos a enviar la peticion
            URL url = new URL("http://optativa3.herokuapp.com/crearArchivo");
            // enviamos un post
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(gson.toJson(listarPers));
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                // String de la peticion al servidor
                sb.append(line + "\n");
            }
            Toast.makeText(getApplicationContext(),"Archivo xml: " +sb.toString(),Toast.LENGTH_LONG).show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
