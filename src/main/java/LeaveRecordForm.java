import entities.LeaveRecord;
import entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean
public class LeaveRecordForm {
    private LeaveRecord leaveRecord = new LeaveRecord();
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    private User user = (User) session.getAttribute("user");

    public void doSubmit() {
        Connection con = DataConnect.getConnection();
        try {
            assert con != null;
            con.createStatement();
            assert false;
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO leave_record (requestBy, content, status) VALUES (?, ?, ?)");
            preparedStatement.setLong(1, this.user.getId());
            preparedStatement.setString(2, this.leaveRecord.getContent());
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LeaveRecord getLeaveRecord() {
        return leaveRecord;
    }

    public void setLeaveRecord(LeaveRecord leaveRecord) {
        this.leaveRecord = leaveRecord;
    }
}
