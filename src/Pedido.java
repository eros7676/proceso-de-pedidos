public class Pedido {

    Cliente cliente;
    float monto;

    public Pedido(Cliente cliente, float monto){
        this.cliente = cliente;
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Cliente: {" + cliente + "}, Monto Base: " + monto;
    }
}
