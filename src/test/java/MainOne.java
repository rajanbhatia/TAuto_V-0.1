
import org.testng.TestListenerAdapter;
///
import org.testng.TestNG;

public class MainOne {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
					
			TestListenerAdapter tla = new TestListenerAdapter();
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { Master.class });
			testng.addListener(tla);
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
