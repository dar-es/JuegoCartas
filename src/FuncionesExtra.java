import java.util.*;

class Carta {
    String valor;  // Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
    String pinta;  // Corazones, Diamantes, Picas, Tréboles

    public Carta(String valor, String pinta) {
        this.valor = valor;
        this.pinta = pinta;
    }

    public int obtenerValor() {
        switch (valor) {
            case "Ace":
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            default: return 0;
        }
    }
}

public class JuegoApuntado {

    // Función para obtener las escaleras por pinta
    public static List<List<Carta>> obtenerEscaleras(List<Carta> cartas) {
        // Agrupar cartas por pinta
        Map<String, List<Carta>> cartasPorPinta = new HashMap<>();
        for (Carta carta : cartas) {
            cartasPorPinta.computeIfAbsent(carta.pinta, k -> new ArrayList<>()).add(carta);
        }

        List<List<Carta>> escaleras = new ArrayList<>();

        // Para cada pinta, buscar secuencias
        for (Map.Entry<String, List<Carta>> entry : cartasPorPinta.entrySet()) {
            List<Carta> cartasPinta = entry.getValue();
            Collections.sort(cartasPinta, (c1, c2) -> Integer.compare(c1.obtenerValor(), c2.obtenerValor()));

            List<Carta> secuencia = new ArrayList<>();
            for (int i = 0; i < cartasPinta.size(); i++) {
                if (secuencia.isEmpty()) {
                    secuencia.add(cartasPinta.get(i));
                } else {
                    if (cartasPinta.get(i).obtenerValor() == secuencia.get(secuencia.size() - 1).obtenerValor() + 1) {
                        secuencia.add(cartasPinta.get(i));
                    } else {
                        if (secuencia.size() >= 3) {  // Consideramos solo secuencias de 3 o más cartas
                            escaleras.add(new ArrayList<>(secuencia));
                        }
                        secuencia.clear();
                        secuencia.add(cartasPinta.get(i));
                    }
                }
            }
            if (secuencia.size() >= 3) {
                escaleras.add(secuencia);
            }
        }

        return escaleras;
    }

    // Función para calcular el puntaje
    public static int calcularPuntaje(List<Carta> cartas, List<List<Carta>> grupos) {
        Set<Carta> cartasEnGrupos = new HashSet<>();
        // Agregar todas las cartas que están en grupos (pares, terna, escaleras, etc.)
        for (List<Carta> grupo : grupos) {
            cartasEnGrupos.addAll(grupo);
        }

        int puntaje = 0;

        // Calcular puntaje de las cartas que no están en grupos
        for (Carta carta : cartas) {
            if (!cartasEnGrupos.contains(carta)) {
                puntaje += carta.obtenerValor();
            }
        }

        return puntaje;
    }

    public static void main(String[] args) {
        // Ejemplo de cartas repartidas
        List<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta("10", "Picas"));
        cartas.add(new Carta("Jack", "Picas"));
        cartas.add(new Carta("Queen", "Picas"));
        cartas.add(new Carta("King", "Picas"));
        cartas.add(new Carta("2", "Corazones"));
        cartas.add(new Carta("3", "Corazones"));
        cartas.add(new Carta("5", "Tréboles"));
        cartas.add(new Carta("7", "Diamantes"));
        cartas.add(new Carta("10", "Corazones"));
        cartas.add(new Carta("10", "Tréboles"));

        // Obtener escaleras por pinta
        List<List<Carta>> escaleras = obtenerEscaleras(cartas);
        System.out.println("Escaleras por pinta:");
        for (List<Carta> escalera : escaleras) {
            for (Carta c : escalera) {
                System.out.print(c.valor + " ");
            }
            System.out.println();
        }

        // Calcular puntaje
        int puntaje = calcularPuntaje(cartas, escaleras);
        System.out.println("Puntaje del jugador: " + puntaje);
    }
}
