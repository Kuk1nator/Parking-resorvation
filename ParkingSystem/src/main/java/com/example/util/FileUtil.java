package com.example.util;

import com.example.model.ParkingSpot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<ParkingSpot> loadParkingSpots(String filename) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ParkingSpot>>(){}.getType();

        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename + ". Будет создан новый файл.");
            return null; // Возвращаем null, чтобы ParkingService знал, что нужно создать новый список
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return null; // Возвращаем null в случае ошибки
        }
    }

    public static void saveParkingSpots(List<ParkingSpot> parkingSpots, String filename) {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(parkingSpots, writer);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}