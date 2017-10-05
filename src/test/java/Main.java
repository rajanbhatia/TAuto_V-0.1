
import org.testng.TestNG;
public class Main {		
		public static void main(String[] args) {							
				//TestListenerAdapter tla = new TestListenerAdapter();
				TestNG testng = new TestNG();
			
				testng.setTestClasses(new Class[] { Master.class });
				//testng.setAnnotationTransformer
				//testng.addListener(new ITestNGListener() {		});
				testng.run();
			
	}

}
