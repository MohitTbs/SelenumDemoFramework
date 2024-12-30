package testCases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import base.DataUtil1;

public class Test_Rough {
	
	
	@Test(dataProvider = "dp3", dataProviderClass = DataUtil1.class)
	public void getTest1(Hashtable data) {
		System.out.println(data.get("username")+"---"+data.get("password"));
	}

}
