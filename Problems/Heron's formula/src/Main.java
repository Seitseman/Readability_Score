import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int a = scanner.nextInt();
        final int b = scanner.nextInt();
        final int c = scanner.nextInt();

        final double p = 0.5 * (a + b + c);
        System.out.println(Math.sqrt(p * (p - a) * (p - b) * (p - c)));
    }
}