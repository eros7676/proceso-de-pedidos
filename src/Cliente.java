public abstract class Cliente {

    int id;
    String nombre;

    public Cliente(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public abstract float calcularTotalConDescuento(float montoBase);

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre;
    }
}
