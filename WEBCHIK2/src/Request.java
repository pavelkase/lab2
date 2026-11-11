import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

public class Request {
    FCGIInterface fcgi = new FCGIInterface();

    public void handleReq() {
        while (fcgi.FCGIaccept() >= 0) {
            try {
                long scriptTime = System.nanoTime();
                String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
                if (!"POST".equals(method)) {
                    Response.responseSend("405 Method Not Allowed", "{\"error\":\"only POST method is allowed\"}");
                } else {
                    String body = readReq();
                    double[] args = parseStr(body);
                    double x = args[0];
                    double y = args[1];
                    double r = args[2];
                    if(App.validate(x, y, r)){
                        Response.responseSend("200 OK", Response.responseBuild(System.nanoTime()-scriptTime, x, y, r, App.hit(x,y,r)));
                    }

                }


            } catch (IOException e) {
                Response.responseSend("500 Internal Server Error", "{\"error\":\"unexpected server mistake\"}");
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                Response.responseSend("400 Bad Request","{\"error\":\"invalid parameters\"}");
            } catch (InvalidParameterException e){
                Response.responseSend("400 Bad Request", "{\"error\":\""+e.getMessage()+"\"}");
            }
        }
    }



    public String readReq() throws IOException{
        FCGIInterface.request.inStream.fill();
        String contentLenStr = FCGIInterface.request.params.getProperty("CONTENT_LENGTH");
        int contentLen = (contentLenStr != null) ? Integer.parseInt(contentLenStr) : 0;
        byte[] bytes = new byte[contentLen];
        FCGIInterface.request.inStream.read(bytes, 0, contentLen);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public double[] parseStr(String body) throws NumberFormatException {
        String[] strArgs = body.split("&");
        double[] doubleArgs = new double[3];
        for(int i = 0; i < 3; i++) {
            doubleArgs[i] = Double.parseDouble(strArgs[i].split("=")[1]);
        }
        return doubleArgs;
    }
}
