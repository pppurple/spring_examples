package not.recommend.field.injection.example.component;

import not.recommend.field.injection.example.service.MyServiceA;
import not.recommend.field.injection.example.service.MyServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {
    private MyServiceA myServiceA;
    private MyServiceB myServiceB;

    @Autowired
    public ConstructorInjection(MyServiceA myServiceA, MyServiceB myServiceB) {
        this.myServiceA = myServiceA;
        this.myServiceB = myServiceB;
    }
}
