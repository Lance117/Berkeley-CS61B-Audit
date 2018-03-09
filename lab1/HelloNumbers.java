public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int sum = 0;
        while (x < 10) {
            sum = x + sum;
            System.out.print(sum);
            if (x < 9)
                System.out.print(" ");
            else
                System.out.println();        
            x++;
        }
    }
}
