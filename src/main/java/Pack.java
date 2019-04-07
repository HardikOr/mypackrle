import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Pack {
    private static void putSymbols(boolean repMode, Deque<Integer> deque, OutputStreamWriter out) throws IOException {
        // writing method for Pack.zip
        if (repMode) {
            int len = deque.size();

            for (int i = 0; i < len / 9; i++) {
                out.write("9");
                out.write(deque.pollFirst());
            }

            if (len % 9 != 0) {
                out.write(String.valueOf(len % 9));
                out.write(deque.pollFirst());
            }
        } else {
            int len = deque.size();

            for (int i = 0; i < len / 9; i++) {
                out.write("-9");
                for (int j = 0; j < 9; j++)
                    out.write(deque.pollFirst());
            }

            if (len % 9 != 0) {
                out.write("-" + String.valueOf(len % 9));
                for (int i = 0; i < len % 9; i++) {
                    out.write(deque.pollFirst());
                }
            }
        }

        deque.clear();
    }

    public static void zip(String inputName, String outputName) {
        InputStreamReader in;
        OutputStreamWriter out;

        try {
            in = new InputStreamReader(new FileInputStream(inputName));
            out = new OutputStreamWriter(new FileOutputStream(outputName));

            int c;
            Deque<Integer> q = new ArrayDeque<Integer>();

            while ((c = in.read()) != -1) {
                if (!q.isEmpty() && q.size() > 1) {
                    int tmp = q.pollLast();

                    if (c == tmp) {
                        if (tmp != q.peekLast())
                            putSymbols(false, q, out);
                        q.addLast(tmp);
                    } else {
                        if (tmp == q.peekLast()) {
                            q.addLast(tmp);
                            putSymbols(true, q, out);
                        } else
                            q.addLast(tmp);
                    }
                }
                q.addLast(c);
            }
            if (q.size() == 1) {
                putSymbols(false, q, out);
            } else if (q.size() > 1) {
                int tmp = q.pollLast();

                if (tmp == q.peekLast()) {
                    q.addLast(tmp);
                    putSymbols(true, q, out);
                } else {
                    q.addLast(tmp);
                    putSymbols(false, q, out);
                }
            }

            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("No such file");
        }
    }

    public static void unzip(String inputName, String outputName) {
        InputStreamReader in;
        OutputStreamWriter out;

        try {
            in = new InputStreamReader(new FileInputStream(inputName));
            out = new OutputStreamWriter(new FileOutputStream(outputName));

            int c;
            boolean number = true;
            boolean repeat = true;
            int num = 0;

            while ((c = in.read()) != -1) {
                if (number) {
                    if (c == '-') {
                        repeat = false;
                    } else {
                        num = c - '0';
                        number = false;
                    }
                } else {
                    if (repeat) {
                        for (int i = 0; i < num; i++) {
                            out.write(c);
                        }
                        number = true;
                        repeat = true;
                    } else {
                        out.write(c);
                        num--;
                        if (num == 0) {
                            number = true;
                            repeat = true;
                        }
                    }
                }
            }

            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("No such file");
        }
    }

    public static boolean compare(String name1, String name2) {
        // compare method for testing
        InputStreamReader in;
        InputStreamReader in2;

        boolean same = true;

        try {
            in = new InputStreamReader(new FileInputStream(name1));
            in2 = new InputStreamReader(new FileInputStream(name2));

            int c;
            int c2;

            while (same) {
                c = in.read();
                c2 = in2.read();

                if ((c != -1) ^ (c2 != -1))
                    same = false;
                else if (c != c2)
                    same = false;
                else if (c == -1)
                    break;
            }

            in.close();
            in2.close();

            return same;
        } catch (IOException e) {
            System.out.println("No such file");
        }

        return same;
    }
}
