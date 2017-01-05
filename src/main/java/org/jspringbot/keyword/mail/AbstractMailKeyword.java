package org.jspringbot.keyword.mail;

import org.jspringbot.Keyword;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractMailKeyword implements Keyword {
    @Autowired
    protected ReadMailHelper helper;

    public AbstractMailKeyword() {
    }
}
