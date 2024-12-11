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
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.X_AXIS));
        _scrollPane = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public void update(Subject store) {
        _subjectState = store.getProducts();

        // Create a new product panel for each product
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        // Construct the image path based on the product name
        String imagePath = _subjectState.getImage();
        File imageFile = new File(imagePath);
        ImageIcon imageIcon;
        if (imageFile.exists()) {
            imageIcon = new ImageIcon(imagePath);
        } else {
            // Use a default image if the specified image is not found
            imageIcon = new ImageIcon("src/main/resources/images/default.jpg");
        }

        // Scale the image
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(1000, 1000, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);

        JLabel imageLabel = new JLabel(imageIcon);
        JLabel nameLabel = new JLabel("Name: " + _subjectState.getName(), JLabel.CENTER);
        JLabel priceLabel = new JLabel("Price: $" + _subjectState.getPrice(), JLabel.CENTER);

        productPanel.add(imageLabel, BorderLayout.NORTH);
        productPanel.add(nameLabel, BorderLayout.CENTER);
        productPanel.add(priceLabel, BorderLayout.SOUTH);

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
        System.out.println(_name + " has state: " + _subjectState);
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