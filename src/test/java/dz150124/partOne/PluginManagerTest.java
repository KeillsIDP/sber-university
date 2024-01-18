package dz150124.partOne;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PluginManagerTest {

    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    final String COMPILED_CLASSES_PATH = "src/main/java/dz150124/partOne/compiled/dz150124/partOne/plugins/";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void load_Compiled_Plugins_FirstPlugin() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        try {
            pluginManager.load("FirstTestPlugin","dz150124.partOne.plugins.FirstTestPlugin").doUsefull();
            assertEquals("Я плагин, который был написан первым", outputStreamCaptor.toString().trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void load_Compiled_Plugins_SecondPlugin() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        try {
            pluginManager.load("SecondTestPlugin","dz150124.partOne.plugins.SecondTestPlugin").doUsefull();
            assertEquals("Я плагин, который был написан вторым", outputStreamCaptor.toString().trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void load_Browser_Plugins_FirstPlugin() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        try {
            pluginManager.load("TestPlugin","dz150124.partOne.plugins.FirstTestPlugin").doUsefull();
            assertEquals("Я плагин, который был написан первым и теперь меня изменили!", outputStreamCaptor.toString().trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void load_Browser_Plugins_SecondPlugin() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        try {
            pluginManager.load("TestPlugin","dz150124.partOne.plugins.SecondTestPlugin").doUsefull();
            assertEquals("Я плагин, который был написан вторым и тоже изменен!", outputStreamCaptor.toString().trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void load_Plugin_WithWrongName_NoClassDefFoundError() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        assertThrows(NoClassDefFoundError.class,()->pluginManager.load("FirstTestPlugin","dz150124.partOne.plugins.SecondTestPlugin"));
    }

    @Test
    void load_Plugin_WithWrongClassName_NoClassDefFoundError() {
        PluginManager pluginManager = new PluginManager(COMPILED_CLASSES_PATH);
        assertThrows(NoClassDefFoundError.class,()->pluginManager.load("FirstTestPlugin","dz150124.partOne.plugins.TestPlugin"));
    }
}