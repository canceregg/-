package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * Servlet implementation class LoginUserServlet
 */
@WebServlet("/LoginUserServlet")
public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUserServlet() {
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
        String userPassword=userList.get(0).getUserPassword().trim();
      
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="select * from user";
        boolean flag=false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs=(ResultSet) ps.executeQuery();
            while(rs.next())
            {
            	
            	if(rs.getString("Email").equals(Email)&&rs.getString("userPassword").equals(userPassword))
            	{
            		
            		flag=true;
            	}
            }
            if(flag==true)
            	response.getWriter().append("true");
            else
            	response.getWriter().append("false");
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
