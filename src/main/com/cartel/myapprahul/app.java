package com.cartel.myapprahul;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@RestController
@RequestMapping("/weather")
public class App {

    private final String recipientEmail;
    private final JavaMailSender mailSender;

    public App(JavaMailSender mailSender, @Value("${gandhirahul190@gmail.com}") String recipientEmail) {
        this.mailSender = mailSender;
        this.recipientEmail = recipientEmail;
    }

    @GetMapping("/update")
    public String sendWeatherUpdates() {
        String weatherData = fetchWeatherData("Mumbai") + "<br/>"
                + fetchWeatherData("Bangalore") + "<br/>"
                + fetchWeatherData("Pune");
        sendEmail("Weather Update", weatherData, recipientEmail);
        return "Weather notifications sent!";
    }

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void scheduledWeatherUpdate() {
        sendWeatherUpdates();
    }

    private String fetchWeatherData(String city) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiKey = "0de875566c6a1d90f3ca4d00d9415602"; // Replace with your API key
            String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,IN&appid=%s&units=metric", city, apiKey);
            HttpGet request = new HttpGet(url);
            String response = httpClient.execute(request, httpResponse ->
                    EntityUtils.toString(httpResponse.getEntity())
            );
            // Parse the response and extract the weather information
            // Customize this part based on the response structure of your chosen weather API
            return "Weather in " + city + ": <weather-info>";
        } catch (IOException e) {
            // Handle exception
            return "Error fetching weather data";
        }
    }

    private void sendEmail(String subject, String content, String recipient) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setTo(recipient);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
        }
    }
}
