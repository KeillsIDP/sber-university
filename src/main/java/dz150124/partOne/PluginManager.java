package dz150124.partOne;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PluginManager extends ClassLoader{
    private final String pluginRootDirectory;
    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }
    public Plugin load(String pluginName, String pluginClassName) throws Exception {
        String pathToPlugin = pluginRootDirectory + pluginName;
        Class<?> loadedPlugin = loadClassFromPlugin(pathToPlugin,pluginClassName);
        Constructor<?> pluginClassLoader = loadedPlugin.getConstructor();

        return (Plugin) pluginClassLoader.newInstance();
    }

    private Class<?> loadClassFromPlugin(String pluginPath, String className) throws Exception {
        Path path = Paths.get(pluginPath + ".class");
        if (Files.exists(path)) {
            byte[] classBytes = Files.readAllBytes(path);
            return defineClass(className, classBytes, 0, classBytes.length);
        } else {
            return super.loadClass(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return findClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name, resolve);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String filePath = pluginRootDirectory + File.separator + name.replace('.', File.separatorChar) + ".class";
            byte[] bytes = loadClassData(filePath);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Класс с именем " + name + " не был загружен", e);
        }
    }

    private byte[] loadClassData(String filePath) throws IOException {
        File classFile = new File(filePath);
        long length = classFile.length();
        byte[] classBytes = new byte[(int)length];
        try (InputStream in = new FileInputStream(classFile)) {
            in.read(classBytes);
        }
        return classBytes;
    }
}
