package subject;

import observer.Client;
import utils.Product;

import java.util.ArrayList;

public abstract class Subject {
    private ArrayList<Client> _clients = new ArrayList<>();
    public boolean addObserver(Client client) {
        try {
            _clients.add(client);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean removeObserver(Client client) {
        try {
            _clients.remove(client);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected void notifyUsers() {
        for (Client client : _clients) {
            client.update(this);
        }
    }
    public abstract void addProduct(Product product);
    public abstract Product getProducts();
}
