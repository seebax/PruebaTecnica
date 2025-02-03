public class Usuario {
    private int id;
    private String nombre;
    private int edad;
    private String direccion;
    private String telefono;
    private byte[] fotografia;  // Se almacena la foto como arreglo de bytes

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public byte[] getFotografia() {
        return fotografia;
    }
    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }
}
