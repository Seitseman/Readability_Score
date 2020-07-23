import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        for (int i = 0; i < text.length() / 2; i++) {
            if(text.charAt(i) != text.charAt(text.length() - 1 - i)) {
                System.out.println("no");
                return;
            }
        }
        System.out.println("yes");
    }
}