import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class TestDemo1 {
    @Test
    public void testSend(){
        try {
            ChatgptClient.sendReq("tell me about yourself");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
