package com.example.CargoTracking.configuration;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ExcelImportHelper {
    public static boolean hasExcelFormat(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }

        // Convert the file name to lowercase to ensure case-insensitivity
        String lowercaseFilename = originalFilename.toLowerCase();

        // Check if the file has an Excel extension
        return lowercaseFilename.endsWith(".xlsx");
    }


    public static List<List<String>> parseExcelFile(MultipartFile file) {
        List<List<String>> rows = new ArrayList<>();
        boolean foundLastWrittenRow = false;

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to parse the first sheet

            for (Row row : sheet) {
                if (foundLastWrittenRow) {
                    break; // Stop parsing after the last written row
                }

                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell, workbook);
                    rowData.add(cellValue);
                }

                rows.add(rowData);

                if (rowData.isEmpty() || rowData.stream().allMatch(String::isEmpty)) {
                    foundLastWrittenRow = true; // Detecting empty row or row with all empty cells as last written row
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rows;
    }


    private static String getCellValueAsString(Cell cell, Workbook workbook) {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        if (cell == null) {
            return "";
        } else if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Handle numeric values without scientific notation
            if (DateUtil.isCellDateFormatted(cell)) {
                return formatDateCellValue(cell);
            } else {
                return formatNumericCellValue(cell);
            }
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            CellValue formulaResult = evaluator.evaluate(cell);
            if (formulaResult.getCellType() == CellType.NUMERIC) {
                return formatNumericCellValue(cell);
            } else if (formulaResult.getCellType() == CellType.STRING) {
                return formulaResult.getStringValue();
            } else if (formulaResult.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(formulaResult.getBooleanValue());
            }
            // You can handle other cell types here if needed
        }
        return "";
    }


    private static String formatDateCellValue(Cell cell) {
        LocalDate dateValue = cell.getLocalDateTimeCellValue().toLocalDate();
        return dateValue.format(DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH));
    }
    private static String formatNumericCellValue(Cell cell) {
        // Use DecimalFormat to format numeric cell values without scientific notation
        DecimalFormat decimalFormat = new DecimalFormat("#0.#####"); // Adjust the pattern as needed
        return decimalFormat.format(cell.getNumericCellValue());
    }
}
