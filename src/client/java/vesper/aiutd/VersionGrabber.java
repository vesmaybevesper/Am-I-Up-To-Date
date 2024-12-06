package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class VersionGrabber {

    private static final Logger log = LoggerFactory.getLogger(VersionGrabber.class);

    // grab version from Modrinth API
    public static String getLatestVersion() {
        StringBuilder result = new StringBuilder();
        try {
            URI url = new URI(MyConfig.versionAPI);

            HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();
            if (!jsonArray.isEmpty()) {
                JsonElement getVersionElement = jsonArray.get(0);
                return getVersionElement.getAsJsonObject().get("version_number").getAsString();
            }
        } catch (Exception fetchVersionError) {
            log.error("fetchVersionError: ", fetchVersionError);
            log.info("If this is first launch this error can be ignored");
        }
        return null;
    }
}
