package by.morozmaksim.deepseektaskservice.client;

import by.morozmaksim.deepseektaskservice.client.dto.UserDto;
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
    public UserDto getUserByUserId(Long userId) {
        UserDto userDto;
        try {
            userDto = restTemplate.getForObject(url + "/" + userId, UserDto.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        return userDto;
    }
}
