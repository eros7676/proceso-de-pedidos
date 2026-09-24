public class ClienteVIP extends Cliente{


    public ClienteVIP(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public float calcularTotalConDescuento(float montoBase) {
        return montoBase * 0.7f; // 30% descuento
    }
}
