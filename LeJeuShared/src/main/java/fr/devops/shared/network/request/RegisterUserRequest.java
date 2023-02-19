package fr.devops.shared.network.request;

public record RegisterUserRequest(String username, String password) implements IRequest {
}
