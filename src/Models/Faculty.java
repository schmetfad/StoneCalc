package Models;



public class Faculty {
    static double g = 7;
    static double[] C = {0.99999999999980993, 676.5203681218851, -1259.1392167224028,
            771.32342877765313, -176.61502916214059, 12.507343278686905,
            -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};

    public static double gamma(double z) {
        double x;
        double t;

        if(z < 0.5) {
            return Math.PI /(Math.sin(Math.PI*z)*gamma(1-z));
        }
        else {
            z-=1;
            x = C[0];
            for(int i=1;i<g+2;i++) {
                x+=C[i]/(z+i);
            }
            t = z+g+0.5;
            return Math.sqrt(2*Math.PI)* Math.pow(t, (z+0.5)) * Math.exp(-t)*x;
        }

    }

    public static double faculty(double z) {
        double res = 1;
        if(z == 0) {
            return 1;
        }
        for(int i=1;i<=z;i++) {
            res*=i;
        }

        return res;
    }

}
