package vn.bt.spring.qlsv_be.response;

public class ApiResponse<T> {
    private int EC;
    private String EM;
    private T data;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(int EC, String EM, T data) {
        this.EC = EC;
        this.EM = EM;
        this.data = data;
    }

    // Getters and Setters
    public int getEC() {
        return EC;
    }

    public void setEC(int EC) {
        this.EC = EC;
    }

    public String getEM() {
        return EM;
    }

    public void setEM(String EM) {
        this.EM = EM;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
