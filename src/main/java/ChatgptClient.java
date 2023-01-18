import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// to do: 发送POST请求到指定URL上
// 先使用同步请求试试吧
public class ChatgptClient extends HttpServlet {
    static String URL = "";
    static Map<String, String> headers = new HashMap<>();   //手动设置请求头

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ChatgptClient part");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = getMsg(req);
        //接收到来自内地的post请求
        System.out.println(msg);
        //包装msg之后再转发出去
        sendReq(msg);
    }

    /**
     * 将post请求中的消息取出来
     */
    static String getMsg(HttpServletRequest req){
        return req.getParameter("Msg");
    }

    /**
     * 转发请求到chat-gpt的API上
     * */
    static void sendReq(String msg){
        //==============TO DO===============
        //封装请求

        //重新发送请求
    }
}
