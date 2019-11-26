import java.util.Scanner;

// 矩阵类，实现了矩阵的存储以及矩阵的加法、乘法
class Matrix {
    int m[][];  // 矩阵用数组存储
    int size;
    public Matrix(int n) {
        m = new int[n][n];
        size = n;
    }

    // 矩阵加法
    public Matrix add(Matrix other, int x) {
        Matrix result = new Matrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.m[i][j] = (m[i][j] + other.m[i][j]) % x;
            }
        }
        return result;
    }

    // 对一个矩阵加上一个单位阵I
    public Matrix addOne() {
        Matrix result = this.clone();
        for (int i = 0; i < size; i++) {
            result.m[i][i] = m[i][i] + 1;
        }
        return result;
    }

    // 实现了方阵乘法
    public Matrix multiply(Matrix other, int x) {
        Matrix result = new Matrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tmp = 0;
                for (int k = 0; k < size; k++) {  // 用临时变量tmp存值，有利于编译器优化
                    tmp += (m[i][k] % x) * (other.m[k][j] % x);  // 先进行取模运算，防止溢出
                }
                tmp %= x;
                result.m[i][j] = tmp;
            }
        }
        return result;
    }

    // 输出矩阵，本质上和toString是一样的
    public void output() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size-1; j++) {
                System.out.printf("%d ", m[i][j]);
            }
            System.out.printf("%d\n", m[i][size-1]);
        }
    }

    // 方便地复制矩阵
    @Override
    public Matrix clone() {
        Matrix tmp = new Matrix(size);
        // 二维数组必须逐行复制，否则复制的就是指针
        for (int i = 0; i < size; i++) { 
            tmp.m[i] = m[i].clone();
        }
        return tmp;
    }
}

public class POJ_3233 {
    static Matrix ans;

    public static Matrix fastPow(Matrix raw, int k, int x) {  // 求和
        if (k == 1) {  // 当k为1时，即结束递归
            ans = raw.clone();
            return raw;
        }
        else {
            Matrix result = fastPow(raw, k / 2, x);  // f(k/2)
            result = result.multiply(ans.addOne(), x);  // (A+I)f(k/2)
            ans = ans.multiply(ans, x);  // 计算A^{2k}
            if (k % 2 == 1) {  // 若k为奇数，ans和f(k/2)就要额外加上一项
                ans = ans.multiply(raw, x);
                result = result.add(ans, x);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        int x = input.nextInt();
        Matrix a = new Matrix(n);
        for (int i = 0; i < n; i++) {  // 读取输入矩阵
            for (int j = 0; j < n; j++) {
                a.m[i][j] = input.nextInt();
            }
        }
        fastPow(a, k, x).output(); // 输出计算结果
        input.close();
    }
}
