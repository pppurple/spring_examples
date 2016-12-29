package not.recommend.field.injection.example.component;

import not.recommend.field.injection.example.service.MyServiceA;
import not.recommend.field.injection.example.service.MyServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterInjection {
    private MyServiceA myServiceA;
    private MyServiceB myServiceB;

    @Autowired
    public void setMyServiceA(MyServiceA myServiceA) {
        this.myServiceA = myServiceA;
    }

    @Autowired
    public void setMyServiceB(MyServiceB myServiceB) {
        this.myServiceB = myServiceB;
    }
}
