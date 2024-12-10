package utils;

public class Product {
    private String _name;
    private double _price;
    private String _image;

    // Getters y setters
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        this._name = name;
    }
    public double getPrice() {
        return _price;
    }
    public void setPrice(double price) {
        this._price = price;
    }
    public String getImage() {
        return _image;
    }
    public void setImage(String image) {
        this._image = image;
    }
}