package by.morozmaksim.deepseektaskservice.client;

import by.morozmaksim.deepseektaskservice.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String url;

    @Override
    public void checkUserExist(Long userId) {
        try {
            restTemplate.getForObject(url + "/" + userId, Void.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}
