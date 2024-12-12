package subject;

import observer.Observer;
import utils.Product;

import java.util.ArrayList;

public abstract class Subject {
    private ArrayList<Observer> _observers = new ArrayList<>();
    public boolean addObserver(Observer observer) {
        try {
            _observers.add(observer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean removeObserver(Observer observer) {
        try {
            _observers.remove(observer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected void notifyUsers() {
        for (Observer observer : _observers) {
            observer.update(this);
        }
    }
    public boolean clientExists(Observer observer) {
        for (Observer c : _observers) {
            if (c.getName().equals(observer.getName())) {
                return true;
            }
        }
        return false;
    }
    public abstract void addProduct(Product product);
    public abstract Product getProducts();
    public abstract boolean exists(String name);
}
