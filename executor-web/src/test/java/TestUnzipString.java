import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import ru.ivmiit.web.utils.EncoderUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestUnzipString {
    public static void filePutContents(String filename, byte[] data) throws IOException {
         FileUtils.writeByteArrayToFile(new File(filename), data);
    }

    public static byte[] getData() throws IOException {
        String data = "\"UEsDBAoAAAAAACsFoVDQhZX8HwAAAB8AAAAFABwAYnVpbGRVVAkAAwJGq14CRqtedXgLAAEE6AMAAAToAwAAIyEvYmluL3NoCiMgbm90aGluZyB0byBjb21waWxlClBLAwQUAAAACAArBaFQR2jgfiQBAADgAQAAAwAcAHJ1blVUCQADAkarXgJGq151eAsAAQToAwAABOgDAABNkE9PwzAMxe/5FKabtAvr4AplQkKAuGxof85T1rltpDYOiaPt4+PQiC1SFCv5Pfu9TO4WR2MXoVNqApto4ey1c+jnofbGMdS67/EEjacBZoyBax3w4KMtQzcrRbNFBO40QxY05GEgj2BsQwIIsg+6xSeYPkCVOhi7hMp5ailyrrwelmU50v+MrA/TI9RkWRtrbAvpaW6si5xGX5tk8tyhDGaSEIYRAvWRDVkQ5laSponkjYZB2xOkTS6BAaiRMAiZSq2OCCmtUrv37e5r9VJMH4tngNCZhtX3Zv253u/y5XiX//G2zblL7jyejMeaU5C/DIurrxVx/sZi+loAXpy4Cml+QKe9ZryHnyiQ2PVtHNByKBVesB4FlRyjwQKWUmdjhfoFUEsBAh4DCgAAAAAAKwWhUNCFlfwfAAAAHwAAAAUAGAAAAAAAAQAAAO2BAAAAAGJ1aWxkVVQFAAMCRqtedXgLAAEE6AMAAAToAwAAUEsBAh4DFAAAAAgAKwWhUEdo4H4kAQAA4AEAAAMAGAAAAAAAAQAAAO2BXgAAAHJ1blVUBQADAkarXnV4CwABBOgDAAAE6AMAAFBLBQYAAAAAAgACAJQAAAC/AQAAAAA=\"";
        return data.getBytes();
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] data = Base64.decodeBase64(mapper.readValue(getData(), String.class));

        filePutContents("C:\\Users\\Azat\\unzip-test.zip", data);

        System.out.println(EncoderUtils.getMd5LowerCase(data));
    }
}
