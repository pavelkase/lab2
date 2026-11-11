import java.security.InvalidParameterException;

public class App {
    public static boolean validate(double x, double y, double r) throws InvalidParameterException{
        StringBuilder sb = new StringBuilder();
        boolean correct = true;

        if(!(-5<=x & x<=5)){
            correct = false;
            sb.append("x is invalid ");
        }

        if(!(y>=-4 & y <=4 & y%1==0)){
            correct = false;
            sb.append("y is invalid ");
        }
        if(!(r >=1 & r<=3 & (r*2)%1==0)){
            correct = false;
            sb.append("r is invalid ");
        }
        if(correct){
            return true;
        }
        throw new InvalidParameterException(sb.toString());
    }

    public static boolean hit(double x, double y, double r){
        if (y <= r && y >= 0 & x >= -r & x <= 0) {
            return true;
        }
        if (y >= 0 & x >= 0 & x * x + y * y <= (r / 2) * (r / 2)) {
            return true;
        }
        if (x >= 0 & y <= 0 & y >= x / 2 - r / 2) {
            return true;
        }else {
            return false;
        }
    }
}
