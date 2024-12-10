package subject;

import utils.Product;
import java.util.ArrayList;
import java.util.List;

public class MobilesSubject extends Subject {
    private List<Product> _products = new ArrayList<>();
    private Product _lastProduct;
    @Override
    public void addProduct(Product product) {
        _products.add(product);
        _lastProduct = product;
        notifyUsers();
    }
    @Override
    public Product getProducts() {
        return _lastProduct;
    }
}