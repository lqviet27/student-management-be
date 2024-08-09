package vn.bt.spring.qlsv_be.response;

public class JwtAuthenticationResponse {
    private String token;

    // No-argument constructor
    public JwtAuthenticationResponse() {
    }

    // Constructor with arguments
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}

