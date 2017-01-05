package org.jspringbot.keyword.mail;

import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;


@Component
@KeywordInfo(
        name = "Get Folder Message Count",
        description = "classpath:desc/GetFolderMessageCount.txt"
)
public class GetFolderMessageCount extends AbstractMailKeyword {


    @Override
    public Object execute(Object[] params) throws Exception {
        return helper.getFolderMessageCount();
    }
}
