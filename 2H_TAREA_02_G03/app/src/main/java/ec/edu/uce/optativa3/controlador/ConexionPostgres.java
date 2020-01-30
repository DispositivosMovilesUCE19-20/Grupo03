package ec.edu.uce.optativa3.controlador;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionPostgres {
    Connection conexion = null;

    public Connection conexionDB(){
        try{

            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("org.postgresql.Driver");
            conexion  = DriverManager.getConnection("jdbc:postgresql://ec2-107-20-239-47.compute-1.amazonaws.com:5432/deuqcfvi8ftq48","cimsjrdtowyhaz","d6ec464428825b90f86749c016c70e93a49b63297371578d082f775630f4c0d1");
            System.out.println("LA conexion se realizo con exito");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

        return conexion;
    }

    protected  void cerrarConexion(Connection con)throws Exception{
        con.close();
    }

}
