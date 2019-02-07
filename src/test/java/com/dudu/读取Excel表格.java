package com.dudu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class 读取Excel表格 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String path="D:\\source\\documents\\WeChat Files\\happydyh100\\Files\\青云苑前期未备案名单2018.9.20.xls";
		read(path);
	}

	
	public static void read(String path) throws FileNotFoundException, IOException {
		//获取Excel对象
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(path));

        //获取一个Sheet对象
        HSSFSheet sheet = wb.getSheetAt(0);
     // 得到总行数
        int rowNum = sheet.getLastRowNum();
        HSSFRow  row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
     // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            System.out.print(row.getCell(2)+",");
            int j = 0;
            /*while (j < colNum) {
                Object obj = row.getCell(j);
                j++;
            }*/
        }
    }

	}

