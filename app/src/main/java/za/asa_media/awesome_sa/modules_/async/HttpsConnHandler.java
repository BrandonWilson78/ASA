package za.asa_media.awesome_sa.modules_.async;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Snow-Dell-07 on 4/20/2017.
 */

public class HttpsConnHandler {
    private static final String TAG = HttpsConnHandler.class.getSimpleName();

   /*   @Nullable
        public static JSONObject requestWebService(String serviceUrl) {
        disableConnectionReuseIfNecessary();
        HttpURLConnection urlConnection = null;
        try {
        //     Create Connection

              URL urlToRequest = new URL(serviceUrl);
              urlConnection = (HttpURLConnection) urlToRequest.openConnection();

           //   urlConnection.setRequestMethod("GET");
           //   urlConnection.setConnectTimeout(5000);
           //   urlConnection.setReadTimeout(5000);

              InputStream in = new BufferedInputStream(urlConnection.getInputStream());
              return new JSONObject(getResponseText(in));
          } catch (Exception e) {
              //    URL is invalid
          } finally {
              if (urlConnection != null) {
                  urlConnection.disconnect();
              }
          }
          return null;
      }

      private static void disableConnectionReuseIfNecessary() {

          if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
              System.setProperty("http.keepAlive", "false");
          }
      }

      private static String getResponseText(InputStream stream) {
          return new Scanner(stream).useDelimiter("\\A").next();
      }*/

   /* public static JSONObject requestWebService(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
         //   conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            return new JSONObject(response);
        } catch (Exception e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            return null;
        }

    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }*/
    public static JSONObject requestWebService(String urlString) {
        try {
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /*milliseconds */);
            urlConnection.setConnectTimeout(15000 /*milliseconds*/ );

            urlConnection.setDoOutput(true);
            urlConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            char[] buffer = new char[1024];

            String jsonString = new String();

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            jsonString = sb.toString();

            System.out.println("JSON: " + jsonString);
            urlConnection.disconnect();

            return new JSONObject(jsonString);
        } catch (Exception ex) {

            return null;
        }
    }
    /*
    public static JSONObject requestWebService(String strUrl)  {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }


            Log.d("downloadUrl", data.toString());
            br.close();
            return new JSONObject(data = sb.toString());
        } catch (Exception e) {
            Log.d("Exception", e.toString());
            return null;
        } finally {
            try {
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            urlConnection.disconnect();
        }

    }
    */
}
