import gui.LoginFrame;
import observer.Client;
import observer.ConcretClient;
import subject.MobilesSubject;
import subject.Subject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        new LoginFrame();
    }

}
