//package za.co.wethinkcode.robots.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.*;
//import za.co.wethinkcode.robots.config.Config;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//class ConfigTest {
//
//    private Path tempFile;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        // Create a temp file for each test
//        tempFile = Files.createTempFile("testConfig", ".json");
//    }
//
//    @AfterEach
//    void tearDown() throws IOException {
//        Files.deleteIfExists(tempFile);
//    }
//
//    @Test
//    void testCreateConfigFile_writesContentToSpecifiedFile() throws IOException {
//        String testContent = "{\"test\": 123}";
//
//        Config.createConfigFile(testContent, tempFile.toString());
//
//        String content = Files.readString(tempFile);
//        assertEquals(testContent, content);
//    }
//
//    @Test
//    void testCreateDefaultConfigFile_createsFileWithExpectedContent() throws IOException {
//        Config.createDefaultConfigFile(tempFile.toString());
//
//        String content = Files.readString(tempFile);
//
//        assertNotNull(content);
//        assertTrue(content.contains("\"world\""));
//        assertTrue(content.contains("\"make\""));
//        assertTrue(content.contains("\"sniper\""));
//    }
//}
//
