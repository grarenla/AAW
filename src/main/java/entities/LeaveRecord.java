package entities;

public class LeaveRecord {
    private long id;
    private long requestBy;
    private String content;
    private int status;

    public LeaveRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(long requestBy) {
        this.requestBy = requestBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
