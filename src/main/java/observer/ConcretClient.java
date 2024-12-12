package observer;

import subject.Subject;
import utils.Product;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ConcretClient extends JFrame implements Client {
    private String _name;
    private Product _subjectState;
    private JPanel productsPanel;
    private JScrollPane _scrollPane;

    public ConcretClient(String name) {
        _name = name;
        setVisible(false);
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS)); // Use vertical BoxLayout
        _scrollPane = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    public void update(Subject store) {
        _subjectState = store.getProducts();

        // Create a new product panel for each product
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());
        productPanel.setPreferredSize(new Dimension(800, 150)); // Reduce the height to make panels closer

        // Retrieve the image path from the Product object
        String imagePath = _subjectState.getImage();
////        System.out.println("Image path: " + imagePath); // Debug statement
        File imageFile = new File(imagePath);
        ImageIcon imageIcon;
        if (!imagePath.isEmpty()) {
            imageIcon = new ImageIcon(imagePath);
//            System.out.println("Image loaded successfully: " + imagePath); // Debug statement
        } else {
            // Use a default image if the specified image is not found
//            System.out.println("Image not found, using default image."); // Debug statement
            imageIcon = new ImageIcon("src/main/resources/images/default.png");
        }

        // Scale the image
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);

        JLabel imageLabel = new JLabel(imageIcon);
        JLabel nameLabel = new JLabel(_subjectState.getName());
        JLabel priceLabel = new JLabel("$" + _subjectState.getPrice());

        // Create a panel to hold the name and price labels
        JPanel namePricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePricePanel.add(nameLabel);
        namePricePanel.add(priceLabel);

        productPanel.add(imageLabel, BorderLayout.NORTH);
        productPanel.add(namePricePanel, BorderLayout.CENTER);

        // Remove the margin between product panels
        productPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        productsPanel.add(productPanel);

        // Refresh the scroll pane
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    @Override
    public String getName() {
        return _name;
    }

    public void getSubjectState() {
//        System.out.println(_name + " has state: " + _subjectState);
    }

    @Override
    public void loadFrame() {
        setTitle(_name);
        setSize(800, 600); // Adjusted size for better horizontal scrolling
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(_scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}