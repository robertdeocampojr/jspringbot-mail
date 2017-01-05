package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Set Mail Auth",
        parameters = {"host", "username", "password"},
        description = "classpath:desc/SetMailAuth.txt"
)
public class SetMailAuth extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        helper.storeAuth(String.valueOf(params[0]), String.valueOf(params[1]), String.valueOf(params[2]));

        return null;
    }
}
