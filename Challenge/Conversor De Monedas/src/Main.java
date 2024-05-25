import javax.swing.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//La clase Main representa el punto de entrada de la aplicación conversor de monedas. Gestiona la interacción con el usuario, el flujo principal del programa y la lógica de conversión de moneda.
public class Main {
    public static void main(String[] args){
        int opcionUsuario = 0;
        ConsultaInfoMoneda consulta = new ConsultaInfoMoneda();
        ArrayList<Conversion> listaDeConversiones = new ArrayList<>();

        JOptionPane.showMessageDialog(null, "***** EL CONVERSOR DE MONEDAS *****");

        while (opcionUsuario != 3) {
            Main main = new Main();
            main.show_main_menu(opcionUsuario);
            opcionUsuario = main.read_main_option();

            switch (opcionUsuario) {
                case 1:
                    main.show_main_money("PRINCIPAL");
                    String monedaBase = main.read_money_option();
                    main.show_main_money("A CAMBIAR");
                    String monedaFinal = main.read_money_option();
                    double cantidadCambiar = main.read_amount_to_change();
                    Conversor conversor = consulta.buscaMoneda(monedaBase);
                    double tasaDeConversion = conversor.getConversionRate(monedaFinal.toUpperCase());
                    double cantidadObtenida = main.amount_obtained(monedaBase, cantidadCambiar, tasaDeConversion, monedaFinal);
                    Conversion nuevaConversion = new Conversion(monedaBase, monedaFinal, cantidadCambiar, cantidadObtenida);
                    listaDeConversiones.add(nuevaConversion);
                    break;
                case 2:
                    main.print_conversion(listaDeConversiones);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "GRACIAS, ACABAS DE SALIR DEL CONVERSOR DE MONEDAS");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "OPCION NO VALIDA");
            }

        }
    }

    private final String available_coins = """
            COP --- Peso Colombiano
            USD --- Dolar Estadounidense
            EUR --- Euro
            ARS --- Peso Argentino
            BRL --- Real Brasileño
            """;

    public void show_main_menu(int opcionPrevia){
        String menuPrincipal = """
                1) REALIZAR UNA CONVERSION
                2) HISTORIAL DE CONVERSIONES
                3) SALIR""";

        if (opcionPrevia != 0) {
            menuPrincipal = "Seleccione otra opcion: \n" + menuPrincipal;
        }

        JOptionPane.showMessageDialog(null, menuPrincipal);
    }

    public int read_main_option(){
        String opcionString = JOptionPane.showInputDialog(null, "OPCION: ");
        return Integer.parseInt(opcionString);
    }

    public void show_main_money(String denominacion){
        JOptionPane.showMessageDialog(null, "SELECCIONE LA MONEDA " + denominacion + ": \n" + available_coins);

    }

    public String read_money_option(){
        String opcion = JOptionPane.showInputDialog(null, "ESCOJE UNA OPCION VALIDA").toLowerCase();
        while (!available_coins.toLowerCase().contains(opcion)) {
            JOptionPane.showMessageDialog(null, "LA OPCION NO ESTA DISPONIBLE");
            opcion = JOptionPane.showInputDialog(null, "ESCOJE UNA OPCION VALIDA").toLowerCase();
        }
        return opcion.toUpperCase();
    }

    public double read_amount_to_change(){
        String cantidadString = JOptionPane.showInputDialog(null, "ESCRIBE LA CANTIDAD A CAMBIAR: ");
        double cantidad;
        while (!cantidadString.matches("[0-9.]+")) { // This regex checks for numbers with optional decimal point
            JOptionPane.showMessageDialog(null, "NUMERO NO VALIDO");
            cantidadString = JOptionPane.showInputDialog(null, "INGRESE NUEVAMENTE LA CANTIDAD");
        }
        cantidad = Double.parseDouble(cantidadString);
        return cantidad;
    }

    public double amount_obtained(String monedaBase, Double cantidadCambiar, Double tasaDeConversion, String monedaFinal){
        double resultado = cantidadCambiar * tasaDeConversion;
        JOptionPane.showMessageDialog(null, cantidadCambiar + " " + monedaBase + " EQUIVALE A: " + resultado + " " + monedaFinal);
        return resultado;
    }

    public void print_conversion(ArrayList<Conversion> listaDeConversiones) {
        if (listaDeConversiones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO HAY CONVERSIONES ACTUALMENTE \n");
        } else {
            JOptionPane.showMessageDialog(null, "HISTORIAL:");
            for (int i = 0; i < listaDeConversiones.size(); i++) {
                Conversion conversion = listaDeConversiones.get(i);
                String conversionDetails = "CONVERSION #" + (i + 1) + ":\n" +
                        "Moneda origen: " + conversion.getMonedaBase() + "\n" +
                        "Moneda objetivo: " + conversion.getMonedaObjetivo() + "\n" +
                        "Cantidad a cambiar: " + conversion.getCantidadACambiar() + "\n" +
                        "Cantidad obtenida: " + conversion.getCantidadEnMonedaObjetivo() + "\n" +
                        "Fecha y hora: " + formatDateTime(conversion.getTiempo());
                JOptionPane.showMessageDialog(null, conversionDetails);}
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

}