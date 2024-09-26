package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

interface Task
{
    void start();
    void stop();
}

public class FolderSyncTask implements Task {

    private final Path sourceDir;
    private final Path targetDir;
    private final AtomicBoolean isRunning;
    private Thread syncThread;

    public FolderSyncTask(String sourceDirPath, String targetDirPath) {
        this.sourceDir = Paths.get(sourceDirPath);
        this.targetDir = Paths.get(targetDirPath);
        this.isRunning = new AtomicBoolean(false);
    }

    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            System.out.println("Синхронизация началась...");
            syncThread = new Thread(() -> {
                while (isRunning.get()) {
                    try {
                        syncDirectories(sourceDir, targetDir);
                        Thread.sleep(5000);
                    } catch (IOException e) {
                        System.err.println("Ошибка синхронизации: " + e.getMessage());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Поток синхронизации прерван.");
                    }
                }
                System.out.println("Синхронизация завершена.");
            });
            syncThread.start();
        } else {
            System.out.println("Синхронизация уже запущена.");
        }
    }

    @Override
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            System.out.println("Запрос на остановку синхронизации отправлен.");
            if (syncThread != null) {
                syncThread.interrupt();
            }
        } else {
            System.out.println("Синхронизация уже остановлена.");
        }
    }

    private void syncDirectories(Path sourceDir, Path targetDir) throws IOException {
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!isRunning.get()) {
                    return FileVisitResult.TERMINATE;
                }
                Path relativePath = sourceDir.relativize(dir);
                Path targetPath = targetDir.resolve(relativePath);
                if (Files.notExists(targetPath)) {
                    Files.createDirectories(targetPath);
                    System.out.println("Создана директория: " + targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!isRunning.get()) {
                    return FileVisitResult.TERMINATE;
                }

                Path relativePath = sourceDir.relativize(file);
                Path targetFile = targetDir.resolve(relativePath);

                if (Files.notExists(targetFile) || !filesAreIdentical(file, targetFile)) {
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    System.out.println("Синхронизирован файл: " + file);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.err.println("Не удалось посетить файл: " + file + " из-за ошибки: " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private boolean filesAreIdentical(Path file1, Path file2) throws IOException {
        return Files.size(file1) == Files.size(file2) &&
                Files.getLastModifiedTime(file1).equals(Files.getLastModifiedTime(file2));
    }

    public static void execute(Scanner scanner) {
        System.out.print("\n    Задание 5.\n");
        System.out.print("Введите путь к исходной папке: ");
        String sourceDirPath = scanner.nextLine().trim();

        System.out.print("Введите путь к целевой папке: ");
        String targetDirPath = scanner.nextLine().trim();

        FolderSyncTask syncTask = new FolderSyncTask(sourceDirPath, targetDirPath);

        System.out.println("Запуск синхронизации...");
        syncTask.start();

        System.out.println("Нажмите Enter для остановки синхронизации.");
        scanner.nextLine();
        syncTask.stop();
    }
}
