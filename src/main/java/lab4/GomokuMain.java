package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {
    public static void main(String[] args) {
        GomokuClient client = new GomokuClient(4000);
        GomokuGameState state = new GomokuGameState(client);
        GomokuGUI gui = new GomokuGUI(state, client);
    }
}
