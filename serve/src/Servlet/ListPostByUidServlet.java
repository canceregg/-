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

import Tool.DBManager;
import Tool.Post;

/**
 * Servlet implementation class ListPostByUidServlet
 */
@WebServlet("/ListPostByUidServlet")
public class ListPostByUidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPostByUidServlet() {
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
        
        String userId=request.getParameter("userId");
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="select * from post where userId=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs=(ResultSet) ps.executeQuery();
            List<Post> postList=new ArrayList<Post>();
            while(rs.next())
            {
            	
            	
					Post p = new Post();
					p.setBoardId(rs.getString("boardId"));
					p.setPostClickCnt(rs.getInt("postClickCnt"));
					p.setPostContent(rs.getString("postContent"));
					p.setPostId(rs.getString("postId"));
					p.setPostLikeCnt(rs.getInt("postLikeCnt"));
					p.setPostTitle(rs.getString("postTitle"));
					p.setUserId(rs.getString("userId"));
					postList.add(p);
			
            }
            String src = MyJson.getInstance().toJson(postList);
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
