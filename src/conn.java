import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // ПОДКЛЮЧЕНИЕ К БД
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:My_cats");

        System.out.println("База Подключена!");
    }

    // СОЗДАНИЕ ТАБЛИЦЫ
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'types' ('id' INTEGER PRIMARY KEY, 'type' VARCHAR(100));");

        System.out.println("Таблица создана или уже существует.");
    }

    // ЗАПОЛНЕНИЕ ТАБЛИЦЫ
    public static void WriteDB() throws SQLException
    {
        File file = new File("C:/Users/ivalu/IdeaProjects/sqlite/types.txt");
        try {
            Scanner sc = new Scanner(file);
            int i = 1;
            while (sc.hasNext()){
                String str = sc.nextLine();
                statmt.execute("INSERT INTO 'TYPES' VALUES (" + i + ", '" + str + "');");
                i ++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        System.out.println("Таблица заполнена");
    }

    // ВЫВОД ТАБЛИЦЫ
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM TYPES");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String type = resSet.getString("type");
            System.out.println( "ID = " + id );
            System.out.println( "type = " + type );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    public static void Delete_type(int id) throws SQLException {
        statmt = conn.createStatement();
        statmt.execute("DELETE FROM TYPES WHERE ID = " + id + ";");
        System.out.println("Данные с id = " + id + " удалены из таблицы");
    }

    public static void Update_type(int id, String new_type) throws SQLException {
        statmt = conn.createStatement();
        statmt.execute("UPDATE TYPES SET TYPE = " + new_type + " WHERE ID = " + id + ";");
        System.out.println("Данные с id = " + id + " изменены");
    }

    // ЗАКРЫТИЕ
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}
