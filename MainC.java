import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class MainC {

    public static void main(String[] args) {
        try {
            BufferedReader bRead = new BufferedReader(new InputStreamReader(System.in));
            String readInput = bRead.readLine();
            String result = Main.calc(readInput);
            System.out.println(result);
        } catch (IOException | MainException e) {
            throw new RuntimeException(e);
        }
    }
}

class Main {

    public static String calc(String input) throws MainException{

        int num1;
        int num2;
        String operator;

        Convert convert = new Convert();
        boolean isRomanCheck;

        List<String> listInput = Arrays.asList(input.split(" "));

        if (listInput.size()!=3) {
            throw new MainException("Ошибка, т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (convert.checkOperator(listInput.get(1))){
            operator = listInput.get(1);
        } else {
            throw new MainException("Ошибка, оператор не корректен, должен быть: + - * / ");
        }

        if (convert.isArabCheck(listInput.get(0)) && convert.isArabCheck(listInput.get(2))){
            num1 = Integer.parseInt(listInput.get(0));
            num2 = Integer.parseInt(listInput.get(2));
            isRomanCheck = false;
        } else if (convert.isRomanCheck(listInput.get(0)) && convert.isRomanCheck(listInput.get(2))){
            num1 = convert.romeToArabConvert(listInput.get(0));
            num2 = convert.romeToArabConvert(listInput.get(2));
            isRomanCheck = true;
            }
         else {
             throw new MainException("Ошибка, т.к. используются одновременно разные системы счисления");
        }

         if (!(num1>=1 && num1<=10)){
             throw new MainException("Ошибка, т.к. допускаются числа от 1 до 10");
         }
         if (!(num2>=1 && num2<=10)){
            throw new MainException("Ошибка, т.к. допускаются числа от 1 до 10");
        }

        int result = calcNum(num1, operator, num2);

         if (isRomanCheck && (result <= 0)){
             throw new MainException("Ошибка, т.к. в римской системе нет отрицательных чисел или нуля");
         }

        if (isRomanCheck){
            String returnRome = "";
            return returnRome + convert.arabToRomeConvert(result);
        }

        return String.valueOf(result);
    }
    static int calcNum(int nm1, String op, int nm2) {
        return switch (op) {
            case "+" -> (nm1 + nm2);
            case "-" -> (nm1 - nm2);
            case "*" -> (nm1 * nm2);
            case "/" -> (nm1 / nm2);
            default -> throw new AssertionError();
        };
    }
}

class Convert {

    HashMap<String, Integer> romeToArabHashMap = new HashMap<>();
    int[] arabArr = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] romeArr = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public Convert() {
        romeToArabHashMap.put("I", 1);
        romeToArabHashMap.put("II", 2);
        romeToArabHashMap.put("III", 3);
        romeToArabHashMap.put("IV", 4);
        romeToArabHashMap.put("V", 5);
        romeToArabHashMap.put("VI", 6);
        romeToArabHashMap.put("VII", 7);
        romeToArabHashMap.put("VIII", 8);
        romeToArabHashMap.put("IX", 9);
        romeToArabHashMap.put("X", 10);
    }
    public Integer romeToArabConvert(String str){
        if (romeToArabHashMap.containsKey(str)){
            return romeToArabHashMap.get(str);
        }
        return null;
    }

    public String arabToRomeConvert(Integer num){
        StringBuilder res = new StringBuilder();
        for (int i=0; i<arabArr.length; i++) {
            while ((num-arabArr[i])>=0){
                num-=arabArr[i];
                res.append(romeArr[i]);
            }
        }
    return res.toString();
    }

    public boolean checkOperator(String op){
        return "+".equals(op) || "-".equals(op) || "*".equals(op) || "/".equals(op);
    }

    public boolean isArabCheck(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isRomanCheck(String str) {
        return romeToArabHashMap.containsKey(str);
    }

}

class MainException extends Exception {

    public MainException(String textError){
        System.out.println(textError);
    }
}