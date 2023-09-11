package com.BookStore.BookStoreManager.controller;

import com.BookStore.BookStoreManager.repository.BookRepository;
import com.BookStore.BookStoreManager.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * for downloading excel file
     * @return downloadExcel
     * @throws IOException
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcel() throws IOException{
        return excelService.downloadExcel();
    }

    @GetMapping("/download/mybooks")
    public ResponseEntity<byte[]> downloadMyBooksExcel() throws IOException{
        return excelService.downloadMyBooksExcel();
    }
}
