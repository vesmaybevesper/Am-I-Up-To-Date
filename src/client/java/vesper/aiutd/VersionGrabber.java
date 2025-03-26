package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class VersionGrabber {

    private static final Logger log = LoggerFactory.getLogger(VersionGrabber.class);

    // Grab version from the Modrinth API
    public static String getLatestVersion(int location) {
        StringBuilder result = new StringBuilder();
        try {
            URI url = new URI(MyConfig.versionAPI);
            HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Check HTTP response code first
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                log.error("HTTP request failed with response code: " + responseCode);
                return MyConfig.versionCache;
            }

            // Use try-with-resources to ensure the reader is closed
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }

            JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();
            if (!jsonArray.isEmpty() && location < jsonArray.size()) {
                JsonObject versionObject = jsonArray.get(location).getAsJsonObject();
                String versionNumber = versionObject.get("version_number").getAsString();
                MyConfig.versionCache = versionNumber;
                return versionNumber;
            }
        } catch (Exception fetchVersionError) {
            log.error("fetchVersionError: ", fetchVersionError);
            log.info("If this is first launch this error can be ignored");
            return MyConfig.versionCache;
        }
        return null;
    }
}