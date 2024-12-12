package observer;

import subject.Subject;

public interface Observer {
    public void update(Subject store);
    public String getName();
    public void loadFrame();
}
