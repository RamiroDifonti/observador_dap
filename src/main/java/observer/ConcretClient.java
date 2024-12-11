package observer;

import subject.Subject;
import utils.Product;

public class ConcretClient implements Client {
    private String _name;
    private Product _subjectState;
    public ConcretClient(String name) {
        _name = name;
    }

    @Override
    public void update(Subject store) {
        _subjectState = store.getProducts();
        System.out.println("Notified " + _name + " of new product: " + _subjectState.getName() + " with price: " + _subjectState.getPrice());
    }

    @Override
    public String getName() {
        return _name;
    }

    public void getSubjectState() {
        System.out.println(_name + " has state: " + _subjectState);
    }
}
