package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Get All Messages",
        description = "classpath:desc/GetAllMessages.txt"
)
public class GetAllMessages extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        return helper.getAllMessages();
    }
}
