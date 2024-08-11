package vn.bt.spring.qlsv_be.request;



public class UserRequest {
    private String useName;
    private String password;
    private String  role;

    public UserRequest(String useName, String password, String role) {
        this.useName = useName;
        this.password = password;
        this.role = role;
    }

    public UserRequest() {
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "useName='" + useName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
