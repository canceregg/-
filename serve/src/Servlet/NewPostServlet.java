package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet("/NewPostServlet")

public class NewPostServlet extends HttpServlet {

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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String postId=df.format(new Date());
        List<Post> postList = gson.fromJson(postdata, new TypeToken<List<Post>>(){}.getType());
        String userId=postList.get(0).getUserId().trim();
        String boardId=postList.get(0).getBoardId().trim();
        String postTitle=postList.get(0).getPostTitle().trim();
        String postContent=postList.get(0).getPostContent().trim();
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="insert into post values(?,?,?,?,?,0,0)";//����������
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,postId);
            ps.setString(2,userId);
            ps.setString(3,boardId);
            ps.setString(4,postTitle);
            ps.setString(5,postContent);

            if (ps.executeUpdate()==1) {
				response.getWriter().append("true");
			}
            else
            	response.getWriter().append("false");
            ps.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        
        sql="update board set postcnt=postcnt+1 where boardId=?";//�������ڰ������������
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,boardId);
            if (ps.executeUpdate()==1) {
				response.getWriter().append("true");
			}
            else
            	response.getWriter().append("false");
            ps.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        
        try {
            conn.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }

}
