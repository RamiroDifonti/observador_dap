import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
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

            System.out.println("\nMobiles:");
            catalog.get("mobiles").forEach(mobile ->
                    System.out.println(mobile.getName() + " - $" + mobile.getPrice()));

            System.out.println("\nCPUs:");
            catalog.get("cpus").forEach(cpu ->
                    System.out.println(cpu.getName() + " - $" + cpu.getPrice()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
