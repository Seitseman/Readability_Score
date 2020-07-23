import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final double u1 = scanner.nextDouble();
        final double u2 = scanner.nextDouble();

        final double v1 = scanner.nextDouble();
        final double v2 = scanner.nextDouble();

        final double uLength = Math.sqrt(u1 * u1 + u2 * u2);
        final double vLength = Math.sqrt(v1 * v1 + v2 * v2);

        final double uv = u1 * v1 + u2 * v2;

        System.out.println(Math.toDegrees(Math.acos(uv / (uLength * vLength))));
    }
}