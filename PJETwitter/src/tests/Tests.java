package tests;

import static org.junit.Assert.assertEquals;
import helper.Utils;

import org.junit.Test;


public class Tests
{
	@Test
	public void cleanTweetsTest()
	{
		String t1_src = "#brevetahiti #replay Mega la blague: Avec Toto, Siki et les autres,… http://t.co/jTQAgKFKXi http://t.co/pnN2yQ0oY5 http://t.co/g6Cagex9pk";
		String t1_exp = "Mega la blague: Avec Toto, Siki et les autres,…";

		String t2_src = "@ThomasPages83 tu manques Toto";
		String t2_exp = "tu manques Toto";

		String t3_src = "@toto_sasishu @Sylv33 @TuTulsa @clomimine @Lapinou1841 @Domi52778 @serenel14278447 @Gacatja bonjour toto bon mercredi biz";
		String t3_exp = "bonjour toto bon mercredi biz";

		String t4_src = "[On révise le Blog !]: 0 + 0 = la tête à toto ou comment nos enfants ne savent plus compter http://t.co/673npWXtWG";
		String t4_exp = "[On révise le Blog !]: 0 + 0 = la tête à toto ou comment nos enfants ne savent plus compter";
		
		String t5_src = "@ toto @tata On va coder en #java sous l'#eau #javadoc #hash#fail";
		String t5_exp = "toto On va coder en  sous l";
		
		String t6_src = "@toto ##";
		String t6_exp = "";
				
		
		assertEquals(t1_exp, Utils.cleanTweet(t1_src));
		assertEquals(t2_exp, Utils.cleanTweet(t2_src));
		assertEquals(t3_exp, Utils.cleanTweet(t3_src));
		assertEquals(t4_exp, Utils.cleanTweet(t4_src));
		assertEquals(t5_exp, Utils.cleanTweet(t5_src));
		assertEquals(t6_exp, Utils.cleanTweet(t6_src));
		
		
		
		
	}


}
