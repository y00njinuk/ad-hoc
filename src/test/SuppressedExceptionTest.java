import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class SuppressedExceptionTest {
    @Test
    public void printSuppressedException() throws IOException {
        Throwable firestException = null;
        FileInputStream fileIn = null;

        try {
            // IOException 발생
            fileIn = new FileInputStream("/invalid/file/path");
        } catch (IOException e) {
            firestException = e;
        } finally {
            try {
                // NullPointerException 발생
                fileIn.close();
            } catch (NullPointerException npe) {
                if (firestException != null) {
                    // IOException에 대한 내용을 추가하기 위해서 suppress
                    npe.addSuppressed(firestException);
                }
                throw npe;
            }
        }
    }

    @Test
    public void printSuppressedException2() throws IOException {
        String filePath = "/invalid/file/path";

        try {
            // IOException 발생
            FileInputStream fileIn = new FileInputStream(filePath);
            fileIn.close();
        } catch (Throwable e) {
            Throwable ex = new Throwable(filePath + " is not valid path");
            e.addSuppressed(ex);
            throw e;
        }
    }

}
