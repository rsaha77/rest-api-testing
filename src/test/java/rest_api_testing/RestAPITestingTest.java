package rest_api_testing;

import org.junit.Test;
import google_map_api.main.RestAPITesting;
import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class RestAPITestingTest {
   @BeforeClass
   public static void setUpClass() {
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void SetUp() {
   }

   @After
   public void TearDown() {
   }

   @Test
   public void AddressFromFileShouldMatchTheExpectedAddress () throws FileNotFoundException, IOException {
      String addr = "1600 Amphitheatre Parkway, Mountain View, CA";
      String lat = "37.4216548";
      String lon = "-122.0856374";
      StringBuilder allLocations = RestAPITesting.getAllLocations();
      // The first address in the file
      int counter = 0;
      String [] lines = allLocations.toString().split("\\n");
      for(String str: lines){
         if (counter == 0) {
            assertEquals (addr,str);
         } else if (counter == 1) {
            assertEquals (lat,str);
         } else if (counter == 2) {
            assertEquals (lon,str);
            break;
         }
         ++counter;
      }
   }
}
