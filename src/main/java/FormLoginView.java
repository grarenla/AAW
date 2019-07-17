import entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
public class FormLoginView {

    private static final Logger LOGGER = Logger.getLogger(FormLoginView.class.getName());

    private User userForm = new User();

    public void doSubmit() {
        Connection con = null;
        PreparedStatement ps = null;
        FacesContext context = FacesContext.getCurrentInstance();
        User user = validate(this.userForm.getUsername(), this.userForm.getPassword());
        if (user != null) {
            context.getExternalContext().getSessionMap().put("user", user);
            try {
                context.getExternalContext().redirect("home.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //Send an error message on Login Failure
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));

        }
    }

    public static User validate(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataConnect.getConnection();
            assert con != null;
            ps = con.prepareStatement("Select * from users where username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setRole(rs.getString(4));
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return null;
        } finally {
            DataConnect.close(con);
        }
        return null;
    }

    public User getUserForm() {
        return userForm;
    }

    public void setUserForm(User userForm) {
        this.userForm = userForm;
    }

    public void test() {
        System.out.println("123");
    }

}