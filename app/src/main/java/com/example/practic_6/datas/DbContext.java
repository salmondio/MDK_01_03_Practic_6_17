package com.example.practic_6.datas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {
    // Статическая переменная для доступа к базе данных из любого места приложения
    // Содержит объект для выполнения запросов к БД
    public static SQLiteDatabase sqLiteDatabase;

    public DbContext(Context context) {
        super(context, "DbNotes", null, 1);

        // Инициализация статической переменной для работы с БД
        // getWritableDatabase() открывает базу данных для записи
        // Если база данных не существует, она будет создана автоматически
        sqLiteDatabase = this.getWritableDatabase();
    }

    /**
     * Вызывается автоматически при первом создании базы данных
     * @param db Объект базы данных, через который выполняются SQL-запросы
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Выполнение SQL-запроса для создания таблицы Notes
        db.execSQL("CREATE TABLE Notes (" +
                "Id integer primary key autoincrement," + // Первичный ключ с автоинкрементом
                "Title text," + // Заголовок (текстовый тип)
                "Text text," + // Содержимое (текстовый тип)
                "Date text," + // Дата (хранится как текст в формате ISO)
                "Color text)"); // Цвет (в формате HEX, например #FF0000)
    }

    /**
     * Вызывается при обновлении версии базы данных
     * Используется для изменения структуры БД при выходе новой версии приложения
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Пока метод пуст, так как версия БД не менялась (версия 1)
        // При изменении версии здесь нужно реализовать логику обновления
        // Например: удаление старых таблиц и создание новых с сохранением данных
    }
}
