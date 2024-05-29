package com.ut.market.service.util;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Component
public class PdfGenerator<T extends Serializable>{

    private final TemplateEngine templateEngine;

    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generatePdf(String template, T model, String fileName) throws IOException {
        Context context = new Context();
        context.setVariable("model", model);

        String html = templateEngine.process(template, context);

        String outputFolder = System.getProperty("user.home") + File.separator + "Documents" + File.separator +
                fileName +  ".pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputFolder;

    }


}
