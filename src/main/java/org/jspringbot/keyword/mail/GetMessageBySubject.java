package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Get Message By Subject",
        parameters = {"subject"},
        description = "classpath:desc/GetMessageBySubject.txt"
)
public class GetMessageBySubject extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        return helper.getMessageBySubject(String.valueOf(params[0]));
    }
}
