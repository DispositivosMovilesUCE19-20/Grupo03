package ec.edu.uce.optativa3.modelo;

public class Usuario {

    private String nombre;
    private String usuario;
    private String clave;

    public Usuario() {

    }

    public Usuario(String nombre,String usu, String passwd) {
        this.nombre = nombre;
        this.usuario = usu;
        this.clave = passwd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
