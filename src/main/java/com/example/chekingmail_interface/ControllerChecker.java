package com.example.chekingmail_interface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class ControllerChecker {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn;

    @FXML
    public TextField mail;

    @FXML
    public TextField pass;

    @FXML
    public TextField host;

    @FXML
    public TextField path;

    @FXML
    public TextField filename;







    @FXML
    void initialize() {


        btn.setOnAction(event -> {

                try {

                    // create properties
                    Properties properties = new Properties();

                    properties.put("mail.imap.host", host);
                    properties.put("mail.imap.port", "993");
                    properties.put("mail.imap.starttls.enable", "true");
                    properties.put("mail.imap.ssl.trust", host);

                    Session emailSession = Session.getDefaultInstance(properties);

                    // create the imap store object and connect to the imap server
                    Store store = emailSession.getStore("imaps");

                    store.connect(host.getText(), mail.getText(), pass.getText());

                    // create the inbox object and open it
                    Folder inbox = store.getFolder("Inbox");
                    inbox.open(Folder.READ_WRITE);

                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Mails");

                    Row row = sheet.createRow(0);


                    // retrieve the messages from the folder in an array and print it
                    Message[] messages = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));

                    System.out.println("messages.length---" + messages.length);

                    for (int i = 0; i < messages.length; i++) {
                        Message message = messages[i];
                        message.setFlag(Flag.SEEN, true);
                        System.out.println("From: " + message.getFrom()[0]);
                        int rowIndex = i + 1;
                        row = sheet.createRow(rowIndex);
                        Cell mails = row.createCell(0);
                        mails.setCellValue(String.valueOf(message.getFrom()[0]));

                    }

                    sheet.autoSizeColumn(3);


                    FileOutputStream outputStream = new FileOutputStream(path.getText()+""+filename.getText()+".xlsx");
                    workbook.write(outputStream);
                    workbook.close();


                    inbox.close(false);
                    store.close();

                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Done");
            System.out.println(mail.getText());
            System.out.println(pass.getText());


        });

    }
}


