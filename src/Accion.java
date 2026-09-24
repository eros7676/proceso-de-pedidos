public class Accion {

    public static final byte REGISTRAR_USUARIO = 0, ENCOLAR_PEDIDO = 1, PROCESAR_PEDIDO = 2;

    byte tipo;

    Object objeto;

    public Accion(byte tipo, Object objeto){
        this.tipo = tipo;
        this.objeto = objeto;
    }
}
