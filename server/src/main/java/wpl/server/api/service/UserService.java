package wpl.server.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wpl.server.api.exception.DuplicateException;
import wpl.server.api.exception.NotFoundException;
import wpl.server.api.repository.UserRepository;
import wpl.server.entity.User;
import wpl.server.payload.Message;
import wpl.server.payload.request.JoinRequest;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Message join(JoinRequest joinRequest) {
        userRepository.findByEmailOrName(joinRequest.getEmail(), joinRequest.getName()).ifPresent((user) -> {
            throw new DuplicateException("email or name duplicated.");
        });
        User newUser = User.createUser(joinRequest.getEmail(), joinRequest.getPassword(), joinRequest.getName());
        userRepository.save(newUser);
        return new Message("success to join", null);
    }

    public Message allUsers() {
        return new Message(
                "success to search all user information",
                userRepository.findAll()
                        .stream()
                        .map(User::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    public Message userInfo(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("cannot find user with id: " + id);
        }
        return new Message("success to find user with id: " + id, User.convertToDto(userOptional.get()));
    }
}
