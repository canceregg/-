package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import Tool.Post;
import Tool.User;
@WebServlet("/DeletePostServlet")
public class DeletePostServlet extends HttpServlet {
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
        
        String postId=postList.get(0).getPostId();
        String boardId=postList.get(0).getBoardId();
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        String sql="delete from post where postId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postId);

            if (ps.executeUpdate()==1) {
				response.getWriter().append("true");
			}
            else
            	response.getWriter().append("false");
            ps.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        sql="update board set boardPostCnt=boardPostCnt-1 where boardId=?";//�������ڰ������������
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,boardId);
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
