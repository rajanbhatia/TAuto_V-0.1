import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
///

public class MainOne {

	public static void main(String[] args) {
		 InvocationRequest request = new DefaultInvocationRequest();
	        request.setPomFile(new File("pom.xml"));
	        request.setGoals(Collections.singletonList("install"));
	        Invoker invoker = new DefaultInvoker();
	        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
	        try {
	            invoker.execute(request);
	        } catch (MavenInvocationException e) {
	            e.printStackTrace();
	        } 
	    } 

	

}
