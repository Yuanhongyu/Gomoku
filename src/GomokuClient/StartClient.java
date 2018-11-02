package GomokuClient;

import java.io.IOException;

public class StartClient {
    public StartClient(Game_OnLine gon) throws IOException {
        ClientManager.getClientManager().communicate();
        ClientManager.getClientManager().setPanel(gon);

    }
}
