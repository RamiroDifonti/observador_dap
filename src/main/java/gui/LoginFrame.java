package gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import observer.Client;
import observer.ConcretClient;
import subject.MobilesSubject;
import subject.Subject;
import utils.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class LoginFrame extends JFrame {
    private JPanel _panel;
    private ObjectMapper _objectMapper = new ObjectMapper();
    private Subject _mobiles = new MobilesSubject();

/*    private Subject _laptops = new LaptopsSubject();
    private Subject _cpus = new CPUsSubject();*/
    public LoginFrame() {
        loadSuscriptors();
        Thread periodicLoader = new Thread(() -> {
            while (true) {
                try {
                    // Llamar a la función load
                    fetchData();

                    // Pausar el hilo durante 15 segundos
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break; // Salir del bucle si el hilo es interrumpido
                }
            }
        });

        // Establecer el hilo como demonio para que no bloquee la finalización del programa
        periodicLoader.setDaemon(true);

        // Iniciar el hilo
        periodicLoader.start();
        Start();
    }
    public void Start() {
        setLayout(new FlowLayout());
        // Crear un BoxLayout para ir introduciendo los objetos
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));
        _panel.setBorder(new EmptyBorder(40, 20, 20, 20));

        login();
        register();
        addProduct();


        add(_panel);
        revalidate();
        repaint();
        setTitle("Easy MarketPlace");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void login() {
        JPanel loginPanel = new JPanel();
        GridLayout layout = new GridLayout(1, 3);
        loginPanel.setLayout(layout);
        JLabel loginText = new JLabel("Introduzca un nombre de usuario: ");
        JTextField loginField = new JTextField();
        JButton loginButton = new JButton("Iniciar sesión");
        loginPanel.add(loginText);
        loginPanel.add(loginField);
        loginPanel.add(loginButton);
        _panel.add(loginPanel);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre de usuario.");
                    return;
                }
                if (exists(loginField.getText())) {
                    loadUser(loginField.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario no existe.");
                }
            }
        });
    }
    public void register() {
        JPanel registerPanel = new JPanel();
        GridLayout layout = new GridLayout(1, 3);
        registerPanel.setLayout(layout);
        JLabel registerText = new JLabel("Introduzca un nombre de usuario: ");
        JTextField registerField = new JTextField();
        JButton registerButton = new JButton("Registrar usuario");

        registerPanel.add(registerText);
        registerPanel.add(registerField);
        registerPanel.add(registerButton);
        _panel.add(registerPanel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registerField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre de usuario.");
                    return;
                }
                if(exists(registerField.getText())) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe.");
                } else {
                    remove(_panel);
                    addSuscriptions(registerField.getText());
                }
            }
        });
    }
    public void addProduct() {
        JButton addButon = new JButton("Añadir producto");
        _panel.add(addButon);

        addButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(_panel);
                addProductOptions();
            }
        });
    }
    public void addProductOptions() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));

        JLabel nameText = new JLabel("Nombre: ");
        JTextField nameField = new JTextField();
        JLabel priceText = new JLabel("Precio: ");
        JTextField priceField = new JTextField();
        JLabel imageText = new JLabel("Imagen: ");
        JTextField imageField = new JTextField();
        JButton addButon = new JButton("Añadir producto");

        _panel.add(nameText);
        _panel.add(nameField);
        _panel.add(priceText);
        _panel.add(priceField);
        _panel.add(imageText);
        _panel.add(imageField);
        _panel.add(addButon);

        addButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre al producto.");
                    return;
                }
                if (priceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un precio al producto.");
                    return;
                }
                // añadir producto
            }
        });
        add(_panel);
        revalidate();
        repaint();
    }
    public void addSuscriptions(String username) {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));

        JLabel suscriptionOptions = new JLabel("Seleccione a la información que se quiere suscribir:",  JLabel.CENTER);
        JPanel suscriptionPanel = new JPanel();
        suscriptionPanel.setLayout(new GridLayout(1, 3));
        JCheckBox laptopsCheckbox = new JCheckBox("Laptops");
        JCheckBox mobilesCheckbox = new JCheckBox("Mobiles");
        JCheckBox cpusCheckbox = new JCheckBox("CPUs");
        suscriptionPanel.add(laptopsCheckbox);
        suscriptionPanel.add(mobilesCheckbox);
        suscriptionPanel.add(cpusCheckbox);
        JButton registerButton = new JButton("Registrar usuario");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Boolean> subscriptions = new HashMap<>();
                subscriptions.put("laptops", laptopsCheckbox.isSelected());
                subscriptions.put("mobiles", mobilesCheckbox.isSelected());
                subscriptions.put("cpus", cpusCheckbox.isSelected());
                File jsonFile = new File("src/main/resources/users.json");
                // Añadir usuario con suscripciones
                try {
                    ObjectNode root = (ObjectNode) _objectMapper.readTree(jsonFile);
                    ObjectNode subscriptionNode = _objectMapper.createObjectNode();
                    subscriptionNode.put("laptops", laptopsCheckbox.isSelected());
                    subscriptionNode.put("mobiles", mobilesCheckbox.isSelected());
                    subscriptionNode.put("cpus", cpusCheckbox.isSelected());
                    ArrayNode userSubscriptionsArray = _objectMapper.createArrayNode();
                    userSubscriptionsArray.add(subscriptionNode);
                    root.set(username, userSubscriptionsArray);
                    _objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, root);

                    JOptionPane.showMessageDialog(null, "Usuario añadido correctamente");
                    remove(_panel);
                    loadSuscriptors();
                    Start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        _panel.add(suscriptionOptions);
        _panel.add(suscriptionPanel);
        _panel.add(registerButton);
        add(_panel);
        revalidate();
        repaint();
    }
    public void loadSuscriptors () {
        File jsonFile = new File("src/main/resources/users.json");
        try {
            // Leer el archivo JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonFile);

            // Iterar sobre los usuarios
            for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> entry = it.next();
                String userName = entry.getKey();
                JsonNode subscriptions = entry.getValue().get(0); // Asumimos que siempre hay un array con 1 objeto

                // Verificar las suscripciones
                Client client = new ConcretClient(userName);
                if (subscriptions.get("cpus").asBoolean()) {
                 /*   _cpus.addObserver(client);*/
                }
                if (subscriptions.get("laptops").asBoolean()) {
            /*        _laptops.addObserver(client);*/
                }
                if (subscriptions.get("mobiles").asBoolean()) {
                    _mobiles.addObserver(client);
                }
            }
            System.out.println("Usuarios cargados correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fetchData() {
        try {
            // Leer el archivo JSON
            File file = new File("src/main/resources/data.json");
            Map<String, List<Product>> catalog = _objectMapper.readValue(
                    file,
                    new TypeReference<Map<String, List<Product>>>() {}
            );

/*            catalog.get("laptops").forEach(laptop ->
                    _laptops.addProduct(laptop));*/

            catalog.get("mobiles").forEach(mobile ->
                    _mobiles.addProduct(mobile));

/*            catalog.get("cpus").forEach(cpu ->
                    _cpus.addProduct(cpu));*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // TODO
    public void loadUser(String username) {

    }
    private boolean exists(String username) {
        try {
            File file = new File("src/main/resources/users.json");
            Map<String, Object> userMap = _objectMapper.readValue(file, Map.class);

            if (userMap.containsKey(username)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
