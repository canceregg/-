package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Servlet implementation class ListUserServlet
 */
@WebServlet("/ListUserServlet")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListUserServlet() {
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

        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="select * from user";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs=(ResultSet) ps.executeQuery();
            List<User> userList=new ArrayList<User>();
            while(rs.next())
            {
            	User u=new User();
            	u.setEmail(rs.getString("Email"));
            	u.setUserId(rs.getString("userId"));
            	u.setUserName(rs.getString("userName"));
            	u.setUserPassword(rs.getString("userPassword"));
            	u.setUserType(Integer.parseInt(rs.getString("userType")));
            	userList.add(u);
            }
            String src = MyJson.getInstance().toJson(userList);
            response.getWriter().println(src);
            rs.close();
            ps.close();
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
