import java.util.*;
import java.io.*;

public class Ahorcado {
    private static final String[] PALABRAS_PREDEFINIDAS = {
        "programacion", "java", "android", "computadora", "desarrollo"
    };
    private static final int INTENTOS_MAXIMOS = 6;
    
    private String palabraSecreta;
    private char[] palabraAdivinada;
    private int intentosRestantes;
    private Set<Character> letrasIntentadas;

    public static void main(String[] args) {
        new Ahorcado().iniciarJuego();
    }

    public void iniciarJuego() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("¿Cargar palabras desde archivo? (s/n)");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        
        if (respuesta.equals("s")) {
            cargarPalabrasDesdeArchivo();
        } else {
            palabraSecreta = seleccionarPalabraAleatoria(PALABRAS_PREDEFINIDAS);
        }

        if (palabraSecreta == null || palabraSecreta.isEmpty()) {
            System.out.println("No se encontraron palabras. Usando lista predeterminada.");
            palabraSecreta = seleccionarPalabraAleatoria(PALABRAS_PREDEFINIDAS);
        }

        inicializarJuego();
        
        while (intentosRestantes > 0 && !palabraCompleta()) {
            mostrarEstado();
            System.out.print("Ingresa una letra: ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("¡Por favor ingresa solo una letra válida!");
                continue;
            }
            
            char letra = input.charAt(0);
            procesarLetra(letra);
        }
        
        mostrarResultadoFinal();
        scanner.close();
    }

    private void inicializarJuego() {
        palabraAdivinada = new char[palabraSecreta.length()];
        Arrays.fill(palabraAdivinada, '_');
        intentosRestantes = INTENTOS_MAXIMOS;
        letrasIntentadas = new HashSet<>();
    }

    private String seleccionarPalabraAleatoria(String[] palabras) {
        Random random = new Random();
        return palabras[random.nextInt(palabras.length)];
    }
    
// Crear archivo 'palabras.txt' con la palabra secreta dentro y guardarlo en el almacenamiento interno.

    private void cargarPalabrasDesdeArchivo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("palabras.txt"));
            List<String> palabras = new ArrayList<>();
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                palabras.add(linea.trim().toLowerCase());
            }
            reader.close();
            
            if (!palabras.isEmpty()) {
                palabraSecreta = seleccionarPalabraAleatoria(palabras.toArray(new String[0]));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar archivo. Usando palabras predeterminadas.");
            palabraSecreta = seleccionarPalabraAleatoria(PALABRAS_PREDEFINIDAS);
        }
    }

    private void procesarLetra(char letra) {
        if (letrasIntentadas.contains(letra)) {
            System.out.println("Ya intentaste con la letra '" + letra + "'.");
            return;
        }

        letrasIntentadas.add(letra);

        if (palabraSecreta.indexOf(letra) >= 0) {
            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.charAt(i) == letra) {
                    palabraAdivinada[i] = letra;
                }
            }
            System.out.println("¡Correcto! La letra '" + letra + "' está en la palabra.");
        } else {
            intentosRestantes--;
            System.out.println("Incorrecto. La letra '" + letra + "' no está en la palabra.");
        }
    }

    private boolean palabraCompleta() {
        return new String(palabraAdivinada).equals(palabraSecreta);
    }

    private void mostrarEstado() {
        System.out.println("\n" + obtenerDibujoAhorcado());
        System.out.println("Palabra: " + String.valueOf(palabraAdivinada));
        System.out.println("Intentos restantes: " + intentosRestantes);
        System.out.println("Letras intentadas: " + letrasIntentadas);
    }

    private void mostrarResultadoFinal() {
        if (palabraCompleta()) {
            System.out.println("\n¡Felicidades! Adivinaste la palabra '"+ palabraSecreta + "'.");
        } else {
            System.out.println("\n¡Perdiste! La palabra era '" + palabraSecreta + "'.");
            System.out.println(obtenerDibujoAhorcado());
        }
    }

    private String obtenerDibujoAhorcado() {
        switch (intentosRestantes) {
            case 6: return
                "  +---+\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========";
            case 5: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "      |\n" +
                "      |\n" +
                "      |\n" +
                "=========";
            case 4: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                "  |   |\n" +
                "      |\n" +
                "      |\n" +
                "=========";
            case 3: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|   |\n" +
                "      |\n" +
                "      |\n" +
                "=========";
            case 2: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                "      |\n" +
                "      |\n" +
                "=========";
            case 1: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " /    |\n" +
                "      |\n" +
                "=========";
            case 0: return
                "  +---+\n" +
                "  |   |\n" +
                "  O   |\n" +
                " /|\\  |\n" +
                " / \\  |\n" +
                "      |\n" +
                "=========";
            default: return "";
        }
    }
}