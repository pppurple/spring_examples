package com.example.dialect.thymeleaf3.view.helper;

import com.example.dialect.thymeleaf3.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StringHelper {
    @Autowired
    private StringUtil stringUtil;

    public String addFoo(String string) {
        return "Foo" + string;
    }

    public String addBar(String string) {
        return stringUtil.addPrefix("Bar", string);
    }
}
