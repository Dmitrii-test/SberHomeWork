package ru.dmitrii.homework04_exception.terminal;

import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final Reader reader;
    private final Writer writer;

    static String[] enumeration = {"первую", "вторую", "третью", "четвертую"};
    static String[] category = {"счёта", "пин-кода", "операции"};

    public TerminalImpl(TerminalServer server, Reader reader, Writer writer) {
        this.server = server;
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        TerminalImpl terminal =
                new TerminalImpl(new TerminalServerImpl(), new Reader(new InputStreamReader(System.in)),
                        new WriterImp());
        int acc = terminal.getNum(3, 0);
        terminal.writer.write("Номер счёта получен");
        if (!terminal.server.checkAccount(acc)) return;
        terminal.checkPK(acc);
        terminal.writer.write(String.format("Ваш счёт № %s. Ваш баланс: %d", acc, terminal.server.requestBalance()));
        terminal.operations(acc);
    }

    /**
     * Метод получения номера счёта ,пин-кода или операции
     * @param quantity int
     * @param cate int
     * @return int
     */
    @Override
    public int getNum(int quantity, int cate) {
        int i = 0;
        Integer num = null;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            writer.write(String.format("Введите %s чифру %s: ", enumeration[i], category[cate]));
            try {
                num = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException r) {
                writer.write("Введено не число");
                continue;
            } catch (IOException e) {
                writer.write("Ошибка ввода" + e);
                continue;
            }
            if ((int) (Math.log10(num) + 1) > 1) writer.write("Введено больше одного числа");
            else {
                stringBuilder.append(num);
                i++;
            }
            if (i > quantity - 1) break;
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    /**
     *  Мектод проверки пин-кода
     * @param acc int
     */
    @Override
    public void checkPK(int acc) {
        int pin = 0;
        for (int i = 1; ; i++) {
            if (i > 3) {
                server.blockAcc(acc);
                i=1;
            }
            // Получение пин-кода
            pin = getNum(4, 1);
            // Проверка на блокировку счёта
            if (server.checkBlock(acc)) {
                continue;
            }
            // Проверка пин-кода
            if (!server.checkPin(acc, pin)) {
                writer.write("Не правильно введен пин-код");
                continue;
            }
            return;
        }
    }

    /**
     * Метод проведения операций через сервер
     * @param acc int
     */
    @Override
    public void operations(int acc) {
        writer.write("Выберите операцию:");
        while (true) {
            writer.write("1 - Снять деньги");
            writer.write("2 - Положить деньги");
            writer.write("3 - Выход");
            int oper = getNum(1, 2);
            switch (oper) {
                case 1: {
                    takeMoney(acc);
                    break;
                }
                case 2: {
                    putMoney(acc);
                    break;
                }
                case 3: {
                    writer.write("До свидания");
                    return;
                }
                default: {
                    writer.write("Не коректный выбор");
                }
            }
        }
    }

    /**
     * Метод обращается к серверу для получения денег
     * @param acc int
     */
    @Override
    public void putMoney(int acc) {
        writer.write("Введите какую сумму желаете положить (кратную 100):");
        int summ = 0;
        try {
            summ = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            writer.write("Не корректна введена сумма");
        }
        if (summ%100!=0) {
            writer.write("Введеная сумма не кратно 100");
            return;
        }
        server.setMoney(summ);
        writer.write(String.format("Внесено %d. Остаток: %d",summ,server.requestBalance()));
    }

    /**
     * Метод обращается к серверу для снятия денег
     * @param acc int
     */
    @Override
    public void takeMoney(int acc) {
        writer.write("Введите какую сумму желаете снять(кратную 100):");
        int summ = 0;
        try {
            summ = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            writer.write("Не корректно введена сумма");
        }
        if (summ%100!=0) {
            writer.write("Введеная сумма не кратно 100");
            return;
        }
        if (!server.checkGet(summ)) {
            writer.write("Не достаточно денег на счёте");
            return;
        }
        else server.getMoney(summ);
        writer.write(String.format("Снято %d Остаток: %d",summ,server.requestBalance()));
    }
}
