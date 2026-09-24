public class ClienteRegular extends Cliente{


    public ClienteRegular(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public float calcularTotalConDescuento(float montoBase) {
        return montoBase;
    }
}
