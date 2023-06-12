package com.cartel.myapprahul;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Unit test for simple App.
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

class AppTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendWeatherUpdates_ShouldSendEmail() {
        String recipientEmail = "gandhirahul190@gmail.com";
        String expectedSubject = "Weather Update";
        String expectedContent = "Weather in Mumbai: <weather-info><br/>" +
                "Weather in Bangalore: <weather-info><br/>" +
                "Weather in Pune: <weather-info>";

        weatherController.sendWeatherUpdates();

        verify(mailSender).send(anyString());
        // Assert other expectations as necessary
    }
}
