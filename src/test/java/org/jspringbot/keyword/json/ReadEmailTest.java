package org.jspringbot.keyword.json;


import org.jspringbot.keyword.mail.ReadMailHelper;
import org.junit.Test;

import java.util.Properties;
import javax.mail.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-text.xml"})
public class ReadEmailTest {
    //@Autowired
    private ReadMailHelper helper = new ReadMailHelper();


    @Test
    public void testEmail() throws Exception {
        helper.openSession();
        helper.storeAuth("test.fundtube@gmail.com", "test1234!@#$");
        helper.setFolder("INBOX");

        System.out.println(helper.getFolderMessageCount());
        helper.getMessages();
    }


    public void testSample() throws Exception {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", "test.fundtube@gmail.com", "test1234!@#$");

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Message:" + folder.getMessageCount());
        System.out.println("Unread Message:" + folder.getUnreadMessageCount());


        boolean isMailFound = false;
        Message mailFromGod = null;

        // retrieve the messages from the folder in an array and print it
        Message[] messages = folder.getMessages();
        System.out.println("messages.length---" + messages.length);



        for (int i = 0, n = messages.length; i < n; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + message.getContent().toString());


            String line;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(message
                            .getInputStream()));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println(buffer);

        }

        //close the store and folder objects
        folder.close(false);
        store.close();

    }

}
