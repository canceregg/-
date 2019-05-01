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

import Tool.Comment;
import Tool.DBManager;

@WebServlet("/NewCommentServlet")
public class NewCommentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        String commentdata=request.getParameter("commentdata");
        Gson gson = new Gson();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String commentId=df.format(new Date());
        List<Comment> commentList = gson.fromJson(commentdata, new TypeToken<List<Comment>>(){}.getType());
        String postId=commentList.get(0).getPostId().trim();
        String observerId=commentList.get(0).getObserverId().trim();
        String commentContent=commentList.get(0).getCommentContent().trim();
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="insert into comment values(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,commentId);
            ps.setString(2,postId);
            ps.setString(3,observerId);
            ps.setString(4,commentContent);

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
