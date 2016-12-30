package not.recommend.field.injection.example.component;

import not.recommend.field.injection.example.service.MyServiceA;
import not.recommend.field.injection.example.service.MyServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {
    private final MyServiceA myServiceA;
    private final MyServiceB myServiceB;

    @Autowired
    public ConstructorInjection(MyServiceA myServiceA, MyServiceB myServiceB) {
        this.myServiceA = myServiceA;
        this.myServiceB = myServiceB;
    }

    public String getText() {
        return myServiceA.getText() + ":" + myServiceB.getText();
    }
}
