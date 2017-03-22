package com.example.dialect.thymeleaf3.view;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

@Component
public class MyExpressionObjectDialect implements IExpressionObjectDialect {

    private MyExpressionObjectFactory myExpressionObjectFactory;

    public MyExpressionObjectDialect(MyExpressionObjectFactory myExpressionObjectFactory) {
        this.myExpressionObjectFactory = myExpressionObjectFactory;
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return myExpressionObjectFactory;
    }

    @Override
    public String getName() {
        return "MyExpressionObjectDialect";
    }
}
