package dz150124.partTwo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader{
    private final int key;
    private final File dir;
    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String[] splitedClassName = name.split("\\.");
            String classFileName = splitedClassName[splitedClassName.length-1];

            Path classPath = Paths.get(dir.getPath()+"/"+classFileName+".class");

            if(!Files.exists(classPath))
                throw new IOException("Класс по данной ссылке не найден");

            byte[] bytes = loadClassData(classPath);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }

    private byte[] loadClassData(Path classPath) throws IOException {
        byte[] encryptedClassBytes = Files.readAllBytes(classPath);
        for(int i = 0; i < encryptedClassBytes.length; i++)
            encryptedClassBytes[i] -= key;
        return encryptedClassBytes;
    }
}
