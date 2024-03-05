package me.keills;

import me.keills.annotation.Cache;
import me.keills.annotation.CacheData;
import me.keills.annotation.CacheType;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class CacheProxyInvocationHandler implements InvocationHandler {

    private final Object service;
    private final String rootDirectory;
    private final Map<CacheData,Object> cache;

    public CacheProxyInvocationHandler(Object service, String rootDirectory) {
        this.service = service;
        this.rootDirectory = rootDirectory;
        cache = new ConcurrentHashMap<>();
    }
    @Override
    public Object invoke(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if(!method.isAnnotationPresent(Cache.class))
            return method.invoke(service,args);

        Cache annotation = method.getAnnotation(Cache.class);
        List<Map.Entry<Class<?>,Object>> identity = new ArrayList<>();
        for(int i = 0; i < annotation.identityBy().length; i++)
            for(int j = 0; j < args.length; j++)
                if(annotation.identityBy()[i].equals(args[j].getClass())){
                    identity.add(new AbstractMap.SimpleEntry<Class<?>,Object>(annotation.identityBy()[i],args[j]));
                    break;
                }

        CacheData cacheData = new CacheData(method.getName(),identity);

        if(annotation.cacheType().equals(CacheType.IN_MEMORY)){
            if(cache.containsKey(cacheData)){
                System.out.println("returning cache");
                return cache.get(cacheData);
            }

            Object result = method.invoke(service,args);

            if(result instanceof List<?>){
                List<?> list = decreaseListToSize(result,annotation.containerSize());
                cache.putIfAbsent(cacheData,list);
            }
            else
                cache.putIfAbsent(cacheData,result);

            return result;
        }
        else if(annotation.cacheType().equals(CacheType.FILE)){
            try {
                Path filePath = Path.of(rootDirectory + annotation.fileNamePrefix() + "_" + method.getName());
                if (Files.exists(filePath)) {
                    return readFile(filePath.toFile(),annotation.zip());
                } else {
                    Object result = method.invoke(service, args);
                    saveFile(result,filePath.toFile(),annotation.zip(),annotation.containerSize());
                    return result;
                }
            }
            catch (NotSerializableException e) {
                System.out.println("Класс который вы пытаетесь сохранить не сериализуем! Проверьте, что он имплементирует интерфейс Serializable");
            } catch (IOException e) {
                System.out.println("Проблема при попытке чтения/записи файла. Проверьте, что вы имеете доступ к заданной рутовой папке.");
            } catch (ClassNotFoundException e) {
                System.out.println("Из данного файла невозможно прочитать объект. Он может быть поврежден, либо вы выбрали не верный файл.");
            }
        }
        return method.invoke(service,args);
    }

    private List<?> decreaseListToSize(Object list,int maxSize){
        {
            List<?> resultList = ((List<?>) list);
            return new ArrayList<>(resultList.subList(0,maxSize>resultList.size()?
                    resultList.size():maxSize));
        }
    }

    private void saveFile(Object toSave, File file, boolean compress, int maxSize) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            if (toSave instanceof List<?>)
                oos.writeObject(decreaseListToSize(toSave, maxSize));
            else
                oos.writeObject(toSave);
        }

        if(compress){
            try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file.getPath()+".zip"))){
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                byte[] data = Files.readAllBytes(file.toPath());
                zos.write(data,0,data.length);
            }
        }
    }
    private Object readFile(File file, boolean compressed) throws IOException, ClassNotFoundException {
        if(compressed){
            try (ZipFile zipFile = new ZipFile(file.getPath()+".zip")) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    try (InputStream is = zipFile.getInputStream(entry)) {
                        try (ObjectInputStream ois = new ObjectInputStream(is)) {
                            Object result = ois.readObject();
                            return result;
                        }
                    }
                }
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object result = ois.readObject();
            return result;
        }
    }
}
