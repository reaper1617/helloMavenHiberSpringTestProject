package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.AdminDTO;
import com.gerasimchuk.dto.AdminDTOImpl;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.AdminService;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {


    private UserDAO userDAO;
    private DriverDAO driverDAO;
    private ManagerDAO managerDAO;
    private OrderDAO orderDAO;
    private TruckDAO truckDAO;
    private CityDAO cityDAO;
    private CargoDAO cargoDAO;
    private RoutepointDAO routepointDAO;
    private RouteDAO routeDAO;
    private AdminService adminService;

    @Autowired
    public AdminController(UserDAO userDAO, DriverDAO driverDAO, ManagerDAO managerDAO, OrderDAO orderDAO, TruckDAO truckDAO, CityDAO cityDAO, CargoDAO cargoDAO, RoutepointDAO routepointDAO, RouteDAO routeDAO, AdminService adminService) {
        this.userDAO = userDAO;
        this.driverDAO = driverDAO;
        this.managerDAO = managerDAO;
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        this.cityDAO = cityDAO;
        this.cargoDAO = cargoDAO;
        this.routepointDAO = routepointDAO;
        this.routeDAO = routeDAO;
        this.adminService = adminService;
    }


    private boolean isAccessGranted(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null ){
            ui.addAttribute("errorMessage", "You must be authorised user to open this page");
            return false;
        }
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.ADMIN){
            ui.addAttribute("errorMessage", "You must be system administrator to open this page");
            return false;
        }
        return true;
    }

    private boolean setTruckPageParams(Model ui){
        List<City> cities = (ArrayList)cityDAO.getAll();
        if (cities==null) return false;
        if (cities.size()== 0) return false;
        ui.addAttribute("cities", cities);
        return true;
    }


    private boolean setUserDriverPageParams(Model ui){
        List<City> citiesList = (ArrayList)cityDAO.getAll();
        if (citiesList==null) return false;
        if (citiesList.size()== 0) return false;
        ui.addAttribute("cities", citiesList);
        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        if (trucks ==null) trucks = new ArrayList<>();
        if (trucks.size()==0) trucks.add(new Truck("No available trucks",1,1, TruckState.NOTREADY,null));
        ui.addAttribute("trucks", trucks);
        return true;
    }


    boolean setAdminPageParams(Model ui){
        List<User> users = (ArrayList)userDAO.getAll();
        List<Manager> managers = (ArrayList)managerDAO.getAll();
        List<Driver> drivers = (ArrayList)driverDAO.getAll();
        List<Order> orders = (ArrayList)orderDAO.getAll();
        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        List<City> cities = (ArrayList)cityDAO.getAll();
        List<Cargo> cargos = (ArrayList)cargoDAO.getAll();
        List<RoutePoint> routePoints = (ArrayList)routepointDAO.getAll();
        List<Route> routes = (ArrayList)routeDAO.getAll();

        if (users==null) users = new ArrayList<>();
        if (users.size()==0) users.add(new User("No", "users", "found","0000", UserRole.DRIVER, new Manager()));
        ui.addAttribute("users", users);

        if (managers==null) managers = new ArrayList<>();
        if (managers.size()==0) managers.add(new Manager());
        ui.addAttribute("managers", managers);

        if (drivers==null) drivers = new ArrayList<>();
        if (drivers.size()==0) drivers.add(new Driver());
        ui.addAttribute("drivers", drivers);

        if (orders==null) orders = new ArrayList<>();
        if (orders.size() == 0) orders.add(new Order(OrderState.PREPARED,"No orders found", new Timestamp(10l),null));
        ui.addAttribute("orders", orders);

        if (trucks==null) trucks = new ArrayList<>();
        if (trucks.size()==0) trucks.add(new Truck("No trucks found",1,1, TruckState.NOTREADY,null));
        ui.addAttribute("trucks", trucks);

        if (cities==null) cities = new ArrayList<>();
        if (cities.size()== 0) cities.add(new City("No cities found",CityHasAgency.HASNOT));
        ui.addAttribute("cities", cities);

        if (cargos==null) cargos = new ArrayList<>();
        if (cargos.size()==0) cargos.add(new Cargo("No cargos found",1,CargoStatus.PREPARED,null,null));
        ui.addAttribute("cargos", cargos);

        if (routePoints==null) routePoints = new ArrayList<>();
        if (routePoints.size()==0) routePoints.add(new RoutePoint(RoutePointType.LOADING, null));
        ui.addAttribute("routepoints", routePoints);

        if (routes==null) routes = new ArrayList<>();
        if (routes.size()==0) routes.add(new Route(null,null,0));
        ui.addAttribute("routes", routes);

        return true;
    }

    @RequestMapping(value = "/adminaccount", method = RequestMethod.GET)
    public String adminMainPageGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        setAdminPageParams(ui);
        return "/admin/adminaccount";
    }

    @RequestMapping(value = "/adminaccount/{id}", method = RequestMethod.POST)
    public String adminMainPagePost(@PathVariable("id") int id, AdminDTOImpl adminDTO, BindingResult bindingResult, Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        if (id == 1){
            ui.addAttribute("addActionSuccess", "Add new user chosen");
            return "/manager/manageractionsuccess";
        }
        if (id == 2){
            ui.addAttribute("addActionSuccess", "Edit user chosen");
            return "/manager/manageractionsuccess";
        }
        if (id == 3){

            boolean success = adminService.deleteUserDriverFromDatabase(adminDTO);
            if (success) {
                ui.addAttribute("adminActionSuccess", "User removed successfully!");
                return  "/admin/adminactionsuccess";
            }
            else {
                ui.addAttribute("errorMessage", "User remove failed");
                return "/error/errorpage";
            }
        }

        if (id == 4){
            //Order operations
        }
        if (id == 5){
            //Cargo operations
        }
        if (id == 6){
            // Truck operations
        }
        if (id == 7){
            //Cities operations
        }
        if (id == 8){
            //Routepoints operations
        }
        if (id == 9){
            // Routes operations
        }
        if (id == 10){
            //Cargo operations
        }
        if (id == 11){
            // Truck operations
        }
        if (id == 12){
            //Cities operations
        }
        if (id == 13){
            //Routepoints operations
        }
        if (id == 14){
            // Routes operations
        }
        if (id == 15){
            //Cargo operations
        }
        if (id == 16){
            // Truck operations
        }
        if (id == 17){
            //Cities operations
        }
        if (id == 18){
            boolean success = adminService.deleteTruckFromDatabase(adminDTO);
            if (success){
                ui.addAttribute("adminActionSuccess", "Truck removed successfully!");
                return  "/admin/adminactionsuccess";
            }
            else {
                ui.addAttribute("errorMessage", "Truck remove failed");
                return "/error/errorpage";
            }
        }
        if (id == 19){
            // Routes operations
        }

        ui.addAttribute("errorMessage", "Can't make action!");
        return "/error/errorpage";
    }


    @RequestMapping(value = "/adminaddtruck", method = RequestMethod.GET)
    public String adminAddTruckGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        boolean setUpSuccess = setTruckPageParams(ui);
        if (!setUpSuccess){
            ui.addAttribute("errorMessage", "Error whole adding truck!");
            return "/error/errorpage";
        }
        return "/admin/adminaddtruck";
    }

    @RequestMapping(value = "/adminedittruck", method = RequestMethod.GET)
    public String adminEditTruckGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        boolean setUpSuccess = setTruckPageParams(ui);
        if (!setUpSuccess){
            ui.addAttribute("errorMessage", "Error whole adding truck!");
            return "/error/errorpage";
        }
        return "/admin/adminedittruck";
    }

    @RequestMapping(value = "/adminactionsuccess", method = RequestMethod.GET)
    public String adminActionSuccessGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        ui.addAttribute("adminActionSuccess", "Success!");
        return "/admin/adminactionsuccess";
    }

    @RequestMapping(value = "/adminadduserdriver", method = RequestMethod.GET)
    public String adminAddUserDriverGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        setUserDriverPageParams(ui);
        return "/admin/adminadduserdriver";
    }

    @RequestMapping(value = "/adminedituserdriver", method = RequestMethod.GET)
    public String adminEditUserDriverGet(Model ui){
        boolean accessGranted = isAccessGranted(ui);
        if (!accessGranted) return "/error/errorpage";
        setUserDriverPageParams(ui);
        return "/admin/adminedituserdriver";
    }

}
