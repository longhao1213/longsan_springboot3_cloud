package com.user;

import java.io.File;

public class BatchRenameFiles {
    public static void main(String[] args) {
        // 设置目标文件夹路径
        String folderPath = "/Volumes/980PRO/书籍文档/面试相关";  // 替换为你的目标文件夹路径
        String targetString = "【www.youxuan68.com】"; // 需要删除的目标字符串

        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("指定的路径不存在或不是一个目录！");
            return;
        }

        // 执行重命名操作
        renameFilesInDirectory(folder, targetString);
        System.out.println("文件重命名完成！");
    }

    /**
     * 递归扫描文件夹，批量修改文件名
     * @param folder 目标文件夹
     * @param targetString 需要删除的字符串
     */
    private static void renameFilesInDirectory(File folder, String targetString) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归处理子文件夹
                renameFilesInDirectory(file, targetString);
            } else {
                renameFile(file, targetString);
            }
        }
    }

    /**
     * 修改文件名，去除指定字符串
     * @param file 目标文件
     * @param targetString 需要删除的字符串
     */
    private static void renameFile(File file, String targetString) {
        String originalName = file.getName();

        // 检查文件名是否包含目标字符串
        if (originalName.contains(targetString)) {
            // 删除目标字符串
            String newName = originalName.replace(targetString, "");
            File newFile = new File(file.getParent(), newName);

            // 避免文件重名
            if (!newFile.exists()) {
                boolean success = file.renameTo(newFile);
                if (success) {
                    System.out.println("重命名: " + originalName + " -> " + newName);
                } else {
                    System.out.println("重命名失败: " + originalName);
                }
            } else {
                System.out.println("跳过: 目标文件已存在 - " + newName);
            }
        }
    }
}