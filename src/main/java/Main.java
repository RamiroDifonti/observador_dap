import Observer.Client;
import Observer.ConcretClient;
import Subject.MobilesSubject;
import Subject.Subject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Client test = new ConcretClient("testMobile");
        Subject mobiles = new MobilesSubject();
/*        Subject laptops = new LaptopsSubject();
        Subject cpus = new CPUsSubject();*/
        mobiles.addObserver(test);
        try {
            // Leer el archivo JSON
            File file = new File("src/main/resources/data.json");
            Map<String, List<Product>> catalog = objectMapper.readValue(
                    file,
                    new TypeReference<Map<String, List<Product>>>() {}
            );

            // Imprimir datos para verificar
            System.out.println("Laptops:");
            catalog.get("laptops").forEach(laptop ->
                    System.out.println(laptop.getName() + " - $" + laptop.getPrice()));

            // Si estas mirando esta parte del código la modifiqué para que funcionará el observador
            // hay que quitarlo antes de la versión final...
            System.out.println("\nMóviles:");
            catalog.get("mobiles").forEach(mobile ->
                    mobiles.addProduct(mobile));

            System.out.println("\nCPUs:");
            catalog.get("cpus").forEach(cpu ->
                    System.out.println(cpu.getName() + " - $" + cpu.getPrice()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
