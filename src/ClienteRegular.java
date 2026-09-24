import java.math.BigDecimal;

public class ClienteRegular extends Cliente {

    public ClienteRegular(String dni, String nombre) {
        super(dni, nombre);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.REGULAR;
    }

    @Override
    public BigDecimal calcularTotalConDescuento(BigDecimal montoBase) {
        return redondear(montoBase);
    }
}
