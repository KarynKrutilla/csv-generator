package src.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CsvGenerator {

    public static void main(String[] args) throws IOException, JSONException {

        // Get json from url
        String posts = getStringFromUrl(new URL("http://jsonplaceholder.typicode.com/posts"));
        String users = getStringFromUrl(new URL("http://jsonplaceholder.typicode.com/users"));

        // Map json to list of objects
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList = mapper.readValue(users, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        List<Post> postList = mapper.readValue(posts, mapper.getTypeFactory().constructCollectionType(List.class, Post.class));

        // Create PrintWriter + StringBuilder to help write to file
        PrintWriter writer = new PrintWriter(new File("generatedCsv.csv"));
        StringBuilder stringBuilder = new StringBuilder();

        // Add header row
        stringBuilder.append("Post ID");
        stringBuilder.append(",");
        stringBuilder.append("Post Title");
        stringBuilder.append(",");
        stringBuilder.append("User ID");
        stringBuilder.append(",");
        stringBuilder.append("User Username");
        stringBuilder.append(",");
        stringBuilder.append("User Email");
        stringBuilder.append("\n");

        // Loop over each Post object
        for(Post post : postList) {
            // Find associated User for the given post
            User user = null;
            for(User currentUser : userList) {
                if (currentUser.getId().equals(post.getUserId())) {
                    user = currentUser;
                }
            }
            // Check that the user exists, just in case
            if(user == null) {
                throw new RuntimeException("No user matches this post! Post ID " + post.getId());
            }
            // Append provided Post and User to StringBuilder
            stringBuilder.append(post.getId());
            stringBuilder.append(",");
            stringBuilder.append(post.getTitle());
            stringBuilder.append(",");
            stringBuilder.append(user.getId());
            stringBuilder.append(",");
            stringBuilder.append(user.getUsername());
            stringBuilder.append(",");
            stringBuilder.append(user.getEmail());
            stringBuilder.append("\n");
        }
        // Write StringBuilder
        writer.write(stringBuilder.toString());
        writer.close();
    }


    private static String getStringFromUrl(URL url) throws IOException {
        // Connect
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        conn.setRequestProperty("Accept", "application/json");

        // Build string to return
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String currentLine;
        while((currentLine=bufferedReader.readLine()) != null) {
            stringBuilder.append(currentLine);
        }
        return stringBuilder.toString();
    }

    // Classes needed for mapping:

    static class User {
        private String id;
        private String name;
        private String username;
        private String email;
        private Address address;
        private String phone;
        private String website;
        private Company company;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        static class Address {
            private String street;
            private String suite;
            private String city;
            private String zipcode;
            private Geo geo;

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getSuite() {
                return suite;
            }

            public void setSuite(String suite) {
                this.suite = suite;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public Geo getGeo() {
                return geo;
            }

            public void setGeo(Geo geo) {
                this.geo = geo;
            }
        }

        static class Geo {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }
        }

        static class Company {
            private String name;
            private String catchPhrase;
            private String bs;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCatchPhrase() {
                return catchPhrase;
            }

            public void setCatchPhrase(String catchPhrase) {
                this.catchPhrase = catchPhrase;
            }

            public String getBs() {
                return bs;
            }

            public void setBs(String bs) {
                this.bs = bs;
            }
        }
    }

    static class Post {
        private String userId;
        private String id;
        private String title;
        private String body;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}

