package not.recommend.field.injection.example.component;

import not.recommend.field.injection.example.service.MyServiceA;
import not.recommend.field.injection.example.service.MyServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjection {
    @Autowired
    private MyServiceA myServiceA;
    @Autowired
    private MyServiceB myServiceB;

    public String getText() {
        return myServiceA.getText() + ":" + myServiceB.getText();
    }
}
