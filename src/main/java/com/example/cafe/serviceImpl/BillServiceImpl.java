package com.example.cafe.serviceImpl;

import com.example.cafe.Entity.Bill;
import com.example.cafe.JWT.JwtFilter;
import com.example.cafe.constents.CafeConstants;
import com.example.cafe.dao.BillDao;
import com.example.cafe.service.BillService;
import com.example.cafe.utils.CafaUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillDao billDao;

    @Autowired
    JwtFilter jwtFilter;

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ResponseEntity<String> generateReport(Map<String, String> requestMap) {
        log.info("Inside generateReport");
        try {
            String fileName = null;
            if (validate(requestMap)) {
                if (requestMap.containsKey("isGenerate")) {
                    Object isGenerateValue = requestMap.get("isGenerate");

                    if (isGenerateValue instanceof Boolean && !(Boolean) isGenerateValue) {
                        fileName = (String) requestMap.get("uuid");
                    } else {
                        fileName = CafaUtils.getUUID();
                        requestMap.put("uuid", fileName);
                        insertBill(requestMap);
                    }
                }
                String data = "Name: " + requestMap.get("name") + "\n" + "ContactNumber: " + requestMap.get("contactNumber")
                        + "\n" + "PaymentMethod: " + requestMap.get("paymentMethod");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph chuck = new Paragraph("Management Systerm", getFont("Header"));
                chuck.setAlignment(Element.ALIGN_BASELINE);
                document.add(chuck);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                createTableHeader(table);

                JSONArray jsonArray = CafaUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    createRow(table, CafaUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: " + requestMap.get("total") +
                        "Thank you for visiting. Please visit again!!", getFont("Data"));
                document.add(footer);
                return new ResponseEntity<>("{\"uuid\" :\"" + fileName + "\"}", HttpStatus.OK);
            }
            return CafaUtils.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafaUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void createRow(PdfPTable table, Map<String, Object> data) {
        log.info("Inside createRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private boolean validate(Map<String, String> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("total");

    }

    private void insertBill(Map<String, String> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("total")));
            bill.setProductDetail((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside document");
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private Font getFont(String type) {
        log.info("Inside file");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void createTableHeader(PdfPTable table) {
        log.info("Inside header");
        Stream.of("Name", "Category", "Quantity", "Price", "Sub total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }
}
