package ties437.service.commons;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by chinhnk on 4/5/2017.
 */
public class Utils {
    public static double distanceFromCoords(double latitude1, double longitude1, double latitude2, double longitude2) {
        double latitudeOneRadian = Math.toRadians(latitude1);
        double latitudeTwoRadian = Math.toRadians(latitude2);
        double deltaLatitudeRadian = Math.toRadians(latitude1 - latitude2);
        double deltaLongitudeRadian = Math.toRadians(longitude1 - longitude2);
        double angle = Math.sin(deltaLatitudeRadian / 2) * Math.sin(deltaLatitudeRadian / 2) +
                Math.cos(latitudeOneRadian) * Math.cos(latitudeTwoRadian) *
                        Math.sin(deltaLongitudeRadian / 2) * Math.sin(deltaLongitudeRadian / 2);
        double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        return (Constants.EARTH_RADIUS * c);
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); // minus number would decrement the days
        return cal.getTime();
    }

    public static long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return Math.abs(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS));
    }

    public static InputStream getInputStreamFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

//        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//        String documentText = "";
//        String line;
//        while ((line = br.readLine()) != null) {
//            documentText += line + "\n";
//        }
//
//        conn.disconnect();
//
//        InputStream stream = new ByteArrayInputStream(documentText.getBytes());
        InputStream stream = conn.getInputStream();
        return stream;
    }
}
