package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Delete All Messages",
        description = "classpath:desc/DeleteAllMessages.txt"
)
public class DeleteAllMessages extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
         helper.deleteAllMessages();

         return null;
    }
}
