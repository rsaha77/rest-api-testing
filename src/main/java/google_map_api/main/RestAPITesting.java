package google_map_api.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import google_map_api.entity.LocationEntity;

public class RestAPITesting {

   public static void main (String [] args) throws IOException, JSONException {
      StringBuilder allLocations = getAllLocations ();
      checkLocationsFromGoogleMapAPI (allLocations);
   }

   public static StringBuilder getAllLocations () throws FileNotFoundException, IOException {
      StringBuilder sb = new StringBuilder();
      try (BufferedReader fileReader = new BufferedReader(new FileReader("ExpectedAddressList.txt"))) {
         String line = fileReader.readLine();
         while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = fileReader.readLine();
         }
      }
      return sb;
   }

   public static void checkLocationsFromGoogleMapAPI (StringBuilder allLocations) throws JSONException {
      LocationEntity locationEntity = new LocationEntity();
      String [] lines = allLocations.toString().split("\\n");
      int counter = 0;
      String addr = "", lat="", lon="";
      for(String str: lines){
         if (counter % 4 == 0) {
            addr = str;
            locationEntity = getLocationFromGoogleMapAPI (addr.replace(' ', '+'));
         } else if (counter % 4 == 1) {
            lat = str;
         } else if (counter % 4 == 2) {
            lon = str;
         } else if (counter % 4 == 3) {
            if (locationEntity.getLatitude().equals(lat) && locationEntity.getLongitude().equals(lon)) {
               System.out.println("CORRECT Latitude and Longitude: \""  + addr + "\"\n");
            } else {
               System.out.println("INCORRECT Latitude and Longitude: \"" + addr + "\"");
               System.out.println("Expected Latitude: " + lat);
               System.out.println("Actual Latitude: " + locationEntity.getLatitude());
               System.out.println("Expected Longitude: " + lon);
               System.out.println("Actual Longitude: " + locationEntity.getLongitude() + "\n");
            }
         }
         ++counter;
      }
   }

   public static LocationEntity getLocationFromGoogleMapAPI (String address) throws JSONException {
      String stringURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=AIzaSyAXGvvQVwmb1vx5Ms0IHomr_3X5gawI3y4";
      JSONObject location = new JSONObject();
      try {
         URL url = new URL(stringURL);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.setRequestProperty("Accept", "application/json");
         if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                  + conn.getResponseCode());
         }
         BufferedReader br = new BufferedReader(new InputStreamReader(
               (conn.getInputStream())));
         String output;
         String str="";
         while ((output = br.readLine()) != null) {
            str += output;
         }
         JSONObject jsonObj = new JSONObject(str);
         JSONArray jsonArray = jsonObj.getJSONArray("results");
         JSONObject result = (JSONObject) jsonArray.get(0);
         JSONObject geometry = (JSONObject) result.get("geometry");
         location = (JSONObject) geometry.get("location");
         conn.disconnect();
      } catch (MalformedURLException e) {
         e.printStackTrace();

      } catch (IOException e) {
         e.printStackTrace();
      }
      return new LocationEntity(location.get("lat").toString(), location.get("lng").toString());
   }
}
