package Subject;

import Observer.Client;

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
    public abstract void addProduct(String product);
    public abstract String getProducts();
}
