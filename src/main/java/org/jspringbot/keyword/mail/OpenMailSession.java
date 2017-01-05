package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Open Mail Session",
        parameters = {"protocol"},
        description = "classpath:desc/OpenMailSession.txt"
)
public class OpenMailSession  extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        helper.openMailSession(String.valueOf(params[0]));

        return null;
    }
}
