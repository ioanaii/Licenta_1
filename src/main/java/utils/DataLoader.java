package utils;

import java.io.IOException;
import java.util.Properties;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileInputStream;
    public class DataLoader {
        public  static TestData[] loadFromJsonFile(String filePath) {
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, TestData[].class);
            } catch (IOException e) {
                e.printStackTrace();
                return new TestData[0];
            }
        }

        public static Object[][] loadFromPropertiesFile(String filePath, String user1, String pass1, String user2, String pass2) {
            Properties prop = new Properties();
            try (FileInputStream fileIn = new FileInputStream(filePath)) {
                prop.load(fileIn);
            } catch (IOException e) {
                e.printStackTrace();
            }

            user1 = prop.getProperty("username");
            pass1 = prop.getProperty("password");
            user2 = prop.getProperty("username2");
            pass2 = prop.getProperty("password2");

            return new Object[][]{
                    {user1, pass1, user2, pass2}
            };
        }
    public class TestData {
            private String firstName;
            private String lastName;
            private String phoneNumber;
            private String email;
            private String address;
            private String city;
            private String state;
            private String postalCode;

            public String getFirstName() {
                return firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public String getEmail() {
                return email;
            }

            public String getAddress() {
                return address;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public String getPostalCode() {
                return postalCode;
            }
        }
}
