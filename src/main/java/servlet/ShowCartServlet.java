package servlet;

import bean.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.CartService;
import service.ItemService;

import javax.jws.WebService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {
    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext wac= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        AutowireCapableBeanFactory factory=wac.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession(true);
        List<Item> cart = (List<Item>)itemService.findNoIncludedItems();
        if(cart != null){
            cartService.setCart(cart);
        }else{
            cart = new ArrayList<Item>();
            cartService.setCart(cart);

        }

        session.setAttribute("cart", cartService.getCart());

       request.getRequestDispatcher("cart.jsp").forward(request,response);

    }
}
