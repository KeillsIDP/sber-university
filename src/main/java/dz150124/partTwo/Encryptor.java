package dz150124.partTwo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Encryptor {
    public static void encrypt(String classPath, String className, int shift) throws IOException {
        byte[] classBytes = Files.readAllBytes(Paths.get(classPath+className+".class"));
        for(int i = 0; i < classBytes.length; i++)
            classBytes[i] += shift;

        try (FileOutputStream fos = new FileOutputStream(classPath+className+"Encrypted.class")) {
            fos.write(classBytes);
        }

    }
}
