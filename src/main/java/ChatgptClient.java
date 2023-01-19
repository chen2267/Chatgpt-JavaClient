import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

// to do: 发送POST请求到指定URL上

// 先使用同步请求试试吧
public class ChatgptClient extends HttpServlet {

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
//        sendReq(msg);
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
    static void sendReq(String msg) throws URISyntaxException {
        //封装请求
        String url = "https://api.openai.com/v1/completions";
        String[] stop = new String[]{
                " Human:", " AI:"
        };
        JSONObject params = new JSONObject();
        params.put("model", "text-davinci-003");
        params.put("prompt", msg);
        params.put("max_tokens", "2048");
        params.put("temperature", "0");
        params.put("top_p", "1");
        params.put("frequency_penalty", "0.6");
        params.put("stop", stop);
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.addHeader("Authorization", "bearer sk-key");
        //重新发送请求
        try {
            HttpResponse response = client.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        }catch (Exception e){
            System.out.println("error");
        }


    }
}
