package Observer;

import Subject.Subject;

public class ConcretClient implements Client {
    private String _name;
    private String _subjectState;
    public ConcretClient(String name) {
        _name = name;
    }

    @Override
    public void update(Subject store) {
        _subjectState = store.getProducts();
        System.out.println("Notified " + _name + " of new product: " + _subjectState);
    }
    public void getSubjectState() {
        System.out.println(_name + " has state: " + _subjectState);
    }
}
