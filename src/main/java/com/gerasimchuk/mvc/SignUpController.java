package com.gerasimchuk.mvc;


import com.gerasimchuk.dao.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.service.*;
import com.gerasimchuk.utils.LoginStateSaver;
import com.gerasimchuk.utils.LoginStateSaverImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.gerasimchuk.dao.CityDAOImpl.getCityDAOInstance;

@Controller
public class SignUpController {

    private TruckDAO truckDAO = TruckDAOImpl.getTruckDAOInstance();
    private TruckService truckService = new TruckServiceImpl();
    private OrderDAO orderDAO = OrderDAOImpl.getOrderDAOInstance();
    private UserDAO userDAO = UserDAOImpl.getUserDAOInstance();
    private UserService userService = new UserServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private CargoService cargoService = new CargoServiceImpl();
    private DriverService driverService = new DriverServiceImpl();

    private boolean setParamsForDriverAccountPage(Model ui, User user){
        User loggedUser = userDAO.getById(user.getId());
        if (loggedUser == null) return false;
        ui.addAttribute("driverID", loggedUser.getDriver().getId());
        ui.addAttribute("driverName", loggedUser.getUserName());
        ui.addAttribute("middleName", loggedUser.getMiddleName());
        ui.addAttribute("lastName", loggedUser.getLastName());
        ui.addAttribute("driverStatus", loggedUser.getDriver().getState());
        ui.addAttribute("currentCity", loggedUser.getDriver().getCurrentCity());
        if (loggedUser.getDriver().getCurrentTruck() == null) ui.addAttribute("currentTruck", new Truck("No truck",1,1, TruckState.NOTREADY, new City()));
        else ui.addAttribute("currentTruck", loggedUser.getDriver().getCurrentTruck());
        Order order = orderService.findCurrentOrder(loggedUser.getDriver());
        if (order == null) ui.addAttribute("currentOrder", new Order(OrderState.PREPARED,"No order", new Timestamp(0),null));
        else ui.addAttribute("currentOrder", order);
        List<City> cities = orderService.makeRoute(order);
        if (cities == null) cities=new ArrayList<>();
        if (cities.size() == 0) cities.add(new City("No route", CityHasAgency.HASNOT));
        ui.addAttribute("orderRoute", cities);
        List<User> assistants = orderService.getDriverAssistantsForCurrentOrder(order, loggedUser);
        if (assistants == null ) assistants = new ArrayList<>();
        if (assistants.size()==0) assistants.add(new User("No", "assistants", "found","0000", UserRole.DRIVER, new Driver()));
        ui.addAttribute("Assistants", assistants);
        List<Cargo> cargos = (ArrayList)cargoService.getCargosInCity(loggedUser.getDriver().getCurrentCity());
        if (cargos == null) cargos = new ArrayList<>();
        if (cargos.size() == 0) cargos.add(new Cargo("No cargos found",1,CargoStatus.PREPARED, new RoutePoint(),new RoutePoint()));
        ui.addAttribute("cargosInCurrentCity", cargos);
        return true;
    }

    @RequestMapping(value = "/changedrivers", method = RequestMethod.GET)
    public String changeDriversGet(Model ui){

        // cities list
        List<City> cities =  (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesListChange", cities);
        // trucks list
        List<Truck> trucks = (ArrayList)TruckDAOImpl.getTruckDAOInstance().getAll();
        ui.addAttribute("currentTrucksListChange", trucks);
        //return "/dark-login-form/23-dark-login-form/index";


        return "/manager/changedrivers";
    }

    @RequestMapping(value = "/changedrivers", method = RequestMethod.POST)
    public String changeDriversPost(DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";

        DriverService driverService = new DriverServiceImpl();
        boolean success = driverService.changeDriverInDatabase(driverDTO);
        if (success) {
            ui.addAttribute("addActionSuccess", "Driver updated successfully!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "Cannot update driver!");
            return "/error/errorpage";
        }

    }


    @RequestMapping(value = "/changetrucks", method = RequestMethod.GET)
    public String changeTruckGet(Model ui){


        List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();

        ui.addAttribute("citiesForChoose", cities);
        //return "/dark-login-form/23-dark-login-form/index";
        return "/manager/changetrucks";
    }


    @RequestMapping(value = "/changetrucks", method = RequestMethod.POST)
    public String changeTruckPost(TruckDTOImpl truck, BindingResult bindingResult, Model ui){

        boolean success = truckService.changeTruckInDatabase(truck);
        if (success) {
            ui.addAttribute("addActionSuccess", "Truck attributes successfully changed!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "failed to change truck attributes");
            return "/error/errorpage";
        }

        //return "/dark-login-form/23-dark-login-form/index";

    }

    @RequestMapping(value = "/manageraccount", method = RequestMethod.GET)
    public String managerAccount(Model ui){


        List<Order> orders = (ArrayList)orderDAO.getAll();
        ui.addAttribute("ordersList", orders);

        //return "/dark-login-form/23-dark-login-form/index";
        return "/manager/manageraccount";
    }

    @RequestMapping(value = "/manageractionsuccess", method = RequestMethod.GET)
    public String managerActionSuccess(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/manager/manageractionsuccess";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(){


        //return "/dark-login-form/23-dark-login-form/index";
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model ui){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {

                List<Order> orders = (ArrayList)orderDAO.getAll();
                ui.addAttribute("ordersList", orders);
                return "/manager/manageraccount";
            }
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {


                User user = LoginStateSaverImpl.getLoggedUser();
                Driver d = user.getDriver();
                setParamsForDriverAccountPage(ui,user);


                return "/driver/driveraccount";
            }
        }
        //return "/dark-login-form/23-dark-login-form/index";
        return "/login/login";
    }

    @RequestMapping(value = "/driveraccount", method = RequestMethod.GET)
    public String driverAccountGet(Model ui){
        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User signedUser = LoginStateSaverImpl.getLoggedUser();
               setParamsForDriverAccountPage(ui,signedUser);

                return "/driver/driveraccount";
            }
        }
        ui.addAttribute("errorMessage", "You must be driver to open this page");
        return "/error/errorpage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        if (!LoginStateSaverImpl.getInstance().isLoggedIn()) return "/index";

        LoginStateSaverImpl.setLoggedUser(null);
        //return "/dark-login-form/23-dark-login-form/index";
        return "/logout/logout";
    }



//    @RequestMapping(value = "/signupmain", method = RequestMethod.GET)
//    public String indexGetSignUp(){
//
//
//        //return "/dark-login-form/23-dark-login-form/index";
//        return "/signup/signupmanager";
//    }

    @RequestMapping(value = "/addcargoview", method = RequestMethod.GET)
    public String indexGetAddCargoView(){

        return "/cargos/addcargoview";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(UserDTOImpl user, BindingResult bindingResult, Model ui){

        if (LoginStateSaverImpl.getInstance().isLoggedIn()){
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.MANAGER) {
                List<Order> orders = (ArrayList)orderDAO.getAll();
                ui.addAttribute("ordersList", orders);
                return "/manager/manageraccount";
            }
            if (LoginStateSaverImpl.getLoggedUser().getRole() == UserRole.DRIVER) {
                User u = LoginStateSaverImpl.getLoggedUser();
               setParamsForDriverAccountPage(ui,u);
            }
        }
        SignInService s = new SignInServiceImpl(UserDAOImpl.getUserDAOInstance());
        User signedUser = s.signIn(user);
        LoginStateSaverImpl.setLoggedUser(signedUser);
        if (signedUser == null) return "/error/errorpage";
        if (signedUser.getRole() == UserRole.DRIVER) {
            User u = LoginStateSaverImpl.getLoggedUser();
            setParamsForDriverAccountPage(ui,u);
            return "/driver/driveraccount";
        }
        if (signedUser.getRole() == UserRole.MANAGER){
            List<Order> orders = (ArrayList)orderDAO.getAll();
            ui.addAttribute("ordersList", orders);
            return "/manager/manageraccount";
        }
        return "/error/errorpage";
    }


    @RequestMapping(value = "/managetrucks", method = RequestMethod.GET)
    public String manageTrucks(Model ui){

        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";


        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksList", trucks);

        List<City> cities = (ArrayList)getCityDAOInstance().getAll();
        ui.addAttribute("citiesList", cities);

        return "/manager/managetrucks";
    }


    @RequestMapping(value = "/managetrucks/{id}", method = RequestMethod.POST)
    public String manageTrucksPOST(@PathVariable("id") int id, TruckDTOImpl truck, BindingResult bindingResult, Model ui ){

        if (id == 0) {
            TruckService truckService = new TruckServiceImpl();
            boolean success = truckService.addTruckToDatabase(truck);

            if (success) {
                ui.addAttribute("addActionSuccess", "Truck successfully added!");
                return "/manager/manageractionsuccess";
            } else return "/error/errorpage";
        }
        if (id == 1){
            ui.addAttribute("changedTruckRegNum", truckDAO.getById(truck.getTruckIdVal()).getRegistrationNumber());
            List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
            ui.addAttribute("citiesForChoose", cities);
           return "/manager/changetrucks";
           // return "/manager/manageractionsuccess";
        }
        if (id == 2){
            ui.addAttribute("addActionSuccess", "Truck deleting!");
            return "/manager/manageractionsuccess";
        }

        return "/error/errorpage";
    }

    @RequestMapping(value = "/managedrivers", method = RequestMethod.GET)
    public String manageDrivers(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";


        List<User> drivers = new UserServiceImpl().getDrivers();
        ui.addAttribute("currentDriversList", drivers);

        List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesList", cities);

        List<Truck> trucks = (ArrayList)truckDAO.getAll();
        ui.addAttribute("currentTrucksList", trucks);

        return "/manager/managedrivers";
    }

    @RequestMapping(value = "/managedrivers/{id}", method = RequestMethod.POST)
    public String manageDriversPOST(@PathVariable("id") int id, DriverDTOImpl driverDTO, BindingResult bindingResult, Model ui ){

        if (id == 0) {

            DriverService driverService = new DriverServiceImpl();
            boolean success = driverService.addDriverToDatabase(driverDTO);

            if (success) return "/manager/addeddriversuccess";
            else return "/error/errorpage";
        }
        if (id == 1){
            // change driver
            List<City> cities =  (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
            ui.addAttribute("currentCitiesListChange", cities);
            // trucks list
            List<Truck> trucks = (ArrayList)TruckDAOImpl.getTruckDAOInstance().getAll();
            ui.addAttribute("currentTrucksListChange", trucks);
            //return "/dark-login-form/23-dark-login-form/index";

            ui.addAttribute("changedDriverId", driverDTO.getDriverId());
            return "/manager/changedrivers";
        }
        if (id == 2){
            // delete driver
            ui.addAttribute("addActionSuccess", "Not really deleted but success )))");
            return "/manager/manageractionsuccess";
        }
        return "/error/errorpage";
    }

    @RequestMapping(value = "/managecargos", method = RequestMethod.GET)
    public String manageCargos(Model ui){
        if (LoginStateSaverImpl.getLoggedUser() == null) return "/error/errorpage";
        if (LoginStateSaverImpl.getLoggedUser().getRole() != UserRole.MANAGER) return "/error/errorpage";

        List<Cargo> cargos = (ArrayList)CargoDAOImpl.getCargoDAOInstance().getAll();

        ui.addAttribute("currentCargosList", cargos);

        List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesList", cities);

        return "/manager/managecargos";
    }

    @RequestMapping(value = "/managecargos/{id}", method = RequestMethod.POST)
    public String manageCargosPOST(@PathVariable("id") int id,CargoDTOImpl cargoDTO, BindingResult bindingResult, Model ui ){

        if (id == 0) {

            CargoService cargoService = new CargoServiceImpl();
            boolean success = cargoService.addCargoToDatabase(cargoDTO);

            if (success) return "/manager/addedcargosuccess";
            else {
                ui.addAttribute("errorMessage", "Can't add cargo!");
                return "/error/errorpage";
            }
        }
        if (id == 1){
            ui.addAttribute("changedCargoId", cargoDTO.getCargoId());
            List<Cargo> cargos = (ArrayList)CargoDAOImpl.getCargoDAOInstance().getAll();
            ui.addAttribute("currentCargosList", cargos);
            List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
            ui.addAttribute("currentCitiesList", cities);
            return "/manager/changecargos";
        }
        if (id == 2){
            ui.addAttribute("addActionSuccess", "No actual delete but success!");
            return "/manager/manageractionsuccess";
        }
        else return "/error/errorpage";
    }

    @RequestMapping(value = "/changecargos", method = RequestMethod.GET)
    public String changeCargoGet(Model ui){

        List<Cargo> cargos = (ArrayList)CargoDAOImpl.getCargoDAOInstance().getAll();

        ui.addAttribute("currentCargosList", cargos);

        List<City> cities = (ArrayList)CityDAOImpl.getCityDAOInstance().getAll();
        ui.addAttribute("currentCitiesList", cities);
        return "/manager/changecargos";
    }

    @RequestMapping(value = "/changecargos", method = RequestMethod.POST)
    public String changeCargoPost(CargoDTOImpl cargoDTO,BindingResult bindingResult, Model ui){

        CargoService cargoService = new CargoServiceImpl();
        boolean success = cargoService.changeCargoInDatabase(cargoDTO);

        if (success){
            ui.addAttribute("addActionSuccess", "Cargo changed successfully!");
            return "/manager/manageractionsuccess";
        }
        else {
            ui.addAttribute("errorMessage", "Can not change cargo(");
            return "/error/errorpage";
        }
    }


    @RequestMapping(value = "/manageorders", method = RequestMethod.GET)
    public String manageOrders(Model ui){


       List<Cargo> cargos = (ArrayList)new CargoServiceImpl().getCargosWithoutAssignedOrder();
       ui.addAttribute("cargosWithoutAssignedOrders",cargos);

        return "/manager/manageorders";
    }



    @RequestMapping(value = "/manageorders", method = RequestMethod.POST)
    public String manageOrdersPost(OrderDTOImpl orderDTO, BindingResult bindingResult, Model ui){



        OrderService orderService = new OrderServiceImpl();
        orderService.addOrderToDatabase(orderDTO);
        ui.addAttribute("orderDescr", orderDTO.getDescription());

        List<Truck> truckList = (ArrayList)orderService.getTrucksFitsToOrder(orderDTO);

        ui.addAttribute("truckListFitsToOrder", truckList);
//        ui.addAttribute("orderDate", orderDTO.getDate());
//        ui.addAttribute("orderCargos", orderDTO.getCargos());


        return "/manager/addtrucktoorder";
    }

    @RequestMapping(value = "/addtrucktoorder", method = RequestMethod.GET )
    public String addTruckToOrder(){




        return "/manager/addtrucktoorder";
    }


    @RequestMapping(value = "/addtrucktoorder",method = RequestMethod.POST)
    public String addTruckToOrderPost(TruckToOrderDTOimpl truckToOrderDTO, BindingResult bindingResult, Model ui){

//        ui.addAttribute("descr", orderDTO.getDescription());
//        ui.addAttribute("date", orderDTO.getDate());
//        ui.addAttribute("truck", orderDTO.getAssignedTruck());
//        ui.addAttribute("cargos", orderDTO.getCargos());
//
//        int [] cargos = orderDTO.getCargos();
//        if (cargos.length == 0) System.out.println("zero");
//        for( int i = 0; i < cargos.length; i++){
//            System.out.println(cargos[i]);
//        }


        OrderService orderService = new OrderServiceImpl();
        boolean success = orderService.addTruckToOrder(truckToOrderDTO);

        if (!success) return "/error/errorpage";

        ui.addAttribute("chosenTruck", truckToOrderDTO.getTruckRegNum());
        ui.addAttribute("createdOrder", truckToOrderDTO.getOrderDescription());
//        List<User> drivers = userService.getDrivers();
       List<User> drivers = (ArrayList)((OrderServiceImpl) orderService).getDriversFitToTruckAndOrder(truckToOrderDTO);
        ui.addAttribute("driversList",drivers);

        return "/manager/adddriverstoorder";
    }


    @RequestMapping(value = "/adddriversoorder",method = RequestMethod.GET)
    public String addDriversToOrderGet(Model ui) {


        List<User> drivers = userService.getDrivers();
        ui.addAttribute("driversList",drivers);

        return "/manager/adddriverstoorder";
    }

        @RequestMapping(value = "/adddriverstoorder",method = RequestMethod.POST)
    public String addDriversToOrderPost(DriversToOrderDTOImpl driversToOrderDTO, BindingResult bindingResult, Model ui) {



        ui.addAttribute("addActionSuccess", "no actual drivers assign ut success");
        return "/manager/manageractionsuccess";
    }




    @RequestMapping(value = "/driveraccount/{id}", method = RequestMethod.POST)
    public String changeDriverStatePost(@PathVariable("id") int id, DriverStateDTOImpl driverStateDTO, BindingResult bindingResult,Model ui){
        if (id == 1){
            User u = LoginStateSaverImpl.getLoggedUser();
            driverService.updateDriverState(driverStateDTO,u.getDriver());
           // ui.addAttribute("addActionSuccess", "tmp: State refresh success");
           // return "/manager/manageractionsuccess";
            setParamsForDriverAccountPage(ui,u);
            ui.addAttribute("stateUpdateSuccess", "State update successfully");
        }
        if (id == 2){
            User u = LoginStateSaverImpl.getLoggedUser();
            orderService.updateCargosStateInOrder(driverStateDTO);
            setParamsForDriverAccountPage(ui,u);
//            ui.addAttribute("addActionSuccess", "tmp: Cargo state refresh success");
//            return "/manager/manageractionsuccess";
            ui.addAttribute("cargosStateUpdateSuccess", "Cargos state updated successfully");
        }
        if (id == 3){
            User u = LoginStateSaverImpl.getLoggedUser();
            Order order = orderService.findCurrentOrder(u.getDriver());
            boolean success = orderService.updateOrderState(order, driverStateDTO.getOrderStateVal());
            if (!success) {
                ui.addAttribute("errorMessage", "Can not update order status!");
                return "/error/errorpage";
            }
            ui.addAttribute("orderStateUpdateSuccess", "Order state updated successfully!");
        }

        return "/driver/driveraccount";
    };

}
