package uce.g3.registro_personas;

import java.sql.Date;

public class Persona {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Date fechaNacimiento;
    private String usuario;
    private String clave;
    private String genero;
    private String asignatura;
    private String becado;
    private String mensaje;

    public Persona() {

    }

    public Persona(String nombre, String apellido, String telefono, String email, Date fechaNacimiento, String usuario, String clave, String genero, String asignatura, String becado, String mensaje) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.clave = clave;
        this.genero = genero;
        this.asignatura = asignatura;
        this.becado = becado;
        this.mensaje=mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getBecado() {
        return becado;
    }

    public void setBecado(String becado) {
        this.becado = becado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
