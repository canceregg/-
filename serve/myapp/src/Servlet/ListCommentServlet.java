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

import com.mysql.jdbc.ResultSet;

import Tool.Board;
import Tool.Comment;
import Tool.DBManager;

/**
 * Servlet implementation class ListComment
 */
@WebServlet("/ListCommentServlet")
public class ListCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        String postId=request.getParameter("postId");
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="select * from comment where postId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, postId);
            ResultSet rs=(ResultSet) ps.executeQuery();
            List<Comment> commentList=new ArrayList<Comment>();
            while(rs.next())
            {
            	Comment c=new Comment();
            	c.setCommentContent(rs.getString("commentContent"));
            	c.setCommentId(rs.getString("commentId"));
            	c.setObserverId(rs.getString("observerId"));
            	c.setPostId(rs.getString("postId"));
            	commentList.add(c);
            }
            String src = MyJson.getInstance().toJson(commentList);
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
