package com.example.WeatherApp.repository;
import com.example.WeatherApp.entity.City;
import org.apache.poi.ss.usermodel.*;
        import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CityRepository {
    private static final String EXCEL_FILE_PATH = "cities.xlsx";

    public List<City> getAllCities() throws IOException {
        List<City> cities = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            // Skip header row
            if (row.getRowNum() == 0) continue;

            String name = row.getCell(0).getStringCellValue();
            double latitude = row.getCell(1).getNumericCellValue();
            double longitude = row.getCell(2).getNumericCellValue();

            cities.add(new City(name, latitude, longitude));
        }

        workbook.close();
        return cities;
    }

    public City getCityByName(String cityName) throws IOException {
        return getAllCities().stream()
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("City not found: " + cityName));
    }
}