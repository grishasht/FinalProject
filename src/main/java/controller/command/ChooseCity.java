package controller.command;

import model.entity.User;
import model.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChooseCity implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String countryName = request.getParameter(Constants.CITY_NAME);
        request.getSession().setAttribute(Constants.CITY_NAME, countryName);
        String role = ((User) request.getSession().getAttribute(Constants.SESSION_USER))
                .getRole()
                .getValue();


        return "forward:/WEB-INF/" + role + "/service.jsp";
    }
}
