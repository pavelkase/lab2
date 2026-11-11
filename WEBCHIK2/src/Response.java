import java.nio.charset.StandardCharsets;

public class Response {

    public static void responseSend(String status, String content){
        String str = "HTTP/1.1 "+status+"\r\n"+
                "Content-Type: application/json; charset=UTF-8\r\n"+
                "Content-length: "+content.getBytes(StandardCharsets.UTF_8).length+"\r\n\r\n"+content;
        System.out.print(str);
    }

    public static String responseBuild(long scriptTime, double x, double y, double r, boolean hit){
        return "{\"scriptTime\" : "+scriptTime+
                ", \"x\" : "+x+
                ", \"y\" : "+y+
                ", \"r\" : "+r+
                ", \"hit\" : "+hit+"}";
    }
}
