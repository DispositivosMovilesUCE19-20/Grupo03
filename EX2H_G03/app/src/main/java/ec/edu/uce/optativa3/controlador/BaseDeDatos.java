package ec.edu.uce.optativa3.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import ec.edu.uce.optativa3.modelo.Estudiante;
import ec.edu.uce.optativa3.modelo.Logs;

public class BaseDeDatos extends SQLiteOpenHelper {



    public BaseDeDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
/*crear estructura de las tablas*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        String usuario = "create table usuario(_ID integer primary key autoincrement, Nombre text, " +
                "Usuario text, Clave text);";

        db.execSQL(usuario);

        String estudiante = "create table estudiante(_ID integer primary key autoincrement, Nombre text, " +
                "Apellido text, Telefono text, Email text,Fecha_Nacimiento date, " +
                "Genero text, Asignatura text, Becado text, Imagen blob);";

        db.execSQL(estudiante);

        String logs = "create table logs(_ID integer primary key autoincrement, Nombre_Usuario text, Inicio text, " +
                "Fin text, Nombre text, Modelo text,Version text);";
        db.execSQL(logs);


    }
/*modificar la estructura de la base de datos*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
// metodo para abrir la base de datos
    public void abrirBase(){
        this.getWritableDatabase();
    }

// metodo para cerrar la base de datos
    public void cerrarBase(){
        this.close();
    }
// metodo para ingresar datos a la tabla
    public void insertarLogs(String usuario,String inicio, String fin, String nombre, String modelo, String version){

        ContentValues datos = new ContentValues();
        datos.put("Nombre_Usuario",usuario);
        datos.put("Inicio",inicio);
        datos.put("Fin",fin);
        datos.put("Nombre",nombre);
        datos.put("Modelo",modelo);
        datos.put("Version",version);

        this.getWritableDatabase().insert("logs",null,datos);
    }

    public void insertarUsuario(String nombre, String usuario, String clave){

        ContentValues datos = new ContentValues();
        datos.put("Nombre",nombre);
        datos.put("Usuario",usuario);
        datos.put("Clave",clave);

        this.getWritableDatabase().insert("usuario",null,datos);
    }

    public void insertarEstudiante(String nombre, String apellido, String telefono, String email, String fechaNacimiento, String genero, String asignatura, String becado){

        ContentValues datos = new ContentValues();
        datos.put("Nombre",nombre);
        datos.put("Apellido",apellido);
        datos.put("Telefono",telefono);
        datos.put("Email",email);
        datos.put("Fecha_Nacimiento", String.valueOf(fechaNacimiento));
        datos.put("Genero",genero);
        datos.put("Asignatura",asignatura);
        datos.put("Becado",becado);
       // datos.put("Imagen",img);

        this.getWritableDatabase().insert("estudiante",null,datos);
    }
// metodo para validar el usuario
    public Cursor login(String usuario, String clave) throws SQLException {
        Cursor cursorC = null;
        cursorC = this.getReadableDatabase().query("usuario", new String[]{"Usuario","Clave"},
                "Usuario='"+usuario+"' and Clave='"+clave+"'",null,
                null,null,null);

        return cursorC;
    }

// metodo para obtener lista de estudiantes
    public ArrayList<Estudiante> listarPersonas(){

        ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
                Estudiante pers = new Estudiante();
                pers.setNombre(info.getString(1));
                pers.setApellido(info.getString(2));
                pers.setTelefono(info.getString(3));
                pers.setEmail(info.getString(4));
                pers.setFechaNacimiento(info.getString(5));
                pers.setGenero(info.getString(6));
                pers.setAsignatura(info.getString(7));
                pers.setBecado(info.getString(8));
                listaEstudiantes.add(pers);
            }while(info.moveToNext());
        }
        return listaEstudiantes;
    }

    public ArrayList<Logs> listarLogsAcceso(String usu){

        ArrayList<Logs> logs = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * from logs where Nombre_Usuario='"+usu+"' ORDER BY Inicio DESC LIMIT 1";
        Cursor info = database.rawQuery(q,null);
        //ResultSet rs  = database.execSQL(q);

        if (info.moveToFirst()){
            do {
                Logs log = new Logs();
                log.setNombreUsuario(info.getString(1));
                log.setInicio(info.getString(2));
                log.setFin(info.getString(3));
                log.setNombre(info.getString(4));
                log.setModelo(info.getString(5));
                log.setVersion(info.getString(6));
                logs.add(log);
            }while(info.moveToNext());
        }
        return logs;
    }

    public ArrayList<Logs> listarLogs(){

        ArrayList<Logs> logs = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * from logs";
        Cursor info = database.rawQuery(q,null);
        //ResultSet rs  = database.execSQL(q);

        if (info.moveToFirst()){
            do {
                Logs log = new Logs();
                log.setNombreUsuario(info.getString(1));
                log.setInicio(info.getString(2));
                log.setFin(info.getString(3));
                log.setNombre(info.getString(4));
                log.setModelo(info.getString(5));
                log.setVersion(info.getString(6));
                logs.add(log);
            }while(info.moveToNext());
        }
        return logs;
    }

public ArrayList llenarListView(){
        ArrayList<String> lista = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
                lista.add(info.getString(0)+" "+info.getString(1));
            }while(info.moveToNext());
        }
        return lista;
}
    public ArrayList llenarListInfo(){

        ArrayList<String> listaTemp = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
               /* listaTemp.add("Nombre: "+info.getString(1)+"\n"+"Apellido: "+info.getString(2)+"\n"+"Telefono: "+info.getString(3)+"\n"
                        +"Email: "+info.getString(4)+"\n"+"Fecha Nacimiento: "+info.getString(5)+"\n"+"Genero: "+info.getString(8)+"\n"+
                        "Asignatura: "+info.getString(9)+"\n"+"Becado?: "+info.getString(10));*/
                listaTemp.add("Nombre: "+info.getString(1));
                listaTemp.add("Apellido: "+info.getString(2));
                listaTemp.add("Telefono: "+info.getString(3));
                listaTemp.add("Email: "+info.getString(4));
                listaTemp.add("Fecha Nacimiento: "+info.getString(5));
                listaTemp.add("Genero: "+info.getString(6));
                listaTemp.add("Asignatura: "+info.getString(7));
                listaTemp.add("Becado?: "+info.getString(8));

            }while(info.moveToNext());
        }
        return listaTemp;
    }

    public ArrayList llenarListInfo1(){

        ArrayList<String> listaTemp = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
               listaTemp.add("Nombre: "+info.getString(1)+"\n"+"Apellido: "+info.getString(2)+"\n"+"Telefono: "+info.getString(3)+"\n"
                        +"Email: "+info.getString(4)+"\n"+"Fecha Nacimiento: "+info.getString(5)+"\n"+"Genero: "+info.getString(6)+"\n"+
                        "Asignatura: "+info.getString(7)+"\n"+"Becado?: "+info.getString(8));


            }while(info.moveToNext());
        }
        return listaTemp;
    }



    public Cursor datos(String nombre) throws SQLException {
        Cursor cursorC = null;
        cursorC = this.getReadableDatabase().query("estudiante", new String[]{"Nombre","Apellido","Telefono","Email","Fecha_Nacimiento","Genero","Asignatura","Becado"},
                "Nombre='"+nombre+"'",null,
                null,null,null);

        return cursorC;
    }

    // metodo para eliminar persona
    public String  eliminarPersona(String nombre){
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        int can = database.delete("estudiante","Nombre = '"+nombre+"'",null);
        if (can!=0){
            mensaje = "si";
        }else{
            mensaje = "no";
        }
return mensaje;
    }

    // metodo para actualizar persona
    public String actualizarPersona(String nom, String ap, String tel, String corr, String fech, String gen, String asig, String bec){
      //  HttpClient client = null;
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cont = new ContentValues();
        cont.put("Nombre",nom);
        cont.put("Apellido",ap);
        cont.put("Telefono",tel);
        cont.put("Email",corr);
        cont.put("Fecha_Nacimiento",fech);
        cont.put("Genero",gen);
        cont.put("Asignatura",asig);
        cont.put("Becado",bec);

        int cantidad = database.update("estudiante",cont,"Nombre = '"+nom+"'",null);
        if (cantidad!=0){
           mensaje="si";
        }else{
            mensaje = "no";
        }
        return mensaje;
    }

    public void actualizarLogsAcceso(String usuario,String inicio, String fechaFin){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cont = new ContentValues();
        cont.put("Fin",fechaFin);
        database.update("logs",cont,"Nombre_Usuario = '"+usuario+"' and Inicio = '"+inicio+"'",null);

    }

    public static void writeToFile(Context context, String fileName, String str) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Estudiante> listarEstudiantesFechaAsc(){

        ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante order by Fecha_Nacimiento ASC";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
                Estudiante pers = new Estudiante();
                pers.setNombre(info.getString(1));
                pers.setApellido(info.getString(2));
                pers.setTelefono(info.getString(3));
                pers.setEmail(info.getString(4));
                pers.setFechaNacimiento(info.getString(5));
                pers.setGenero(info.getString(6));
                pers.setAsignatura(info.getString(7));
                pers.setBecado(info.getString(8));
                listaEstudiantes.add(pers);
            }while(info.moveToNext());
        }
        return listaEstudiantes;
    }

    public ArrayList<Estudiante> listarEstudiantesApellidoAsc(){

        ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM estudiante order by Apellido DESC";
        Cursor info = database.rawQuery(q,null);
        if (info.moveToFirst()){
            do {
                Estudiante pers = new Estudiante();
                pers.setNombre(info.getString(1));
                pers.setApellido(info.getString(2));
                pers.setTelefono(info.getString(3));
                pers.setEmail(info.getString(4));
                pers.setFechaNacimiento(info.getString(5));
                pers.setGenero(info.getString(6));
                pers.setAsignatura(info.getString(7));
                pers.setBecado(info.getString(8));
                listaEstudiantes.add(pers);
            }while(info.moveToNext());
        }
        return listaEstudiantes;
    }
}
