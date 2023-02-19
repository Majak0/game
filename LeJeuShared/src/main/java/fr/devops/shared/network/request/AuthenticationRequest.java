package fr.devops.shared.network.request;

public record AuthenticationRequest(String username, String password) implements IRequest {
}
