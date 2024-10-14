package fc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {

    private String cookieFile;
    private List<String> cookies = new ArrayList<>();

    public Cookie(String cookieFile) throws IOException {
        FileReader fr = new FileReader(cookieFile);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            cookies.add(line);
        }
        br.close();
    }

    public String getCookie() {
        if (cookies.isEmpty()) {
            return "No cookies available!";
        }
        Random random = new Random();
        return cookies.get(random.nextInt(cookies.size()));
    }
}
