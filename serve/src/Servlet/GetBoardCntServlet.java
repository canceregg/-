package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Tool.DBManager;

/**
 * Servlet implementation class GetBoardCntServlet
 */
@WebServlet("/GetBoardCntServlet")
public class GetBoardCntServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBoardCntServlet() {
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
        
        String boardId=request.getParameter("boardId");
        
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql="select boardClickCnt from board where boardId=?";
		try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, boardId);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) response.getWriter().print(rs.getInt("boardClickCnt"));
            else response.getWriter().append("false");
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
