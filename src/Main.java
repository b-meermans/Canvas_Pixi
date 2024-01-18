import AopsGui.Aops2DRunner;

public class Main {
    /**
     * For quick tests outside the local server.
     * @param args
     */
    public static void main(String[] args) {
        Aops2DRunner runner = new Aops2DRunner();
        System.out.println(runner.act(1, 0, 0, false, new String[0]));
    }
}
