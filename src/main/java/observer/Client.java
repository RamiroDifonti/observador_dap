package observer;

import subject.Subject;

public interface Client {
    public void update(Subject store);
    public String getName();
    public void loadFrame();
}
