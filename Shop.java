import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * @author Hussein Fuseini
 * @version 1.0.0.1
 * 
 */


public class Shop{
    
    // The vender class is a class for the vender object of the shop
    public static class  Vender{
        private String name;
        private String Gender;
        private int Age;

        public Vender(){
            name = "";
            Gender = "male";
            Age = 0;
        }
        public Vender(String name, String Gender, int Age){
            this.name = name;
            this.Gender = Gender;
            this.Age = Age;
        }
        public String getName(){
            return name;
        }
    }

    // The Item class is a class representing the item objects in the chop
    public static class Item {
        private String name;
        private double price;
        private int quantity;

        public Item(){
            name = "";
            price = 0;
            quantity = 0;
        }
        public Item(String name,double price,int quantity){
            this.name = name;
            this.price = price;
            this. quantity = quantity;
        }
        String getName(){
            return name;
        }
        double getPrice(){
            return price;
        }
        int getQuantity(){
            return quantity;
        }
    }

    private String shopName;
    private Vender vender;
    private ArrayList<Item> stock;
    
    public Shop(){
        shopName = "";
        vender = null;
        stock = new ArrayList<>();
    }

    // The constructor of the class shop
    public Shop(String name, Vender vender){
        shopName = name;
        this.vender = vender;
        stock = new ArrayList<>();
    }

    /**
     * 
     * @param item is the name of the item
     * 
     * @param quantity is the number of the items
     * @param price is the price of the item
     * @return is of type boolean
     */
    public boolean addItem(String item, int quantity,double price){
        return addStock(new Item(item,price,quantity));
    }
    // a private method which adds the object item to the ArrayList
    private boolean addStock(Item obj){
        if(stock.contains(obj)){
            stock.get(stock.indexOf(obj)).quantity+=obj.quantity;
            return true;
        }else
            return stock.add(obj);
    }
    
    // Sets a Vendor
    public Boolean setVender(Vender vender){
        if(this.vender == null){
            this.vender = vender;
            return true;
        }
        return false;
    }
    // return the vendor object
    public Vender getVender(){return vender;}
    
    // Saves the items in the shop in a file.
    public void recordStock(){
        // This method add the time and  date the record was editted
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(new FileOutputStream("essentials_stock.txt",true));
        }catch(FileNotFoundException e){
            e.getMessage();
        }
        
        
        for(Item e:stock){
            writer.printf("Product Name: %s, Quants: %d, Price: Ghs %f",e.name,e.quantity,e.price);
            writer.println();
            
        }
        writer.printf("\nEdited by %s at %s",vender.getName(),f.format(date));
        writer.println();
        writer.print("---------------------------------------------------------------------------\n");
        writer.close();
        backUpRecords();
    }
    
    // This method backups the infomation of the items in the shop to another file
    public void backUpRecords(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(new FileOutputStream("backup_essentials_stock.txt",true));
        }catch(FileNotFoundException e){
            e.getMessage();
        }
        
        for(Item e:stock){
            writer.printf("Product Name: %s, Quants: %d, Price: Ghs %f",e.name,e.quantity,e.price);
            writer.println();
            
        }
        writer.printf("\nEdited by %s at %s",vender.getName(),f.format(date));
        writer.println();
        writer.print("---------------------------------------------------------\n");
        writer.close();
    }
    // This is method prints the items in the shop onto the console
    public void printItems(){
        System.out.printf("Here are the list of items in %s",shopName);
        System.out.println();
        for(Item e:stock){
            System.out.printf("Product Name: %s, Quants: %d, Price: Ghs %f",e.name,e.quantity,e.price);
            System.out.println();
        }
    }
    // This method prints out the infomation on the file onto the console.
    public void loadItemsFromFile(){
        String filename = "essentials_stock.txt";
        try{
            Scanner scanIn = new Scanner(new BufferedReader(new FileReader(filename)));
            while(scanIn.hasNextLine()){
                System.out.println(scanIn.nextLine());
            }   
            scanIn.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    
    }
    public static void main(String[] args) {
        Shop Essentials = new Shop("Essentials Shop",new Vender("Fifi Ben","Male",32));
        
        Scanner input = new Scanner(System.in);
        String condition;
        do {
            System.err.println("Please enter the name, quantity and price of the item in the"+
            "\nformat <item> <quantity> <GHC price>");
            try{
                String info[] = input.nextLine().split(" ");
                Essentials.addItem(info[0], Integer.parseInt(info[1]), Double.parseDouble(info[3]));
            }catch(Exception e){
                System.out.println("Input Invalid \n Enter again.");
                String info[] = input.nextLine().split(" ");
                Essentials.addItem(info[0], Integer.parseInt(info[1]), Double.parseDouble(info[3]));
            }
            System.out.println("To Add another item, hit the Enter key else press 'N'key.");
            condition = input.nextLine();
        } while (condition.equals(""));

        System.out.println("--------------------------------------------------------------------");

        Essentials.recordStock();
        Essentials.loadItemsFromFile();
        
    }
}