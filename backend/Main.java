import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {

    public static void main(String[] args) throws IOException {

        Connection connection = DatabaseConnection.getConnection();

    if (connection != null) {
    System.out.println("MySQL connection successful!");
    
}   else {
    System.out.println("MySQL connection failed!");
}

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        server.createContext("/student", exchange -> {

                System.out.println("STUDENT REQUEST RECEIVED");

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Student data received:");
        System.out.println(requestData);

        try {

            String id = requestData.split("\"id\":\"")[1].split("\"")[0];
            String name = requestData.split("\"name\":\"")[1].split("\"")[0];
            String email = requestData.split("\"email\":\"")[1].split("\"")[0];
            String phone = requestData.split("\"phone\":\"")[1].split("\"")[0];
            String course = requestData.split("\"course\":\"")[1].split("\"")[0];
            String address = requestData.split("\"address\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO student" +
                         "(student_id, name, email, phone, course, address) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, course);
            ps.setString(6, address);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Student saved to MySQL!");

            String response = "Student saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save student!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }

    }

    exchange.close();

});

    server.createContext("/teacher", exchange -> {

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Teacher data received:");
        System.out.println(requestData);

        try {

            String name = requestData.split("\"name\":\"")[1].split("\"")[0];
            String email = requestData.split("\"email\":\"")[1].split("\"")[0];
            String phone = requestData.split("\"phone\":\"")[1].split("\"")[0];
            String subject = requestData.split("\"subject\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO teachers" +
                         "(name, email, phone, subject) " +
                         "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, subject);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Teacher saved to MySQL!");

            String response = "Teacher saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save teacher!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }
    }

    exchange.close();

});
  
  server.createContext("/attendance", exchange -> {

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Attendance data received:");
        System.out.println(requestData);

        try {

            String studentId =
                    requestData.split("\"student_id\":\"")[1].split("\"")[0];

            String date =
                    requestData.split("\"date\":\"")[1].split("\"")[0];

            String status =
                    requestData.split("\"status\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO attendance " +
                         "(student_id, date, status) " +
                         "VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, date);
            ps.setString(3, status);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Attendance saved to MySQL!");

            String response = "Attendance saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save attendance!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }
    }

    exchange.close();

});

 server.createContext("/examination", exchange -> {

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Examination data received:");
        System.out.println(requestData);

        try {

            String studentId =
                    requestData.split("\"student_id\":\"")[1].split("\"")[0];

            String subject =
                    requestData.split("\"subject\":\"")[1].split("\"")[0];

            String marks =
                    requestData.split("\"marks\":\"")[1].split("\"")[0];

            String examType =
                    requestData.split("\"exam_type\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO examinations" +
                         "(student_id, subject, marks, exam_type) " +
                         "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, subject);
            ps.setInt(3, Integer.parseInt(marks));
            ps.setString(4, examType);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Examination result saved to MySQL!");

            String response = "Examination result saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save examination result!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }
    }

    exchange.close();

});
   
   server.createContext("/fees", exchange -> {

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Fee data received:");
        System.out.println(requestData);

        try {

            String studentId =
                    requestData.split("\"student_id\":\"")[1].split("\"")[0];

            String amount =
                    requestData.split("\"amount\":\"")[1].split("\"")[0];

            String paymentStatus =
                    requestData.split("\"payment_status\":\"")[1].split("\"")[0];

            String paymentDate =
                    requestData.split("\"payment_date\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO fees" +
                         "(student_id, amount, payment_status, payment_date) " +
                         "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(studentId));
            ps.setDouble(2, Double.parseDouble(amount));
            ps.setString(3, paymentStatus);
            ps.setString(4, paymentDate);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Fee saved to MySQL!");

            String response = "Fee saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save fee!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }
    }

    exchange.close();

});

   server.createContext("/academic", exchange -> {

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        exchange.sendResponseHeaders(204, -1);
        exchange.close();
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String requestData = new String(requestBytes, StandardCharsets.UTF_8);

        System.out.println("Academic record received:");
        System.out.println(requestData);

        try {

            String studentId =
                    requestData.split("\"student_id\":\"")[1].split("\"")[0];

            String semester =
                    requestData.split("\"semester\":\"")[1].split("\"")[0];

            String percentage =
                    requestData.split("\"percentage\":\"")[1].split("\"")[0];

            String grade =
                    requestData.split("\"grade\":\"")[1].split("\"")[0];

            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO academic_records" +
                         "(student_id, semester, percentage, grade) " +
                         "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(studentId));
            ps.setString(2, semester);
            ps.setDouble(3, Double.parseDouble(percentage));
            ps.setString(4, grade);

            ps.executeUpdate();

            ps.close();
            conn.close();

            System.out.println("Academic record saved to MySQL!");

            String response = "Academic record saved successfully!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );

        } catch (Exception e) {

            e.printStackTrace();

            String response = "Failed to save academic record!";

            exchange.getResponseHeaders().add(
                    "Access-Control-Allow-Origin", "*"
            );

            exchange.sendResponseHeaders(
                    500,
                    response.getBytes().length
            );

            exchange.getResponseBody().write(
                    response.getBytes()
            );
        }
    }

    exchange.close();

});
  
       server.start();

        System.out.println("Java Server started!");
        System.out.println("http://localhost:8080/test");
    }
}