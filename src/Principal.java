import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el límite de la tarjeta: ");
        double limite = lectura.nextDouble();
        TarjetaDeCredito tarjeta = new TarjetaDeCredito(limite);

        int continuar = 1;
        while (continuar != 0) {
            System.out.println("\nEscriba la descripción de la compra:");
            String descripcion = lectura.next();

            System.out.println("Escriba el valor de la compra:");
            double valor = lectura.nextDouble();

            Compra compra = new Compra(valor, descripcion);
            boolean compraRealizada = tarjeta.lanzarCompra(compra);

            if (compraRealizada) {
                System.out.println("✅ Compra realizada con éxito.");
                System.out.println("¿Desea realizar otra compra? (1 = sí, 0 = no): ");
                continuar = lectura.nextInt();
            } else {
                System.out.println("❌ Saldo insuficiente. No se pudo realizar la compra.");
                continuar = 0;
            }
        }

        mostrarResumen(tarjeta);
    }

    private static void mostrarResumen(TarjetaDeCredito tarjeta) {
        System.out.println("\n***********************");
        System.out.println("COMPRAS REALIZADAS:");

        // Crear una copia para ordenar sin modificar la original
        List<Compra> comprasOrdenadas = new ArrayList<>(tarjeta.getListaDeCompras());
        Collections.sort(comprasOrdenadas);

        for (Compra compra : comprasOrdenadas) {
            System.out.printf("%s - $%.2f\n", compra.getDescripcion(), compra.getValor());
        }

        System.out.println("***********************");
        System.out.printf("Saldo restante de la tarjeta: $%.2f\n", tarjeta.getSaldo());
    }
}
