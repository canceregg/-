package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.ResultSet;

import Tool.DBManager;
import Tool.Post;
import Tool.User;

/**
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        String userdata=request.getParameter("userdata");
        Gson gson = new Gson();
        List<User> userList = gson.fromJson(userdata, new TypeToken<List<User>>(){}.getType());
        String Email=userList.get(0).getEmail().trim();
        String oldPassword=userList.get(0).getUserPassword().trim();
        String newPassword=userList.get(0).getUserName().trim();
        
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql1="select * from user";
        String sql2="update user set userPassword=? where Email=?";
        try {
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ResultSet rs=(ResultSet) ps1.executeQuery();
            boolean flag=false;
            while (rs.next()) {
            	if(rs.getString("Email").equals(Email)&&rs.getString("userPassword").equals(oldPassword))
            	{
            		ps2.setString(1, newPassword);
            		ps2.setString(2, Email);
            		flag=true;
            	}
			}
            if (flag) {
				if (ps2.executeUpdate() == 1) {
					response.getWriter().append("true");
				} else
					response.getWriter().append("false");
			}
            else
            	response.getWriter().append("false");
			ps1.close();
            ps2.close();
            conn.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
      
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
