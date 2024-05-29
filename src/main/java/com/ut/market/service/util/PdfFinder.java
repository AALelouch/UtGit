package com.ut.market.service.util;

import com.ut.market.service.exception.NotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfFinder {

    private final String PATH_TO_INVOICES = System.getProperty("user.home") + File.separator + "Documents" + File.separator;

    public ByteArrayResource findPdfUsingIdAsLastDigits(String param){
        List<String> names = listAllFiles();

        String name = names.stream().filter(nameToFind -> nameToFind.endsWith("_" + param + ".pdf")).findFirst()
                .orElseThrow(() -> new NotFoundException("File not found"));

        File file = new File(PATH_TO_INVOICES + name);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;

        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        }catch (IOException ioException){
            System.out.println("Error generating invoice");
            System.out.println(ioException);
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getStackTrace());
        }

        return resource;
    }

    private List<String> listAllFiles(){
        List<String> names = new ArrayList<>();
        File folder = new File(PATH_TO_INVOICES);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    names.add(listOfFiles[i].getName());
                }
            }
        }
        return names;
    }

}
