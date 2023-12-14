import java.io.IOException;
import java.io.InputStream;

public class DynamicInputStream extends InputStream {
    private StringBuilder content;
    private int position;

    public DynamicInputStream() {
        content = new StringBuilder();
        position = 0;
    }

    public void addText(String text) {
        content.append(text + "\n");
        System.out.println(content + " " + position);
    }

    @Override
    public int read() throws IOException {
        if (position < content.length()) {
            return content.charAt(position++);
        } else {
            return -1; // Signal end of stream
        }
    }

    public void print() {
        System.out.println(content + " " + position);
    }
}