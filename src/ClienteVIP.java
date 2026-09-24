import java.math.BigDecimal;

public class ClienteVIP extends Cliente {

    private static final BigDecimal FACTOR_DESCUENTO = new BigDecimal("0.70");

    public ClienteVIP(String dni, String nombre) {
        super(dni, nombre);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.VIP;
    }

    @Override
    public BigDecimal calcularTotalConDescuento(BigDecimal montoBase) {
        return redondear(montoBase.multiply(FACTOR_DESCUENTO));
    }
}
