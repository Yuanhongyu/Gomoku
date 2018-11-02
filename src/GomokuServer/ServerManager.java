package GomokuServer;

import java.io.IOException;
import java.util.Vector;

public class ServerManager {

    private ServerManager() {
    }

    private static final ServerManager cm = new ServerManager();

    public static ServerManager getChatManager() {
        return cm;
    }

    Vector<GomokuSocket> vector = new Vector<>();

    public void add(GomokuSocket cs) throws IOException {
        vector.add(cs);
        if (vector.size() % 2 == 0) {
            cs.gaming = true;
            cs.out("start black" +  "\n");
            getOp(cs).out("start white" + "\n");
            getOp(cs).gaming=true;
        }

//        if (vector.size() >= 2 && vector.size() % 2 == 0) {
//            for (int i = 0; i < vector.size(); i++) {
//                if (!vector.get(i).gaming) {
//                    vector.get(i).gaming = true;
//
//                    vector.get(i).out("start " + String.valueOf(i % 2) + "\n");
//                }
//            }
//        }

    }

    public GomokuSocket getOp(GomokuSocket cs) {
        for (int i = 0; i < vector.size(); i++) {
            if (cs.equals(vector.get(i))) {
                if (i % 2 == 0) {
                    return vector.get(i + 1);
                } else {
                    return vector.get(i - 1);
                }
            }
        }
        return cs;
    }


    public void publish(GomokuSocket cs, String out) throws IOException {
        System.out.println(out);

        int n = vector.size();
        if (n >= 2) {
            if (n % 2 == 1) n--;
            for (int i = 0; i < n; i++) {
                if (cs.equals(vector.get(i))) {
                    if (i % 2 == 0) {
                        vector.get(i + 1).out(out + "\n");
                    } else {
                        vector.get(i - 1).out(out + "\n");
                    }
                }
            }
        }
    }
}
