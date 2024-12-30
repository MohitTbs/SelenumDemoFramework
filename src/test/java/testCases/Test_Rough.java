package testCases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import base.DataUtil1;

public class Test_Rough {
	
	@Test(dataProviderClass = DataUtil1.class, dataProvider = "dp3")
	public void printData(Hashtable<String, String> data) {
		
		System.out.println(data.get("username") +"---->"+data.get("password"));
	}

}
