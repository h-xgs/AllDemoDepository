package javaSE.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class ArraysTest {

    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(disorderedArrangement(arr)));
    }

    /**
     * 数组无序排列输出
     * @param arr 输入一个数组
     * @return 返回打乱了顺序的数组
     */
    public static int[] disorderedArrangement(int[] arr) {
        int[] result = new int[arr.length];
        System.out.println(Arrays.toString(arr));
        HashSet indexSet = new HashSet();
        int index = random(arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            while (!indexSet.add(index)) {
                index = random(arr.length - 1);
            }
            result[i] = arr[index];
        }
        return result;
    }

    private static int random(int n) {
        return new Random().nextInt(n + 1);
    }

}
