import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TarjetaDeCredito {
    private double limite;
    private double saldo;
    private List<Compra> listaDeCompras;
    private String numeroTarjeta;
    private LocalDate fechaVencimiento;
    private double tasaInteresAnual; // Tasa de interés anual (por ejemplo, 24%)

    public TarjetaDeCredito(double limite, String numeroTarjeta, LocalDate fechaVencimiento) {
        this.limite = limite;
        this.saldo = limite;
        this.listaDeCompras = new ArrayList<>();
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.tasaInteresAnual = 24.0; // 24% anual
    }

    public boolean lanzarCompra(Compra compra) {
        if (this.saldo >= compra.getValor()) {
            this.saldo -= compra.getValor();
            this.listaDeCompras.add(compra);
            return true;
        }
        return false;
    }

    public void aplicarIntereses() {
        double saldoUtilizado = limite - saldo;
        if (saldoUtilizado > 0) {
            double interesMensual = (saldoUtilizado * (tasaInteresAnual / 100)) / 12; // Interés mensual
            saldo -= interesMensual;
            if (saldo < 0) saldo = 0; // No permitir saldo negativo
        }
    }

    public void pagar(double monto) {
        double saldoUtilizado = limite - saldo;
        if (monto > saldoUtilizado) {
            monto = saldoUtilizado; // No pagar más de lo utilizado
        }
        saldo += monto;
    }

    public double getLimite() {
        return limite;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public List<Compra> getListaDeCompras() {
        return Collections.unmodifiableList(listaDeCompras);
    }
}