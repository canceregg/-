package com.example.skyreach.connect.work;

import com.example.skyreach.connect.tool.Board;
import com.example.skyreach.connect.tool.MyHttp;
import com.example.skyreach.connect.tool.MyJson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class BoardIF
{
    List<Board> boardList = null;
    String result = null;
    //获取本类对象
    public static BoardIF getInstance()
    {
        return single;
    }

    //获取所有板块信息
    public List<Board> getAllInf()
    {
        final String url = "http://111.230.183.219/myapp/ListBoardServlet";
        result = MyHttp.getInstance().postSync(url," "," ");
        List<Board> boardList = (List<Board>) MyJson.getInstance().fromJson(result, new TypeToken<List<Board>>(){}.getType());
        return boardList;
    }

    //根据板块名称获取板块ID
    public String getBoardIdByName(String boardName)
    {
        List<Board> boardList = this.getAllInf();
        String temp = null;
        for(int i = 0; i < boardList.size(); i++)
        {
            if (boardList.get(i).getBoardName().equals(boardName))
            {
                temp = boardList.get(i).getBoardId();
                break;
            }
        }
        return temp;
    }

    //点击量增加
    //参数：BoardId->板块ID   add->点击的增加数，处理方式原来点击量+=add
    public Boolean increaseClickCnt(String boardId)
    {
        final String url = "http://111.230.183.219/myapp/BoardClickPlusServlet";
        result = MyHttp.getInstance().postSync(url,"boardId", boardId);
        System.out.println("输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //根据帖子获取点击数
    //参数：帖子ID
    public int getClickCnt(String boardId)
    {
        final String url = "http://111.230.183.219/myapp/GetBoardCntServlet";
        result = MyHttp.getInstance().postSync(url,"boardId", boardId);
        System.out.println("输出-------:" + result);
        try
        {
            return Integer.parseInt(result);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    //获取最新的某板块的帖子数
    //参数：板块的ID
    //返回值：板块的帖子总数
    public int getPostCnt(String boardId)
    {
        final String url = "http://111.230.183.219/myapp/GetPostCntServlet";
        result = MyHttp.getInstance().postSync(url,"boardId", boardId);
        System.out.println("输出-------:" + result);
        try
        {
            return Integer.parseInt(result);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    //新增一个板块
    //参数：一个板块对象,板块的id不需要赋值，系统自动生成
    //返回值：成功返回true，失败返回false
    public Boolean addBoard(Board board)
    {
        final String url = "http://111.230.183.219/myapp/NewBoardServlet";
        List<Board> boardList = new ArrayList<Board>();
        boardList.add(board);
        final String src = MyJson.getInstance().toJson(boardList);
        result = MyHttp.getInstance().postSync(url,"boarddata",src);
        System.out.println("输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;
    }

    //删除一个板块
    //返回值：成功返回true，失败返回false
    public Boolean delBoard(String boardId)
    {
        final String url = "http://111.230.183.219/myapp/DeleteBoardServlet";
        List<Board> boardList = new ArrayList<Board>();
        Board b = new Board();
        b.setBoardId(boardId);
        boardList.add(b);
        final String src = MyJson.getInstance().toJson(boardList);
        result = MyHttp.getInstance().postSync(url,"boarddata",src);
        System.out.println("BOardJF输出-------:" + result);
        if(result.trim().equals("true"))
            return true;
        return false;

    }



    //单例模式
    private BoardIF() { }
    private static BoardIF single = new BoardIF();
}
