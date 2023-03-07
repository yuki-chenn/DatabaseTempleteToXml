package Frame;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author yuki
 * @version 1.0.0
 * @date 2023-02-19 22:34:36
 * @packageName Frame
 * @className SystemOutTextArea
 * @describe 控制台输出重定向到textArea
 */
public class SystemOutTextArea extends JTextArea {

    private StringBuilder sb = new StringBuilder();

    public SystemOutTextArea(){
        this.setLineWrap(true);
        redirectSystemStreams();
    }

    public void clearText(){
        sb.delete(0, sb.length());
        setText(sb.toString());
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(text.length() > 2){
                    sb.append(text);
                    sb.append("\n********************************************************\n");
                    setText(sb.toString());
                }

            }
        });
    }

    private void redirectSystemStreams() {

        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}
