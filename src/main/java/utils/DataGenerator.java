package utils;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.github.javafaker.Faker;
import java.io.File;




public class DataGenerator {
    public static void updateJsonFile() {
        File file = new File("src/main/resources/testdata.json");
        file.delete();
        // Read the existing JSON data from the file
        Gson gson = new Gson();

        // Create a new empty list of persons
        List<Person> persons = new ArrayList<>();
        Faker faker = new Faker();

        // Modify the data structure with new values
        Person newPerson = new Person();
        newPerson.setFirstName(faker.name().firstName());
        newPerson.setLastName(faker.name().lastName());
        newPerson.setPhoneNumber(faker.phoneNumber().cellPhone());
        newPerson.setEmail(faker.internet().emailAddress());
        newPerson.setAddress(faker.address().streetAddress());
        newPerson.setCity(faker.address().city());
        newPerson.setState(faker.address().state());
        newPerson.setPostalCode(faker.address().zipCode());
        persons.add(newPerson);

        // Write the updated data structure back to the file
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Person {
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

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }
}