import AopsGui.Aops2DRunner;

public class Main {
    public static void main(String[] args) {
        Aops2DRunner runner = new Aops2DRunner();
        System.out.println(runner.act(0, 0, false, new String[0]));
        runner.trial();
    }
}
