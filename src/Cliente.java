import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class Cliente {

    public enum Tipo {
        REGULAR,
        VIP
    }

    private final String dni;
    private final String nombre;

    protected Cliente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract Tipo getTipo();

    public abstract BigDecimal calcularTotalConDescuento(BigDecimal montoBase);

    static BigDecimal redondear(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "DNI: " + dni + ", Nombre: " + nombre + ", Tipo: " + getTipo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente cliente)) {
            return false;
        }
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
}
