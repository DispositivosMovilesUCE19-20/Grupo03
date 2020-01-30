package ec.edu.uce.optativa3.modelo;

public class Logs {

    private String nombreUsuario;
    private String inicio;
    private String fin;
    private String nombre;
    private String modelo;
    private String version;

    private String idClase;

    public Logs(){

    }

    public Logs(String idClase, String nombreUsuario, String inicio, String fin, String nombre, String modelo, String version) {
        this.idClase = idClase;
        this.nombreUsuario = nombreUsuario;
        this.inicio = inicio;
        this.fin = fin;
        this.nombre = nombre;
        this.modelo = modelo;
        this.version = version;

    }

    public Logs(String nombreUsuario, String inicio, String fin, String nombre, String modelo, String version) {
        this.nombreUsuario = nombreUsuario;
        this.inicio = inicio;
        this.fin = fin;
        this.nombre = nombre;
        this.modelo = modelo;
        this.version = version;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
    }
}
