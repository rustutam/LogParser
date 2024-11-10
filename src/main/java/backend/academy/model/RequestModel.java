package backend.academy.model;

public record RequestModel(
    String requestMethod,
    String requestResource,
    String protocolVersion
) {
}
