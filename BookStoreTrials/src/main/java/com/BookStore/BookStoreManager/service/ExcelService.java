package com.BookStore.BookStoreManager.service;


import com.BookStore.BookStoreManager.entity.Book;
import com.BookStore.BookStoreManager.entity.MyBookList;
import com.BookStore.BookStoreManager.repository.BookRepository;
import com.BookStore.BookStoreManager.repository.MyBookRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private BookRepository bRepo;

    @Autowired
    private MyBookRepository mybRepo;

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    String dt = formatter.format(date);


//    public void allBookDownload(){
//        List<Book> dataList = bRepo.findAll();
//        downloadExcel(dataList);
//    }





//    public ResponseEntity<byte[]> downloadExcel() throws IOException {
//        List<Book> dataList = bRepo.findAll(); // Repo data
//        return downloadExcelCommon(dataList, "data.xlsx", "Data");
//    }
//
//    public ResponseEntity<byte[]> downloadMyBooksExcel() throws IOException {
//        List<MyBookList> dataList = mybRepo.findAll(); // Repo data
//        return downloadExcelCommon(dataList, "mybookdata.xlsx", "Data");
//    }
//



    public ResponseEntity<byte[]> downloadExcel()  throws IOException{
        List<Book> dataList = bRepo.findAll();

        // Create a ByteArrayOutputStream to store the Excel file data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //Create an XSSFWorkbook which is an Excel Workbook for the functionalities
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");//Creating an excel sheet



            CellStyle boldStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldStyle.setFont(boldFont);

            // Creating header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("List of Books");
            headerRow.getCell(0).setCellStyle(boldStyle);

            Row row1 = sheet.createRow(1);

            row1.createCell(0).setCellValue("Date");
            row1.createCell(1).setCellValue(dt);//dont hardcode
            row1.getCell(0).setCellStyle(boldStyle);


            Row row2 = sheet.createRow(2);

            row2.createCell(0).setCellValue("Librarian");
            row2.createCell(1).setCellValue("Vinayak");//
            row2.getCell(0).setCellStyle(boldStyle);

            Row row3 = sheet.createRow(4);
            row3.createCell(0).setCellValue("Id");
            row3.createCell(1).setCellValue("Book Name");
            row3.createCell(2).setCellValue("Author");//
            row3.createCell(3).setCellValue("Price");
            row3.getCell(0).setCellStyle(boldStyle);
            row3.getCell(1).setCellStyle(boldStyle);
            row3.getCell(2).setCellStyle(boldStyle);
            row3.getCell(3).setCellStyle(boldStyle);




//            Row headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("Book Name");
//            headerRow.createCell(1).setCellValue("Author");
//            headerRow.createCell(2).setCellValue("Price");


//
//            // Apply the bold style to each cell in the header row
//            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
//                headerRow.getCell(i).setCellStyle(boldStyle);
//            }

            // For filling rows
            int rowNum = 5;
            for (Book obj : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(obj.getId());
                row.createCell(1).setCellValue(obj.getName());
                row.createCell(2).setCellValue(obj.getAuthor());
                row.createCell(3).setCellValue(obj.getPrice());

            }

            workbook.write(outputStream);// Write the Excel workbook data to the ByteArrayOutputStream
        }

        byte[] excelBytes = outputStream.toByteArray();//converting os to bytearray

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "data.xlsx");

        return new ResponseEntity<>(excelBytes, headers, org.springframework.http.HttpStatus.OK);

    }


    public ResponseEntity<byte[]> downloadMyBooksExcel()  throws IOException{

        List<MyBookList> dataList = mybRepo.findAll(); // Repo data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            // Creating header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Book Name");
            headerRow.createCell(2).setCellValue("Author");
            headerRow.createCell(3).setCellValue("Price");

            CellStyle boldStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldStyle.setFont(boldFont);

            // Apply the bold style to each cell in the header row
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headerRow.getCell(i).setCellStyle(boldStyle);
            }

            // For filling rows
            int rowNum = 1;
            for (MyBookList obj : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(obj.getId());
                row.createCell(1).setCellValue(obj.getName());
                row.createCell(2).setCellValue(obj.getAuthor());
                row.createCell(3).setCellValue(obj.getPrice());

            }

            workbook.write(outputStream);
        }

        byte[] excelBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//binary format to be downloaded
        headers.setContentDispositionFormData("attachment", "mybookdata.xlsx");

        return new ResponseEntity<>(excelBytes, headers, org.springframework.http.HttpStatus.OK);

    }


}
