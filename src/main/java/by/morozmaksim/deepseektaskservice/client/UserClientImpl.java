package by.morozmaksim.deepseektaskservice.client;

import by.morozmaksim.deepseektaskservice.client.dto.UserDto;
import by.morozmaksim.deepseektaskservice.domain.configuration.RestTemplateConfig;
import by.morozmaksim.deepseektaskservice.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final RestTemplateConfig restTemplateConfig;

    @Override
    public UserDto getUserByUserId(Long userId) {
        RestTemplate restTemplate = restTemplateConfig.restTemplate();
        UserDto userDto;
        String url = "http://localhost:8082/api/v1/users/" + userId;
        try {
            userDto = restTemplate.getForObject("http://localhost:8082/api/v1/users/" + userId, UserDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        return userDto;
    }
}
