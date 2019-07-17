import entities.LeaveRecord;
import entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class HomeView {
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    private User user = (User) session.getAttribute("user");

//    public List<LeaveRecord> leaveRecordList = new ArrayList<LeaveRecord>();


    public List<LeaveRecord> leaveRecordAll = getList();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LeaveRecord> getLeaveRecordAll() {
        return leaveRecordAll;
    }

    public void setLeaveRecordAll(List<LeaveRecord> leaveRecordAll) {
        this.leaveRecordAll = leaveRecordAll;
    }

    public List<LeaveRecord> getList() {
        Connection con;
        PreparedStatement ps;
        ResultSet resultSet;
        List<LeaveRecord> leaveRecords = new ArrayList<>();
        con = DataConnect.getConnection();
        String sql;
        if (user.getRole().equals("admin")) {
            sql = "select * from leave_record";
        } else {
            sql = "select * from leave_record where leave_record.requestBy = " + user.getId();
        }
        try {
            ps = con.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                LeaveRecord leaveRecord = new LeaveRecord();
                leaveRecord.setId(resultSet.getLong(1));
                leaveRecord.setRequestBy(resultSet.getLong(2));
                leaveRecord.setContent(resultSet.getString(3));
                leaveRecord.setStatus(resultSet.getInt(4));
                leaveRecords.add(leaveRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveRecords;
    }

    public void changeStatus(long id, String status) {
        System.out.println(id);
        Connection con = DataConnect.getConnection();
        Statement stmt = null;
        int stt = 0;
        if (status.equals("accept")) {
            stt = 1;
        } else if(status.equals("reject")) {
            stt = -1;
        }
        System.out.println("UPDATE leave_record set status = " + stt + "where id = " + id);
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("UPDATE leave_record set status = " + stt + "where id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        System.out.println("test");
    }
}
