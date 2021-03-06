/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PdfGeneration;

/**
 *
 * @author dylan
 */
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.border.Border;
import static com.itextpdf.kernel.pdf.PdfName.DeviceGray;
import static com.itextpdf.kernel.pdf.PdfName.Text;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.test.annotations.WrapToTest;
import com.mycompany.snp.Person2;
import com.mycompany.snp.Person3;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

@WrapToTest
public class pdfQuotation {

    static int pgnotot = 0;
    protected PdfFormXObject template;
    public static String DEST = "results/Quotation/Steel/";

    public pdfQuotation(HashMap<String, Object> hm) throws IOException {

        DEST = "results/Quotation/Steel/";
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        DEST = DEST + hm.get("Quotation Number").toString().replace('.', '-') + ".pdf";
        System.out.println(DEST);
        createPdf(DEST, hm);

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(DEST);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                Utilities.AlertBox.showErrorMessage(ex);
            }

        }
    }

    

    public void createPdf(String dest, HashMap<String, Object> hm) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE,
                new Header(""));
        PageXofY event = new PageXofY(pdf);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, event);
        // Initialize document
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(18, 36, 36, 36);
        //Add paragraph to the document
        //  template = new PdfFormXObject(new Rectangle(550, 803, 30, 30));
        // PdfCanvas canvas = new PdfCanvas(template, pdf);

        // HeaderHandler headerHandler = new HeaderHandler();
        // pdf.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
        Image steelslogo = new Image(ImageDataFactory.create("images/SteelLogo.png"));
        Image extralogo1 = new Image(ImageDataFactory.create("InvoiceSC/Picture2.jpg"));
        Image extralogo2 = new Image(ImageDataFactory.create("InvoiceSC/Picture3.jpg"));
        Image extralogo3 = new Image(ImageDataFactory.create("InvoiceSC/Picture4.jpg"));

        //row 1 in pdf
        steelslogo.scaleAbsolute(215f, 50f);
        extralogo1.scaleAbsolute(130f, 55f);
        extralogo2.scaleAbsolute(130f, 55f);
        extralogo3.scaleAbsolute(90f, 55f);

        //for space
        Paragraph o1 = new Paragraph(" ");
        o1.setMarginLeft(17);//increase this value for increase in space

        //for font of address
        Style normal = new Style();
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        normal.setFont(font).setFontSize(9.5f);
        PdfFont calibri = PdfFontFactory.createFont("calibri/CALIBRIB.TTF", PdfEncodings.IDENTITY_H, true);
        Style cal = new Style();

        cal.setFont(calibri).setFontSize(9.5f);
        //address paragraph
        Paragraph address = new Paragraph().add(new Text("No. 12, Tuas View Place, Singapore - 637864.\n"
                + "No.109 Tuas South Ave.8 Singapore - 637037.\n"
                + "Tel: +65 6265 9476  Fax: +65 6265 7685.\n"
                + "Email: Enquires@steelcoat.com.sg  \n"
                + "Website: www.steelcoat.com.sg\n" + "Co. Reg No:   201410749G\n"
                + "GST. Reg No: 201410749G").addStyle(cal));
        address.setFixedLeading(11.5f);//line spacing
        address.setMarginLeft(15);//set text in paragraph a little to left
        Paragraph p = new Paragraph().add(steelslogo).add(address);
        // p.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(p);

        //row 2 in pdf
        Paragraph p1 = new Paragraph().add(extralogo1);
        Paragraph p2 = new Paragraph().add(extralogo2);
        Paragraph p3 = new Paragraph().add(extralogo3);
        Paragraph row2 = new Paragraph().add(p1).add(o1).add(p2).add(o1).add(p3);
        document.add(row2);

        //Paragraph o2 = new Paragraph("\n");
        o1.setMarginLeft(17);//increase this value for increase in space
        //document.add(o2);

        /*
        //table formation
        float[] columnWidths = {1,3,1,1,1};
        Table table = new Table(columnWidths);
        table.setWidthPercent(100);
        PdfFont f = PdfFontFactory.createFont("arial-narrow/ARIALNB.TTF", PdfEncodings.IDENTITY_H, true);
        Cell cell1 = new Cell(1, 5)
                .add(new Paragraph("TAX INVOICE").setFixedLeading(15))
                .setFont(calibri)
                .setFontSize(17)
                //.setBackgroundColor(DeviceGray.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(cell1);
        
        Cell celladd = new Cell(1,2);
        
        Paragraph p9=new Paragraph();
         PdfFont f1 = PdfFontFactory.createFont("arial-narrow/Arialn.ttf", PdfEncodings.IDENTITY_H, true);
        p9.add(new Text("To :").setFont(f).setFontSize(8))
                .add(new Paragraph(new Text("Menck Pte Ltd"))
                        .setFont(f).setFontSize(8).setMarginLeft(45));
       //p9.add(new Text("107, Tuas South Avenue 8, Acteon Singapore Operations Centre\n")
         //       .setFont(f1).setFontSize(8));
        Paragraph p10=new Paragraph("Kind Attn. :").setFont(f).setFontSize(8);
        p10.add(new Paragraph("Accounts Payable").setFont(f1).setFontSize(8).setMarginLeft(21));
//"Tel: (65) 62622282").setFont(f1).setFontSize(8).setMarginLeft(44));
        
        //p9.add(p10);
        celladd.add(p9)
                .add(new Paragraph("107, Tuas South Avenue 8, Acteon Singapore Operations Centre")
                .setFont(f1).setFontSize(8).setMarginLeft(56))
                .add(new Paragraph("Singapore - 637036")
                .setFont(f1).setFontSize(8).setMarginLeft(56))
                .add(p10)
                .add(new Paragraph("Tel: (65) 62622282").setFont(f1).setFontSize(8).setMarginLeft(56))
                .setBorderRight(Border.NO_BORDER);
        
        
       Table in1=new Table(new float[] {1,1});
       
        in1.setWidthPercent(100);
        Cell inv =new Cell(1,1);
        
        //inv.setNextRenderer(new DottedLineCellRenderer(inv));
        inv.add(new Paragraph("Invoice No:\n1803-SC-INV-01").setFont(f).setFontSize(8)).setBorderTop(Border.NO_BORDER);
        inv.setWidth(98.9f);
        Cell d =new Cell(1,1).setBorderTop(Border.NO_BORDER);
        
        d.add(new Paragraph("Date: ")
                .setFont(f).setFontSize(8));
                d.add(new Paragraph("22-01-2018").setFont(f1).setFontSize(8).setFontColor(Color.RED));
        Cell so =new Cell(1,1)
                .add(new Paragraph("Our S/O No: N/A.").setFont(f).setFontSize(8));//.setBorder(Border.NO_BORDER);
        //so.setNextRenderer(new DottedLineCellRenderer(so));
        Cell sp =new Cell(2,1);
        sp.add(new Paragraph("Salesperson:\n").setFont(f).setFontSize(8)
                .add(new Text("Sri Vidya").setFont(f1).setFontSize(8)));
        Cell jo =new Cell(1,1)
                .add(new Paragraph("Our Job No:").setFont(f).setFontSize(8));
        Cell pjo =new Cell(1,1)
                .add(new Paragraph("Your Order Ref No:\nProject No:").setFont(f).setFontSize(8));
        Cell pjno1 =new Cell(1,1)
                .add(new Paragraph("PO13309 \nJ11230").setFont(f1).setFontSize(8));
        Cell tem =new Cell(1,1)
                .add(new Paragraph("Terms of payment:").setFont(f).setFontSize(8)); 
        Cell jo1 =new Cell(1,1)
                .add(new Paragraph("30 days").setFont(f1).setFontSize(8));
        Cell kp =new Cell(1,2).add(new Paragraph("Page No 1 of 1").setTextAlignment(TextAlignment.CENTER).setFont(f).setFontSize(8));
        in1.addCell(inv).addCell(d).addCell(so).addCell(sp).addCell(jo).addCell(pjo).addCell(pjno1)
                .addCell(tem).addCell(jo1).addCell(kp);
        
        Cell a= new Cell(1,3).add(in1).setPadding(0).setBorder(Border.NO_BORDER);
        
        table.addCell(celladd)
                .addCell(a);
        
        //Invoice Table
        
        
        //Table ti =new Table(new float[]{1,4,1,1,1});
       //ti.setWidthPercent(100);
        table.addCell(new Cell(1,1).setPaddingTop(10).setPaddingBottom(10).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(new Text("Item/No:").setFont(f).setFontSize(8))));
        Cell b =new Cell(1,1).setPaddingTop(10).setPaddingBottom(10).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(new Text("Description").setFont(f)).setFontSize(8));
        b.setWidthPercent(100);
        b.setWidth(285f);
       
        table.addCell(b);
        table.addCell(new Cell(1,1).setPadding(10).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(new Text("Qty").setFont(f).setFontSize(8))));
       
        table.addCell(new Cell(1,1).setPaddingTop(10).setPaddingBottom(10).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(new Text("Unit Price (SGD)").setFont(f).setFontSize(8))));
        table.addCell(new Cell(1,1).setPaddingTop(10).setPaddingBottom(10).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(new Text("Total Price (SGD)").setFont(f).setFontSize(8))));
        
        table.addCell(new Cell(1,1).setPadding(8).add(new Paragraph("1").setTextAlignment(TextAlignment.CENTER).setFont(f).setFontSize(8)));
         table.addCell(new Cell(1,1).setPadding(8).add(new Paragraph("Sand Blasting / Coating of AF SN63177").setTextAlignment(TextAlignment.CENTER).setFont(f1).setFontSize(8)));
          table.addCell(new Cell(1,1).setPadding(8).add(new Paragraph("1").setFont(f).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));
           table.addCell(new Cell(1,1).setPadding(8).add(new Paragraph("$4,469.00").setFont(f).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));
           table.addCell(new Cell(1,1).setPadding(8).add(new Paragraph("$4,469.00").setFont(f).setFontSize(8).setTextAlignment(TextAlignment.CENTER)));
        for(int j=0;j<4;j++)   
         for(int i=0;i<5;i++){  
            table.addCell(new Cell(1,1).setPadding(12).add(new Paragraph().add(new Text("\t")).setTextAlignment(TextAlignment.CENTER).setFont(f1).setFontSize(8)));  
         }
        Color magentaColor = new DeviceCmyk(0.f, 0.f, 0.f, 1.f);
        
      
                //table.addCell(new Cell(1,3).add(ti).setBorder(Border.NO_BORDER).setPadding(0));
                
                
       
         
         
         Table t1=new Table(new float[]{3,2,1});
         t1.setWidthPercent(100);
          PdfFont f2 = PdfFontFactory.createFont("arial-narrow/ARIALNBI.TTF", PdfEncodings.IDENTITY_H, true);
         t1.addCell(new Cell(3,1).setVerticalAlignment(VerticalAlignment.MIDDLE).setWidth(250).add(new Paragraph("Note. Any discrepancy in the invoice or the time sheet shall be "
                 + "brought to our notice within 48 hours of submission of the invoice.").setFont(f2).setFontSize(8)));
         t1.addCell(new Cell(1,1).setWidth(191.3f).add(new Paragraph("Total").setTextAlignment(TextAlignment.RIGHT).setMarginRight(7).setFont(f).setFontSize(8)));
         t1.addCell(new Cell(1,1).add(new Paragraph("$4,469.00").setTextAlignment(TextAlignment.CENTER).setFont(f).setFontSize(8)));
         t1.addCell(new Cell(1,1).setWidth(191.3f).add(new Paragraph("Add GST 7%").setTextAlignment(TextAlignment.RIGHT).setMarginRight(7).setFont(f).setFontSize(8)));
         t1.addCell(new Cell(1,1).add(new Paragraph("$312.83").setTextAlignment(TextAlignment.CENTER).setFont(f).setFontSize(8)));
         t1.addCell(new Cell(1,1).setWidth(191.3f).add(new Paragraph("Amount Due").setTextAlignment(TextAlignment.RIGHT).setMarginRight(7).setFont(f).setFontSize(8)));
           t1.addCell(new Cell(1,1).add(new Paragraph("$4,781.83").setTextAlignment(TextAlignment.CENTER).setFont(f).setFontSize(8)));
         
           Cell c = new Cell(1,5).add(t1).setBorder(Border.NO_BORDER).setPadding(0);
           
         table.addCell(c);
         Table t2= new Table(2);
         
         Cell fin=new Cell(1,1)
                .add(new Paragraph("All cheques should be crossed and made payable to \"Steel Coat Pte Ltd\".").setFont(f1).setFontSize(8))
                .add(new Paragraph("\n"))
                .add(new Paragraph("OCBC A/c No: 686515487001").setFont(f1).setFontSize(8))
                .add(new Paragraph("All cheques accounts will accrue interest at a rate of 1.5% for each 60days or part thereof beyond due date.")
                .setFont(f1).setFontSize(8)).add(new Paragraph("\n ")).setBorder(Border.NO_BORDER);
         //fin.setWidth(350);
         t2.addCell(fin);
         t2.addCell(new Cell(1,1).setBorder(Border.NO_BORDER).add(new Paragraph("\n\n\n\n\n\n\n\tFor and on behalf of Steel Coat Pte Ltd\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                 + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t").setFont(f1).setFontSize(8).setUnderline(1.0f, 7.5f)));
        table.addCell(new Cell(1,5).add(t2).setPadding(0));
          document.add(table);
        
        System.out.println();
         System.out.println(pdf.getNumberOfPages());
            PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
         canvas.setStrokeColor(magentaColor)
               .moveTo(55, 395).lineTo(550, 320).closePathStroke();
  
         */
        Paragraph r1 = new Paragraph("Offer").setTextAlignment(TextAlignment.RIGHT)
                .setFont(PdfFontFactory.createFont("trebuchet-ms/trebucbd.ttf", PdfEncodings.IDENTITY_H, true))
                .setFontSize(12);
        Style arial = new Style();//arial-narrow/arial.ttf
        PdfFont f1f = PdfFontFactory.createFont("arial-unicode-ms/ARIALUNI.TTF", PdfEncodings.IDENTITY_H, true);//espar-arial-classic/esparac.ttf
        arial.setFont(f1f).setFontSize(9.5f);
        Table q1 = new Table(new float[]{1, 1});

        //Paragraph q1=new Paragraph();
        Paragraph q11 = new Paragraph().add((String) hm.get("Customer Name") + "\n"
                + (String) hm.get("toAddress") + "\n"
                + "Phone: " + (String) hm.get("Customer Phone") + "\n"
                + "Email: " + (String) hm.get("Customer Mail") + "\n")
                .setTextAlignment(TextAlignment.LEFT).addStyle(arial).setFixedLeading(13);
        Cell q2 = new Cell(1, 1).add(q11).setBorder(Border.NO_BORDER).setPadding(0);
        Text t = new Text("Our Quote:  " + (String) hm.get("Quotation Number"));

        Paragraph q12 = new Paragraph().add("Quoted By: Vijay\n"
                + "Phone: +65 6778 8271 / +65 81899206\n"
                + "Fax: +65 6265 7685\n"
                + "email: vijay@steelcoat.com.sg\n"
                + "Date:" + (String) hm.get("Date") + "\n").add(t)
                .setTextAlignment(TextAlignment.RIGHT).addStyle(arial).setFixedLeading(13);
        t.setFont(PdfFontFactory.createFont("arial-narrow/ARIALNB.TTF", PdfEncodings.IDENTITY_H, true));
        Cell q21 = new Cell(1, 1).add(q12).setBorder(Border.NO_BORDER).setPadding(0);
        q1.addCell(q2).addCell(q21).setWidthPercent(100);

        Style arialn = new Style();//arial-narrow/arial.ttf
        PdfFont f1fn = PdfFontFactory.createFont("arial-narrow/Arialn.ttf", PdfEncodings.IDENTITY_H, true);//espar-arial-classic/esparac.ttf
        arialn.setFont(f1fn).setFontSize(9.5f);
        Paragraph q3 = new Paragraph();
        q3.add("Attention to Project Manager,\n\n"
                + "Thank you for inviting us to quote for the above mentioned job. We are pleased to submit our quotation for your\n"
                + "perusal and approval.\n"
                + ((String) hm.get("Subject")).trim() + "\n").addStyle(arialn);

        document.add(r1).add(q1).add(q3);

        //main table
        Table t2 = new Table(new float[]{0.25f, 0.5f, 3, 1, 1, 1, 1});
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("S.N").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("Position").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("Normal Rate/Hr (S$)\n" + "(Mon to Fri 8am to 5pm)").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("Beyond Normal Hours and\n" + "Saturdays (S$)	").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("Sundays and Public\n" + "Holidays (S$)").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addHeaderCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("Remarks").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        int roman_counter = 0;
        String arr[] = {"l",
            "ll",
            "lll",
            "lV",
            "V",
            "Vl",
            "Vll",
            "Vlll",
            "lX",
            "X", "Xl", "Xll", "Xll", "XlV", "XV", "XVl", "XVll", "XVll", "XVlll", "XlX", "XX"
        };
        t2.addCell(new Cell(1, 1).setPadding(0).setBorder(Border.NO_BORDER).add(new Paragraph(arr[roman_counter])
                .setFont(PdfFontFactory.createFont("arial-narrow/ARIALNB.TTF", PdfEncodings.IDENTITY_H, true))
                .setFontSize(9.5f).setTextAlignment(TextAlignment.CENTER)));
        t2.startNewRow();
        roman_counter++;
        ObservableList<Person2> trc = (ObservableList) hm.get("TableItems");
        ListIterator<Person2> li = trc.listIterator();
        int inti = -99;//inital SN value
        boolean co = true;
        while (li.hasNext()) {
            Person2 pe = li.next();
            if (pe.getSN().getText().isEmpty()) {
                break;
            }
            if ((inti + 1 == Integer.valueOf((String) pe.getSN().getText().trim())) || co) {
                inti = Integer.valueOf((String) pe.getSN().getText().trim());
                //for(int l=0;l<2;l++){
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setWidth(50.0f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getSN().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getPosition().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.LEFT)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getNormalRate().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getBeyondNormalHours().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getHolidays().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph((String) pe.getRemarks().getText().trim()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
                //}
                co = false;
            } else {
                t2.addCell(new Cell(1, 1).setPadding(0).setBorder(Border.NO_BORDER).add(new Paragraph(arr[roman_counter])
                        .setFont(PdfFontFactory.createFont("arial-narrow/ARIALNB.TTF", PdfEncodings.IDENTITY_H, true))
                        .setFontSize(9.5f).setTextAlignment(TextAlignment.CENTER)));
                t2.startNewRow();
                roman_counter++;
                co = true;
            }
        }

        /* String arr1[] = {"Skilled / Certified Blaster",
            "Skilled / Certified Painter",
            "Painting Helper",
            "Power Tool Men",
            "Nace Inspector Level.2"};
        for (int i = 0; i < 5; i++) {

            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setWidth(50.0f).setBorder(Border.NO_BORDER).add(new Paragraph(Integer.valueOf(i + 1).toString()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph(arr1[i]).addStyle(arialn).setTextAlignment(TextAlignment.LEFT)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("$18.00").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("1.5 x Normal Rate").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("2 x Normal Rate").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));

        }*/
        //t2.setWidthPercent(100);
        //document.add(t2);
        Paragraph p7 = new Paragraph("\nTerms & Condition.").addStyle(arialn);
        t2.addCell(new Cell(1, 3).add(p7).setBorder(Border.NO_BORDER));
        t2.startNewRow();
        //Table t4 =new Table(new float[]{0.5f,3});
        String arr3[] = {"Quote shall remain valid for 7days.",
            "Workmen Compensatioin by Steel Coat Pte Ltd & Other",
            "Work Coordination by Acqatic at site",
            "PPE, Trasportaion & Lodging by Steel Coat Pte Ltd",
            "Invoice will be issued twice month and timecard to be released on every 15th and 30th/31st of the month.",
            "Payment Terms : 30 days from the date of invoice",
            "Work order to be issued prior to commencement of works"};
        for (int i = 0; i < 7; i++) {
            t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 1).setWidth(50.0f).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph(Integer.valueOf(i + 1).toString()).addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
            t2.addCell(new Cell(1, 5).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph(arr3[i]).addStyle(arialn).setTextAlignment(TextAlignment.LEFT)));
        }
        Image extralogo4 = new Image(ImageDataFactory.create("InvoiceSC/Picture5.jpg"));//signature and stamp on page 1
        t2.setWidthPercent(100);
        t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").addStyle(arialn).setTextAlignment(TextAlignment.CENTER)));
        t2.addCell(new Cell(1, 1).setPadding(0.1f).setBorder(Border.NO_BORDER).add(new Paragraph("").add(extralogo4)));
        extralogo4.scaleAbsolute(100f, 55f);
        document.add(t2);
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(p);

        document.add(row2);
//          document.add(o2);

        Paragraph p4 = new Paragraph("Should you have further enquiries,please contact Vijay @ 81899206, if this quotes"
                + " meet your requirments, \nKindly endorese and return for our record. "
                + "\n\nWe trust our offer is acceptable to your and look forward to receiving your favourable reply soon.\n\n")
                .addStyle(arialn).setFontSize(9.5f).setTextAlignment(TextAlignment.LEFT);

        Table t5 = new Table(new float[]{1, 1, 2});
        t5.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER).add(new Paragraph("Thank you.\n\nYours faithfully,").addStyle(arialn).setFontSize(9.5f).setTextAlignment(TextAlignment.LEFT)));
        t5.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER).add(extralogo4).setHorizontalAlignment(HorizontalAlignment.LEFT));
        t5.addCell(new Cell(1, 1).setBorder(Border.NO_BORDER).add(new Paragraph().add(new Text("M/S : Aquatic Asia Pacific Pte Ltd".toUpperCase()).setFontSize(9.5f)
                .setFont(PdfFontFactory.createFont("arial-narrow/ARIALNB.TTF", PdfEncodings.IDENTITY_H, true)))
                .add("\nWe accept the terms and conditions").addStyle(arialn).setFontSize(9.5f)));
        Image steelslogo1 = new Image(ImageDataFactory.create("images/SteelLogo.png"));
        steelslogo1.scaleAbsolute(130f, 45f);
        t5.addCell(new Cell(1, 2).setBorder(Border.NO_BORDER).add(steelslogo1));
        t5.setWidthPercent(100);
        t5.addCell(new Cell(2, 1).setBorder(Border.NO_BORDER).add(new Paragraph("Name:-------------------------------------------------"
                + "\n\nDesignation:-----------------------------------------").addStyle(arialn)
                .setFontSize(9.5f).setTextAlignment(TextAlignment.LEFT)).setVerticalAlignment(VerticalAlignment.MIDDLE));
        document.add(p4).add(t5);
        event.writeTotal(pdf);
        document.close();

    }

    protected class Header implements IEventHandler {

        String header;

        public Header(String header) {
            this.header = header;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            if (pdf.getPageNumber(page) == 1) {
                return;
            }
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.newContentStreamBefore(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas.showTextAligned(header,
                    pageSize.getWidth() / 2,
                    pageSize.getTop() - 30, TextAlignment.CENTER);
        }
    }

    protected class PageXofY implements IEventHandler {

        protected PdfFormXObject placeholder;
        protected float side = 20;
        protected float x = 300;
        protected float y = 25;
        protected float space = 4.5f;
        protected float descent = 3;

        public PageXofY(PdfDocument pdf) {
            placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdf.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.newContentStreamBefore(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            Style arialn = new Style();//arial-narrow/arial.ttf
            PdfFont f1fn;
            try {
                f1fn = PdfFontFactory.createFont("arial-narrow/Arialn.ttf", PdfEncodings.IDENTITY_H, true);
                arialn.setFont(f1fn).setFontSize(8.5f);//espar-arial-classic/esparac.ttf
            } catch (IOException ex) {
                Logger.getLogger(pdfQuotation.class.getName()).log(Level.SEVERE, null, ex);
            }

            Paragraph p = new Paragraph()
                    .add("Page ").add(String.valueOf(pageNumber)).add(" of").addStyle(arialn);
            canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
            pdfCanvas.addXObject(placeholder, x + space, y - descent);
            pdfCanvas.release();
        }

        public void writeTotal(PdfDocument pdf) {
            Canvas canvas = new Canvas(placeholder, pdf);
            try {
                canvas.setFont(PdfFontFactory.createFont("arial-unicode-ms/ARIALUNI.TTF", PdfEncodings.IDENTITY_H, true)).setFontSize(8.5f);
            } catch (IOException ex) {
                Logger.getLogger(pdfQuotation.class.getName()).log(Level.SEVERE, null, ex);
            }
            canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()),
                    0, descent, TextAlignment.LEFT);
        }
    }
}
