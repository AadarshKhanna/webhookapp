webhookapp

A Spring Boot project that:
- Sends a payload to a webhook generation API
- Receives a webhook URL and access token
- Submits a final SQL query to the webhook using the token

Author:
Aadarsh Khanna
Reg No: 2210994751
Email: aadarsh4751.be22@chitkara.edu.in

Technologies Used:
- Java 17+
- Spring Boot 3.2.4
- Maven
- RestTemplate
- Jackson for JSON parsing

How to Run:

Using Maven:
> mvn clean install
> mvn spring-boot:run

Or run the .jar directly:
> java -jar target/webhookapp-0.0.1-SNAPSHOT.jar

API Flow:

1. Generate Webhook
   POST https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA
   Payload:
   {
     "name": "Aadarsh Khanna",
     "regNo": "2210994751",
     "email": "aadarsh4751.be22@chitkara.edu.in"
   }

2. Submit Final SQL Query
   - Use the accessToken in the Authorization header as: Bearer <token>
   - Send the finalQuery string to the webhook URL

Final Query:
SELECT p.amount AS SALARY,
       CONCAT(e.first_name, ' ', e.last_name) AS NAME,
       TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS AGE,
       d.department_name AS DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.emp_id = e.emp_id
JOIN DEPARTMENT d ON e.department = d.department_id
WHERE DAY(p.payment_time) != 1
ORDER BY p.amount DESC
LIMIT 1;

Project Structure:
webhookapp/
 ├── src/
 │   └── main/
 │       └── java/
 │           └── com/bajaj/webhookapp/
 │               ├── WebhookApp.java
 │               └── WebhookService.java
 ├── pom.xml
 └── README.txt

Submission Checklist:
GitHub Repo:
https://github.com/AadarshKhanna/webhookapp

JAR File (Raw Downloadable):
https://raw.githubusercontent.com/AadarshKhanna/webhookapp/main/target/webhookapp-0.0.1-SNAPSHOT.jar
