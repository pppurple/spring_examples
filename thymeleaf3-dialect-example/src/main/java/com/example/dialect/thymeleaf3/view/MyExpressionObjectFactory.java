package com.example.dialect.thymeleaf3.view;

import com.example.dialect.thymeleaf3.view.helper.DateHelper;
import com.example.dialect.thymeleaf3.view.helper.StringHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyExpressionObjectFactory implements IExpressionObjectFactory {
    private static final String dateExpression = "dateHelper";
    private static final String stringExpression = "strHelper";

    private DateHelper dateHelper;
    private StringHelper strHelper;

    public MyExpressionObjectFactory(DateHelper dateHelper, StringHelper strHelper) {
        this.dateHelper = dateHelper;
        this.strHelper = strHelper;
    }

    private static final Set<String> expressionSet = new HashSet<String>() {
        {
            add(dateExpression);
            add(stringExpression);
        }
    };

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return expressionSet;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        switch (expressionObjectName) {
            case stringExpression:
                return strHelper;
            case dateExpression:
                return dateHelper;
            default:
                return null;
        }
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return false;
    }
}
