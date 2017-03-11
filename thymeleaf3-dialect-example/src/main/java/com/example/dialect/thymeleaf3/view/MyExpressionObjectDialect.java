package com.example.dialect.thymeleaf3.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

@Component
public class MyExpressionObjectDialect implements IExpressionObjectDialect {
    @Autowired
    private MyExpressionObjectFactory myExpressionObjectFactory;

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return myExpressionObjectFactory;
    }

    @Override
    public String getName() {
        return "MyExpressionObjectDialect";
    }
}
