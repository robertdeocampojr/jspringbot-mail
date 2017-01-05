package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Set Mail Folder",
        parameters = {"folder"},
        description = "classpath:desc/SetMailFolder.txt"
)
public class SetMailFolder extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        helper.setFolder(String.valueOf(params[0]));

        return null;
    }
}
