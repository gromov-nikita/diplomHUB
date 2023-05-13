package com.gromov.service.dataExport.agreements;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.gromov.entity.OrderHistory;
import com.gromov.entity.enums.OrderAgreement;
import org.hibernate.criterion.Order;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderAgreementHandler {
    private static final String docTemplatePath = "src/main/resources/OrderAgreement.docx";
    private static final String docSavePath = "src/main/java/com/gromov/документы Docx/";
    public static void fillAgreement(OrderHistory order) {
        try {
            Calendar calendar = Calendar.getInstance();
            Document doc = new Document(docTemplatePath);
            doc.getRange().replace(OrderAgreement.CURRENT_DAY.getName(),
                    calendar.get(Calendar.DAY_OF_MONTH)+"",
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.CURRENT_MONTH.getName(),
                    calendar.getDisplayName(Calendar.MONTH,Calendar.LONG_FORMAT,new Locale("ru")),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.CURRENT_YEAR.getName(),
                    calendar.get(Calendar.YEAR)+"",
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.DRIVER_NAME.getName(),order.getDriver().getName(),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.CUSTOMER_NAME.getName(),order.getRequest().getUser().getName(),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.DRIVER_PRICE.getName(),order.getDriver().getPrice()+"",
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.SENDING_DATE.getName(),
                    order.getRequest().getDateSending().toLocalDate()
                            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("ru"))),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.DELIVERY_DATE.getName(),
                    order.getRequest().getDateDelivery().toLocalDate()
                            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy",new Locale("ru"))),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.FROM.getName(),order.getRequest().getFrom().getName(),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.TO.getName(),order.getRequest().getTo().getName(),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            doc.getRange().replace(OrderAgreement.MANAGER_NAME.getName(),order.getDriver().getUser().getName(),
                    new FindReplaceOptions(FindReplaceDirection.FORWARD));
            String date = new Date().toString();
            date = date.replaceAll(":","-");
            doc.save(docSavePath+"Договор " + date + ".docx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

