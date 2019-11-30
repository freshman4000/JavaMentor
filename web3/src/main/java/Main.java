
import com.freshman4000.servlets.MoneyTransactionServlet;
import com.freshman4000.servlets.RegistrationServlet;
import com.freshman4000.servlets.ResultServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.freshman4000.servlets.ApiServlet;

public class Main {
    public static void main(String[] args) throws Exception{
        ApiServlet apiServlet = new ApiServlet();
        MoneyTransactionServlet moneyTransactionServlet = new MoneyTransactionServlet();
        RegistrationServlet registrationServlet = new RegistrationServlet();
        ResultServlet resultServlet = new ResultServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(apiServlet), "/api/*");
        context.addServlet(new ServletHolder(moneyTransactionServlet), "/transaction");
        context.addServlet(new ServletHolder(registrationServlet), "/registration");
        context.addServlet(new ServletHolder(resultServlet), "/result");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
