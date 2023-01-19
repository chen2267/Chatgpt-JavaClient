import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Map;

// to do: 发送POST请求到指定URL上

// 先使用同步请求试试吧
public class ChatgptClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("Msg");
        //接收到来自内地的post请求
        System.out.println(msg);
        //包装msg之后再转发出去
        String res = "error";
        try {
            res = sendReq(msg);
            resp.setStatus(200);
            PrintWriter writer = resp.getWriter();
            writer.println(res);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String msg = getMsg(req);
//        //接收到来自内地的post请求
//        //包装msg之后再转发出去
//        String res = "error";
//        try {
//            res = sendReq(msg);
//            resp.setStatus(200);
//            PrintWriter writer = resp.getWriter();
//            writer.println(res);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 将post请求中的消息取出来  TO DO
     */
    static String getMsg(HttpServletRequest req) throws IOException {

        StringBuilder tmp = new StringBuilder();
        String line;
        BufferedReader reader;
        try {
            reader = req.getReader();
            while (null != (line = reader.readLine())) {
                tmp.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        // to do
        return "";

    }

    /**
     * 转发请求到chat-gpt的API上
     * 返回chatgpt的消息
     * */
    static String sendReq(String msg) throws URISyntaxException {
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
        httpPost.addHeader("Authorization", "Bearer sk-key");
        //重新发送请求
        HttpResponse res = null;
        String result = "error";
        try {
            HttpResponse response = client.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject sJson = JSONObject.parseObject(s);
            String tmp = sJson.getString("choices");
            String resultText = tmp.substring(1, tmp.length() - 1);
            result = JSONObject.parseObject(resultText).getString("text");
            System.out.println(result);
        }catch (Exception e){
            System.out.println("error");
        }
        return result;
    }
}
