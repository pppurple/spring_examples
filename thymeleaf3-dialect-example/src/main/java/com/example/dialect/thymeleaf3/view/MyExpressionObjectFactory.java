package com.example.dialect.thymeleaf3.view;

import com.example.dialect.thymeleaf3.view.helper.DateHelper;
import com.example.dialect.thymeleaf3.view.helper.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyExpressionObjectFactory implements IExpressionObjectFactory {
    private static final String dateExpression = "dateHelper";
    private static final String stringExpression = "strHelper";

    @Autowired
    private DateHelper dateHelper;
    @Autowired
    private StringHelper strHelper;

    private static final Set<String> set = new HashSet<String>() {
        {
            add(dateExpression);
            add(stringExpression);
        }
    };

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return set;
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
