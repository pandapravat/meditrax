package meditraxx;

import com.pravat.meditrax.util.AppContextPropertyLookUp;
import com.pravat.meditrax.util.ApplicationContextUtil;

public class PropertyTest {
	public static void main(String[] args) {
		AppContextPropertyLookUp instance = ApplicationContextUtil.getInstance(AppContextPropertyLookUp.class);
		String dbPath = instance.getDbPath();
		System.out.println(dbPath);
	}
}
