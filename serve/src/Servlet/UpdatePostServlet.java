package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import Tool.DBManager;
import Tool.Post;
@WebServlet("/UpdatePostServlet")
public class UpdatePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        String postdata=request.getParameter("postdata");
        Gson gson = new Gson();
        List<Post> postList = gson.fromJson(postdata, new TypeToken<List<Post>>(){}.getType());
        String oldId=postList.get(0).getPostId().trim();
        String postContent=postList.get(0).getPostContent().trim();
        String postTitle=postList.get(0).getPostTitle().trim();
        
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="update post set postContent=?,postTitle=? where postId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postContent);
            ps.setString(2, postTitle);
            ps.setString(3, oldId);
            if (ps.executeUpdate()==1) {
				response.getWriter().append("true");
			}
            else
            	response.getWriter().append("false");
            ps.close();
            conn.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
      
    }
}
