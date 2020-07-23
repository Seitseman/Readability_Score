import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String subs = scanner.nextLine();
        int idx = 0;
        int counter = 0;

        while (idx != -1) {
            idx = text.indexOf(subs, idx);
            if (idx != -1) {
                ++counter;
                idx += subs.length();
            }
        }

        System.out.println(counter);
    }
}