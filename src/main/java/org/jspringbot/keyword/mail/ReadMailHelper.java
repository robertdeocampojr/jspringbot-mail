/*
 * Copyright (c) 2012. JSpringBot. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The JSpringBot licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jspringbot.keyword.mail;


import org.jspringbot.syntax.HighlightRobotLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.mail.*;
import java.util.Properties;


public class ReadMailHelper {

    public static final HighlightRobotLogger LOG = HighlightRobotLogger.getLogger(ReadMailHelper.class);

    protected Session session;

    protected Store store;

    protected Folder folder;

    protected String protocol;



    public void openSession(){
        try{
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            this.store = store;
        } catch(Exception e) {
            throw new IllegalStateException("Error in opening session: ", e);
        }
    }

    public void openMailSession(String protocol){
        try{
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", protocol);

            this.protocol = protocol;
            this.session = Session.getDefaultInstance(props, null);
            LOG.keywordAppender().appendArgument("Mail Session: ", session.getProperties());
        } catch(Exception e) {
            throw new IllegalStateException("Error in opening session: ", e);
        }
    }

    public void storeAuth(String host, String username, String password){
        try{
            store = session.getStore(protocol);
            store.connect(host, username, password);
            LOG.keywordAppender().appendArgument("Mail Session is connected?: ", store.isConnected());
        } catch(Exception e) {
            throw new IllegalStateException("Authentication problem. Please check connection or mail credentials.", e);
        }
    }

    public void storeAuth(String username, String password){
        try{
            store.connect("imap.gmail.com", username, password);
        } catch(Exception e) {
            throw new IllegalStateException("Authentication problem. Please check connection or mail credentials.", e);
        }
    }

    public  void setFolder(String folderName){
        try{
            folder = store.getFolder(folderName);
            folder.open(Folder.READ_WRITE);
        } catch(Exception e) {
            throw new IllegalStateException(String.format("Error accessing folder - %s", folderName), e);
        }
    }

    public int getFolderMessageCount() throws MessagingException {
        return folder.getMessageCount();
    }



    public List<String> getAllMessages(){
        List<String> listMessages = new ArrayList<String>(); ;

        try {
            Message[] messages = folder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String messageContent = "";
                Object content = message.getContent();

                if (content instanceof Multipart) {
                    Multipart mp = (Multipart) content;
                    for (int j = 0; j < mp.getCount(); j++) {
                        BodyPart bp = mp.getBodyPart(j);
                        if (Pattern
                                .compile(Pattern.quote("text/html"),
                                        Pattern.CASE_INSENSITIVE)
                                .matcher(bp.getContentType()).find()) {
                            // found html part
                            messageContent = (String) bp.getContent();
                        }
                    }
                }else{
                    messageContent = message.getContent().toString();
                }

            listMessages.add(messageContent);

            }
            LOG.keywordAppender().appendArgument("Messages: ", listMessages);
        } catch (Exception e) {
            throw new IllegalStateException("Error in getting messages.");
        }

        return listMessages;
    }


    public String getMessageBySubject(String subject){
        String messageContent = "";

        try {
            Message[] messages = folder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String actualSubject = message.getSubject();

                Object content = message.getContent();

                if (content instanceof Multipart) {
                    Multipart mp = (Multipart) content;
                    for (int j = 0; j < mp.getCount(); j++) {
                        BodyPart bp = mp.getBodyPart(j);
                        if (Pattern
                                .compile(Pattern.quote("text/html"),
                                        Pattern.CASE_INSENSITIVE)
                                .matcher(bp.getContentType()).find()) {
                            // found html part
                            messageContent = (String) bp.getContent();
                        }
                    }
                }else{
                    messageContent = message.getContent().toString();
                }

                if(subject.equals(actualSubject)){
                    LOG.keywordAppender().appendArgument("Message: ", messageContent);
                    return messageContent;
                }

            }

        } catch (Exception e) {
            throw new IllegalStateException("Error in getting messages.");
        }
        LOG.keywordAppender().appendArgument("Message: ", messageContent);
        return messageContent;
    }

    public void deleteAllMessages(){
        try {
            Message[] messages = folder.getMessages();
            Flags deleted = new Flags(Flags.Flag.DELETED);
            folder.setFlags(messages, deleted, true);
            folder.expunge();
        } catch (MessagingException e) {
            throw new IllegalStateException(String.format("Error in deleting messages in folder %s", folder.getName()), e);
        }
    }

    public void getMessages() throws Exception {
        Message[] messages = folder.getMessages();

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            Address[] fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            String subject = message.getSubject();
            String sentDate = message.getSentDate().toString();
            String messageContent = "";


            Object content = message.getContent();

            if (content instanceof Multipart) {
                Multipart mp = (Multipart) content;
                for (int j = 0; j < mp.getCount(); j++) {
                    BodyPart bp = mp.getBodyPart(j);
                    if (Pattern
                            .compile(Pattern.quote("text/html"),
                                    Pattern.CASE_INSENSITIVE)
                            .matcher(bp.getContentType()).find()) {
                        // found html part
                        messageContent = (String) bp.getContent();
                    }
                }
            }else{
                messageContent = message.getContent().toString();
            }


            // print out details of each message
            System.out.println("Message #" + (i + 1) + ":");
            System.out.println("\t From: " + from);
            System.out.println("\t Subject: " + subject);
            System.out.println("\t Sent Date: " + sentDate);
            System.out.println("\t Message: " + messageContent);
        }
    }

    public void closeFolder(){
        try {
            folder.close(false);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error in closing folder.", e);
        }
    }

    public void closeSession(){
        try {
            store.close();
        } catch (MessagingException e) {
            throw new IllegalStateException("Error in closing mail session.", e);
        }
    }
}
