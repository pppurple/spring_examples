package not.recommend.field.injection.example;

import not.recommend.field.injection.example.component.ConstructorInjection;
import not.recommend.field.injection.example.component.ConstructorInjectionWithLombok;
import not.recommend.field.injection.example.component.FieldInjection;
import not.recommend.field.injection.example.component.SetterInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotRecommendFieldInjectionApplication implements CommandLineRunner {
	private final ConstructorInjection constructorInjection;
	private final FieldInjection fieldInjection;
	private final SetterInjection setterInjection;
	private final ConstructorInjectionWithLombok constructorInjectionWithLombok;

	public static void main(String[] args) {
		SpringApplication.run(NotRecommendFieldInjectionApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("[ConstructorInjection] " + constructorInjection.toString());
		System.out.println("[FieldInjection] " + fieldInjection.toString());
		System.out.println("[SetterInjection] " + setterInjection.toString());
		System.out.println("[ConstructorInjectionWithLombok] " + constructorInjectionWithLombok.toString());
	}

	@Autowired
	public NotRecommendFieldInjectionApplication(ConstructorInjection constructorInjection,
												 FieldInjection fieldInjection,
												 SetterInjection setterInjection,
												 ConstructorInjectionWithLombok constructorInjectionWithLombok) {
		this.constructorInjection = constructorInjection;
		this.fieldInjection = fieldInjection;
		this.setterInjection = setterInjection;
		this.constructorInjectionWithLombok = constructorInjectionWithLombok;
	}
}
