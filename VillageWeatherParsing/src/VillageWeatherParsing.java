import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VillageWeatherParsing {
	public static void main(String[] args) {
		try {
			String nx = "58";
			String ny = "126";
			String baseData = "20190312";
			String baseTime = "1800";
			String ServiceKey = "";
			
			String urlStr = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?"
					+ "ServiceKey=" + ServiceKey
					+ "&base_date=" + baseData
					+ "&base_time=" + baseTime
					+ "&nx=" + nx
					+ "&ny=" + ny
					+ "&_type=json";
			
			URL url = new URL(urlStr);
			
			BufferedReader bf;
			String line = "";
			String result = "";
			
			bf = new BufferedReader(new InputStreamReader(url.openStream()));
			
			while((line=bf.readLine()) != null) {
				result = result.concat(line);
			}
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(result);
			
			JSONObject parse_responsd = (JSONObject)obj.get("response");
			JSONObject parse_body = (JSONObject)parse_responsd.get("body");
			JSONObject parse_items = (JSONObject)parse_body.get("items");
			JSONArray parse_item = (JSONArray)parse_items.get("item");
			
			String category = null;
			JSONObject weather;
			
			for(int i = 0 ; i < parse_item.size() ; i++) {
				weather = (JSONObject)parse_item.get(i);
				double obsr_value = ((Long)weather.get("obsrValue")).doubleValue();
				category = (String)weather.get("category");
				
				System.out.print("¹è¿­ÀÇ " + i + "¹øÂ° ¿ä¼Ò");
				System.out.print("	category : " + category);
				System.out.print("	obsr_value" + obsr_value);
				System.out.println();
			}
			
			bf.close();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
