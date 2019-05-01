package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Tool.Board;
import Tool.Comment;
import Tool.DBManager;

/**
 * Servlet implementation class NewBoardServlet
 */
@WebServlet("/NewBoardServlet")
public class NewBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewBoardServlet() {
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
        
        String boarddata=request.getParameter("boarddata");
        Gson gson = new Gson();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String boardId=df.format(new Date());
        List<Board> boardList = gson.fromJson(boarddata, new TypeToken<List<Board>>(){}.getType());
        String boardName=boardList.get(0).getBoardName().trim();
        String boardMessage=boardList.get(0).getBoardMessage().trim();
        Connection conn = null;
		try {
			conn = DBManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String sql="insert into board values(?,?,0,0,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,boardId);
            ps.setString(2,boardName);
            ps.setString(3,boardMessage);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
