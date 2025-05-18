import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Compra implements Comparable<Compra> {
    private double valor;
    private String descripcion;
    private LocalDateTime fechaHora;

    public Compra(double valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
        this.fechaHora = LocalDateTime.now(); // Registrar fecha y hora actual
    }

    public double getValor() {
        return valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Compra: valor=" + valor + ", descripcion='" + descripcion + "', fecha=" + fechaHora.format(formatter);
    }

    @Override
    public int compareTo(Compra otraCompra) {
        return Double.compare(this.valor, otraCompra.getValor());
    }
}