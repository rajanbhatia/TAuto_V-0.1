import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class MainOne{
		public static void main(String[] args)
		{				
			//TestListenerAdapter tla = new TestListenerAdapter();
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { Master.class });
			//List<String> suites = Lists.newArrayList();
			//suites.add("c:/tests/testng1.xml");//path to xml..
			//suites.add("c:/tests/testng2.xml");
			//testng.setTestSuites(suites);
			//testng.addListener(tla);
			testng.run();
		
		
		/**
		 InvocationRequest request = new DefaultInvocationRequest();
	        request.setPomFile(new File("pom.xml"));
	        request.setGoals(Collections.singletonList("install"));
	        Invoker invoker = new DefaultInvoker();
	        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
	        try {
	            invoker.execute(request);
	        } catch (MavenInvocationException e) {
	            e.printStackTrace();
	        }**/ 
	    }
	}
