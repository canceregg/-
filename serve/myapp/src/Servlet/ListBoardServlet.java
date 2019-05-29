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
import Tool.DBManager;
import Tool.User;

/**
 * Servlet implementation class ListBoardServlet
 */
@WebServlet("/ListBoardServlet")
public class ListBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBoardServlet() {
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
        
        String sql="select * from board";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs=(ResultSet) ps.executeQuery();
            List<Board> boardList=new ArrayList<Board>();
            while(rs.next())
            {
            	Board b=new Board();
            	b.setBoardId(rs.getString("boardId"));
            	b.setBoardName(rs.getString("boardName"));
            	b.setBoardClickCnt(rs.getInt("boardClickCnt"));
            	b.setBoardPostCnt(rs.getInt("boardPostCnt"));
            	b.setBoardMessage(rs.getString("boardMessage"));
            	boardList.add(b);
            }
            String src = MyJson.getInstance().toJson(boardList);
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
