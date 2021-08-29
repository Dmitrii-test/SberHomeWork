package ru.dmitrii.homework04_exception.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalImpl implements Terminal{
    private final TerminalServer server;
    private final BufferedReader reader;
    static String[] enumeration = {"первую", "вторую", "третью", "четвертую"};
    static String[] category = {"счёта", "пин-кода"};

    public TerminalImpl(TerminalServer server, BufferedReader reader) {
        this.server = server;
        this.reader = reader;
    }

    public static void main(String[] args) {
        TerminalImpl terminal = new TerminalImpl(new TerminalServer(), new BufferedReader(new InputStreamReader(System.in)));
        int acc = terminal.getNum(3,0);
        System.out.println("Номера счёта получен");
        if (!terminal.server.checkAccount(acc)) {
            System.out.println("Счёт не найден");
            return;
        }
        int i =1;
        int pin = 0;
        while (true) {
            pin = terminal.getNum(4,1);
            //terminal.server.checkPin(acc,pin) &
            if (!terminal.server.checkBlock(acc)) {
                System.out.println("Счёт заблокирован");
                break;
            }
            System.out.println("Не правильно введен пин-код");
            i++;
            if (i>3) {
                terminal.server.blockAcc(acc);
            }
        }
        System.out.println("Ваш счёт:" + acc +"Ваш пин-код:" + pin);

    }


    /**
     * Метод получения номера счёта или пин-кода
     * @return List
     */
    public int getNum(int quantity, int cate) {
        int i = 0;
        Integer num = null;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            System.out.printf("Введите %s чифру %s: \n", enumeration[i], category[cate]);
            try {
                num = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException r) {
                System.out.println("Введено не число");
                continue;
            } catch (IOException e) {
                System.out.println("Ошибка ввода" + e);
                continue;
            }
            if ((int) (Math.log10(num) + 1)>1) System.out.println("Введено больше одного числа");
            else {
                stringBuilder.append(num);
                i++;
            }
            if (i>quantity-1) break;
        }
        return Integer.parseInt(stringBuilder.toString());
    }
}
