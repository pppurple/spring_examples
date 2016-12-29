package not.recommend.field.injection.example.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import not.recommend.field.injection.example.service.MyServiceA;
import not.recommend.field.injection.example.service.MyServiceB;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequiredArgsConstructor
@Component
public class ConstructorInjectionWithLombok {
    @NonNull
    private final MyServiceA myServiceA;
    @NonNull
    private final MyServiceB myServiceB;
}
