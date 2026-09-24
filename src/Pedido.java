import java.math.BigDecimal;
import java.util.Objects;

public class Pedido {

    private final Cliente cliente;
    private final BigDecimal montoBase;
    private final BigDecimal montoFinal;

    public Pedido(Cliente cliente, BigDecimal montoBase) {
        this.cliente = Objects.requireNonNull(cliente, "El pedido necesita un cliente");
        this.montoBase = Cliente.redondear(Objects.requireNonNull(montoBase, "El monto no puede ser nulo"));
        this.montoFinal = cliente.calcularTotalConDescuento(this.montoBase);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public BigDecimal getMontoBase() {
        return montoBase;
    }

    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    @Override
    public String toString() {
        return "Cliente: {" + cliente + "}, Monto base: " + montoBase + ", Monto final: " + montoFinal;
    }
}
