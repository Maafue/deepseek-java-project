package by.morozmaksim.deepseektaskservice.client;

import by.morozmaksim.deepseektaskservice.client.dto.UserDto;


public interface UserClient {
    UserDto getUserByUserId(Long userId);
}
