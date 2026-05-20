package com.example.practic_6.datas;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.practic_6.domains.models.Note;
import java.util.ArrayList;

public class NoteContext {
    /**
     * Получает все заметки из базы данных
     */
    public static ArrayList<Note> AllNotes() {
        // Создаём пустой список для хранения заметок
        ArrayList<Note> allNotes = new ArrayList<>();

        // Выполняем запрос к таблице Notes
        Cursor cursor = DbContext.sqLiteDatabase.query(
                "Notes", null, null, null, null, null, null);

        // Проверяем, есть ли хотя бы одна запись в результате
        // moveToFirst() перемещает курсор на первую запись и возвращает true, если запись существует
        if (cursor.moveToFirst() == false) {
            // Если записей нет, возвращаем пустой список
            return allNotes;
        }

        // Проходим по всем записям из результата запроса
        do {
            // Создаём новый объект заметки
            Note note = new Note();

            // Заполняем поля заметки данными из текущей строки курсора
            note.id = cursor.getInt(0); // ID заметки
            note.title = cursor.getString(1); // Заголовок
            note.text = cursor.getString(2); // Текст
            note.date = cursor.getString(3); // Дата
            note.color = cursor.getString(4); // Цвет

            // Добавляем заполненную заметку в список
            allNotes.add(note);
        } while (cursor.moveToNext());

        // Важно: всегда закрываем курсор после использования
        // чтобы освободить ресурсы и избежать утечек памяти
        cursor.close();

        // Возвращаем список всех заметок
        return allNotes;
    }

    /**
     * Сохраняет или обновляет заметку в базе данных
     * @param note объект заметки для сохранения
     * @param update флаг: true - обновление существующей, false - создание новой
     */
    public static void Save(Note note, boolean update) {
        // ContentValues - контейнер для хранения пар "ключ-значение"
        // Используется для вставки и обновления данных в БД
        ContentValues CV = new ContentValues();

        // Заполняем ContentValues данными из объекта Note
        // Ключи должны соответствовать названиям столбцов в таблице
        CV.put("Title", note.title); // Заголовок заметки
        CV.put("Text", note.text); // Текст заметки
        CV.put("Date", note.date); // Дата заметки
        CV.put("Color", note.color); // Цвет заметки

        // Проверяем, нужно ли обновить существующую запись или создать новую
        if (update == false) {
            // ВСТАВКА НОВОЙ ЗАПИСИ
            DbContext.sqLiteDatabase.insert(
                    "Notes", // Имя таблицы
                    null, // nullColumnHack (не используется)
                    CV); // Значения для вставки
        } else {
            // ОБНОВЛЕНИЕ СУЩЕСТВУЮЩЕЙ ЗАПИСИ
            DbContext.sqLiteDatabase.update(
                    "Notes", // Имя таблицы
                    CV, // Новые значения
                    "Id = ?", // Условие: обновить запись с указанным ID
                    new String[]{String.valueOf(note.id)}); // Значение ID для условия
        }
    }

    /**
     * Удаляет заметку из базы данных
     */
    public static void Delete(Note note) {
        // Выполняем удаление из таблицы Notes
        DbContext.sqLiteDatabase.delete(
                "Notes", // Имя таблицы
                "Id = ?", // Условие: удалить запись с указанным ID
                new String[]{String.valueOf(note.id)}); // Значение ID для условия
    }
}
