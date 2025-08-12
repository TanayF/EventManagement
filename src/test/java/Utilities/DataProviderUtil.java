package Utilities;

import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class DataProviderUtil {

    ExcelReader excelReader = new ExcelReader();

    @DataProvider(name = "hotelBookingData")
    public Object[][] getHotelBookingData() {
        String path = "src/test/resources/testdata/HotelBookingData.xlsx";
        List<Map<String, String>> testData = excelReader.getData(path, "Sheet1");

        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);  // Each row is a Map
        }
        return data;
    }

    @DataProvider(name = "ticketBookingData")
    public Object[][] getTicketBookingData() {
        String path = "src/test/resources/testdata/TicketBookingData.xlsx";
        List<Map<String, String>> testData = excelReader.getData(path, "Sheet1");

        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);  // Each row is a Map
        }
        return data;
    }
}
