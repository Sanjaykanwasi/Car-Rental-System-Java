import java.util.*;

// CAR CLASS
 class Car{

    private String carId;
    private String brand;
    private String model;
    private double price_per_day;
    private boolean is_Availabe;

    // Constructor creation
    public Car(String carId, String brand, String model, double price_per_day){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.price_per_day = price_per_day;
        this.is_Availabe = true;
    }


    // Methods
    public String getCarId(){
        return carId;
    }

    public String getbrand(){
        return brand;
    }

    public String getmodel(){
        return model;
    }

    public double calPrice(int rentalDays){
        return price_per_day * rentalDays;
    }

    public boolean isAvailable(){
        return is_Availabe;
    }

    public void rent(){
        is_Availabe = false;
    }

    public void returnCar(){
        is_Availabe = true;
    }
}

// CUSTOMER CLASS
class Customer{

    private String customerId;
    private String name;
    private String phonenumber;
    private String location;

    // Constructor
    public Customer(String customerId, String name, String phonenumber, String location){
        this.customerId = customerId;
        this.name = name;
        this.phonenumber = phonenumber;
        this.location = location;
    }

    // Methods
    public String getcustomerId(){
        return customerId;
    }
 
    public String getname(){
        return name;
    }
    
    public String getphonenumber(){
        return phonenumber;
    }
    
    public String getlocation(){
        return location;
    }
    
}

// RENTAL CLASS
class Rental{

   
    private Car car;
    private Customer customer;
    private int days;

    //Constructor
    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    //Methods
    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }
}

//CAR RENTAL SYSTEM CLASS
class Car_Rental_System{

    private List<Car> car_list;
    private List<Customer> customer_list;
    private List<Rental> rental_list;

    //Constructor
    public Car_Rental_System(List<Car> cars, List<Customer> customers,List<Rental> rentals){
        car_list = new ArrayList<>();
        customer_list = new ArrayList<>();
        rental_list = new ArrayList<>();
    }

    //Methods   
    public void addCar (Car car){
        car_list.add(car);
    }

    public void addCustomer(Customer customer){
        customer_list.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rental_list.add(new Rental(car, customer, days));
        }else{
            System.out.println("Sorry! This Car is not Available!!!");
        }
    }

    public void returnCar (Car car){
        Rental removerental = null;
        for(Rental rental : rental_list){
            if(rental.getCar() == car){
                removerental = rental;
                break;
            }
        }
        if(removerental != null){
            rental_list.remove(removerental);
           // System.out.println("Car is Succesfully Returned");
            car.returnCar();
        }else{
            System.out.println("Sorry! Car was not Rented");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            
            System.out.println();
            System.out.println();
            System.out.println("***WELCOME TO SANJAY'S CAR RENTAL SERVICE***");
            System.out.println("Press 1: Rent a Car");
            System.out.println("Press 2: Return a Car");
            System.out.println("Press 3: Exit");
            System.out.println();
            System.out.print("Enter Your Option: ");

            int choice = sc.nextInt();
            sc.nextLine();  //NEW LINE

            if(choice == 1){
                System.out.println("\n***Rent a Car***\n");
                
                System.out.print("Enter Your Good Name: ");
                String customerName = sc.nextLine();

                System.out.print("Enter your Mobile Numner: ");
                String customerNumber = sc.nextLine();

                System.out.print("Enter your Location: ");
                String customerLocation = sc.nextLine();

                System.out.println("\nList of Available Cars:");
                for(Car car: car_list){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId() + " - " + car.getbrand() + " " + car.getmodel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = sc.nextLine();
                System.out.println();

                System.out.print("Enter the number of days for rental: ");
                int rentaldays = sc.nextInt();
                sc.nextLine(); //Cosnumes New Line

                Customer newCustomer = new Customer("CUS" + (customer_list.size()+1), customerName, customerNumber, customerLocation);
                addCustomer(newCustomer); 
                
                Car selectedCar = null;
                for(Car car: car_list){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calPrice(rentaldays);
                    System.out.println("\n** Rental Information **\n");
                    System.out.println("Customer ID: " + newCustomer.getcustomerId());
                    System.out.println("Customer Name: " + newCustomer.getname());
                    System.out.println("Customer Numner: " + newCustomer.getphonenumber());
                    System.out.println("Customer Location: " + newCustomer.getlocation());
                    System.out.println("Car: " + selectedCar.getbrand() + " " + selectedCar.getmodel());
                    System.out.println("Rental Days: " + rentaldays);
                    System.out.printf("Total price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm Rental (YES/NO): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("YES")){
                        rentCar(selectedCar, newCustomer, rentaldays);
                        System.out.println("\nCONGRULACTION! Car Rented Successfully");
                    }else{
                        System.out.println("Sorry! Rental Cancelled");
                    }
                }else{
                    System.out.println("\nInvalid car selection or car not available");
                }

            } else if(choice == 2){
                System.out.println("\n** Return a Car **\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car: car_list){
                    if(car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }   
                    if(carToReturn != null){
                        Customer customer = null;
                        for(Rental rental : rental_list){
                            if(rental.getCar() == carToReturn){
                                customer = rental.getCustomer();
                                break;
                            }        
                        }     

                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by: " + customer.getname() + " - " + customer.getphonenumber() + "  - " + customer.getlocation());
                    }else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                    }else{
                        System.out.println("Invalid car ID or car is not rented");
                    }
                
            } else if(choice == 3){
                break;
            } else{
                System.out.println("Invalid Choice. Please enter a valid option");
            }
        }

        System.out.println("\nThank you for using our Service!");
    }
}

// MAIN CLASS
public class Main{
    public static void main(String[] args) {
        
        Car_Rental_System rental_System = new Car_Rental_System(null, null, null);

        Car car1 = new Car("C001", "BMW", "M4 Competition", 50);
        Car car2 = new Car("C002", "Porsche", "Panamera", 70);
        Car car3 = new Car("C003", "Audi", "R8 Sports", 60);
        Car car4 = new Car("C004", "Mercedes", "Benz", 80);
        Car car5 = new Car("C005", "Lamborghini", "Aventador", 90);
        Car car6 = new Car("C006", "Rolls-Royce", "Phantom", 100);
        rental_System.addCar(car1);
        rental_System.addCar(car2);
        rental_System.addCar(car3);
        rental_System.addCar(car4);
        rental_System.addCar(car5);
        rental_System.addCar(car6);

        rental_System.menu();
    }
}