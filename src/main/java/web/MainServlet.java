package web;

import controller.command.*;
import model.util.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    Map<String, Command> commandMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) {

        commandMap.put("/", new Home());
        commandMap.put("/login", new Login());
        commandMap.put("/logout", new Logout());
        commandMap.put("/register", new Register());
        commandMap.put("/reg_fwd", new RegForward());
        commandMap.put("/service", new ServiceForward());
        commandMap.put("/commit_req", new CommitRequest());
        commandMap.put("/my_requests", new MyRequests());
        commandMap.put("/service/choose_country", new ChooseCountry());
        commandMap.put("/service/choose_city", new ChooseCity());
        commandMap.put("/service/choose_street", new ChooseStreet());
        commandMap.put("/service/choose_house", new ChooseHouse());
        commandMap.put("/service/choose_lug_type", new ChooseLuggage());
        commandMap.put("/service/choose_weight", new ChooseWeight());
        commandMap.put("/service/create_req", new RequestAdd());
        commandMap.put("/service/reset", new ResetFields());

        //getServletContext().setAttribute(USER_LIST, new LinkedList<User>());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String path = request.getPathInfo();
        request.setAttribute(Constants.CUR_REQ_URL, request.getRequestURL());
        Command command = commandMap.get(path);
        if (command != null) {
            String page = command.execute(request, response);

            if (page.contains("redirect")) {
                response.sendRedirect(page.replace("redirect:", ""));
            } else if (page.contains("forward")) {
                request.getRequestDispatcher(page.replace("forward:", ""))
                        .forward(request, response);
            }
        }
    }
}
