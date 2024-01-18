package dz150124.partTwo;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class EncryptedClassLoaderTest {

    String compiledPath = "src/main/java/dz150124/partTwo/";

    @Test
    void findClass_Success() {
        int key = 5;
        try {
            //Encryptor.encrypt(compiledPath,"ClassToEncrypt",key);
            EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(key,new File(compiledPath), ClassLoader.getSystemClassLoader());
            Class<?> decrypted = encryptedClassLoader.findClass("dz150124.partTwo.ClassToEncrypt");

            Method[] methods = decrypted.getDeclaredMethods();
            for(Method method: methods)
                method.invoke(decrypted.getConstructor().newInstance());

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findClass_Failure() {
        int key = 5;
        //Encryptor.encrypt(compiledPath,"ClassToEncrypt",key);
        EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(key,new File(compiledPath), ClassLoader.getSystemClassLoader());
        assertThrows(ClassNotFoundException.class,()-> encryptedClassLoader.findClass("dz150124.partTwo.ClassToEncryp"));
    }

    @Test
    void findClass_IncorrectKey() {
        int key = 5;
        //Encryptor.encrypt(compiledPath,"ClassToEncrypt",key);
        EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(key-1,new File(compiledPath), ClassLoader.getSystemClassLoader());
        assertThrows(ClassFormatError.class,()-> encryptedClassLoader.findClass("dz150124.partTwo.ClassToEncrypt"));
    }
}