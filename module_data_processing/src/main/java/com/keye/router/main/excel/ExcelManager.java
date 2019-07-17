package com.keye.router.main.excel;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * Created by keye on 2019/7/15.
 */

public class ExcelManager {
    private static ExcelManager instance;
    private static final String SHAREDSTRINGS = "xl/sharedStrings.xml";
    private static final String DIRSHEET = "xl/worksheets/";
    private static final String ENDXML = ".xml";

    private List<String> listCells;

    private ExcelManager() {

    }

    public static ExcelManager getInstance() {
        if (instance == null) {
            synchronized (ExcelManager.class) {
                if (instance == null) {
                    instance = new ExcelManager();
                }
            }
        }
        return instance;
    }

    public Map<String, List<List<String>>> analyzeXlsx(String fileName) {
        Map<String, List<List<String>>> map = new HashMap<>();
        InputStream isShareStrings = null;
        InputStream isXlsx = null;
        ZipInputStream zipInputStream = null;
        listCells = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(new File(fileName));
            ZipEntry sharedStringXML = zipFile.getEntry(SHAREDSTRINGS);
            isShareStrings = zipFile.getInputStream(sharedStringXML);
            //
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(isShareStrings, "utf-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != xmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlPullParser.getName();
                        if ("t".equals(tag)) {
                            listCells.add(xmlPullParser.nextText());
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
            Log.i("zzz", "analyze: list --> " + listCells);
            //
            isXlsx = new BufferedInputStream(new FileInputStream(fileName));
            zipInputStream = new ZipInputStream(isXlsx);
            ZipEntry zipDir;
            while ((zipDir = zipInputStream.getNextEntry()) != null) {
                String dirName = zipDir.getName();
                if (!zipDir.isDirectory() && dirName.endsWith(ENDXML)) {
                    if (dirName.contains(DIRSHEET)) {
                        parseSheet(zipFile, dirName, map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                isXlsx.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                isShareStrings.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Iterator<Map.Entry<String, List<List<String>>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<List<String>>> next = iterator.next();
            Iterator<List<String>> iterator1 = next.getValue().iterator();
            while (iterator1.hasNext()) {
                Log.i("zzz", "analyzeXls: sheet --> " + next.getKey() + " row --> " + iterator1.next());
            }
        }
        return map;
    }

    private void parseSheet(ZipFile zipFile, String entryName, Map<String, List<List<String>>> map) {
        int lastIndexOf = entryName.lastIndexOf(File.separator);
        String sheetName = entryName.substring(lastIndexOf + 1, entryName.length() - 4);
        //
        String v = null;
        List<String> colums = null;
        List<List<String>> rows = new ArrayList<>();
        InputStream inputStreamSheet = null;
        try {
            ZipEntry sheet = zipFile.getEntry(entryName);
            inputStreamSheet = zipFile.getInputStream(sheet);
            XmlPullParser xmlPullParserSheet = Xml.newPullParser();
            xmlPullParserSheet.setInput(inputStreamSheet, "utf-8");
            int evenTypeSheet = xmlPullParserSheet.getEventType();
            while (xmlPullParserSheet.END_DOCUMENT != evenTypeSheet) {
                switch (evenTypeSheet) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlPullParserSheet.getName();
                        if ("row".equalsIgnoreCase(tag)) {
                            colums = new ArrayList<>();
                        } else if ("v".equalsIgnoreCase(tag)) {
                            v = xmlPullParserSheet.nextText();
                            if (v != null) {
                                colums.add(listCells.get(Integer.parseInt(v)));
                            } else {
                                colums.add(v);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("row".equalsIgnoreCase(xmlPullParserSheet.getName()) && v != null) {
                            rows.add(colums);
                        }
                        break;
                }
                evenTypeSheet = xmlPullParserSheet.next();
            }
            if (rows != null && rows.size() > 0) {
                map.put(sheetName, rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamSheet.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, List<List<String>>> analyzeXls(String fileName) {
        Map<String, List<List<String>>> map = new HashMap<>();
        List<List<String>> rows;
        List<String> columns = null;
        try {
            Workbook workbook = Workbook.getWorkbook(new File(fileName));
            Sheet[] sheets = workbook.getSheets();
            for (Sheet sheet : sheets) {
                rows = new ArrayList<>();
                String sheetName = sheet.getName();
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] sheetRow = sheet.getRow(i);
                    int columnNum = sheet.getColumns();
                    for (int j = 0; j < sheetRow.length; j++) {
                        if (j % columnNum == 0) {
                            columns = new ArrayList<>();
                        }
                        columns.add(sheetRow[j].getContents());
                    }
                    rows.add(columns);
                }
                map.put(sheetName, rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
