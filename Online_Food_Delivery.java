import java.util.*;
import java.sql.*;
import java.io.*;   
public class Online_Food_Delivery
{
    Scanner sc=new Scanner(System.in);
    Connection Con;
    Online_Food_Delivery(Connection con)
    {
       Con=con;
    }
    void order()
    {
        sc.nextLine();
        System.out.println("Enter your name:");
        String name=sc.nextLine();

        System.out.println("Enter your mobile number:");
        long mo=sc.nextLong();
        int flag=0;
        int count=0;
        while(flag==0)
        {
          long temp=mo;
          while(temp!=0)
          {
            temp=temp/10;
            ++count;
          }
          if(count!=10)
          {
            System.out.println("Please enter 10 digit phone number:");
            mo=sc.nextLong();
            break;
          }
          else{
            flag=1;
          }
        }
        sc.nextLine();
        System.out.println("Please enter your address:");
        String add=sc.nextLine();

        int menu=0;
        do
        {
        System.out.println("Welcome to Food Fantasy restaurant");
        System.out.println("Select what you want to order");
        System.out.println("Dish                    Price");
        System.out.println("-------------------------------");
        System.out.println("1.Panner chilli         Rs.200.00/-");
        System.out.println("2.Frech Fries           Rs.100.00/-");
        System.out.println("3.Spicy Burger          Rs.220.00/-");
        System.out.println("4.Mexican BBQ           Rs.400.00/-");
        System.out.println("5.Classic Sushi         Rs.300.00/-");
        System.out.println("6.Oreo-Brownie Shake    Rs.150.00/-");
        System.out.println("7.Mint Mojito           Rs.150.00/-");
        System.out.println("8.Blueberry Cheesecake  Rs.180.00/-");
        System.out.println("9.Chocolate Pancake     Rs.200.00/-");
        System.out.println("10.Kulfi Delight        Rs.150.00/-");
        System.out.println("Enter 11 to exit");

        menu=sc.nextInt();
        switch(menu)
        {
          case 1:
          Order(name,mo,add,"Panner Chili",200.00);
          break;

          case 2:
          Order(name,mo,add,"Frech Fries",100.00);
          break;

          case 3:
          Order(name,mo,add,"Spicy Burger",220.00);
          break;

          case 4:
          Order(name,mo,add,"Mexican BBQ",400.00);
          break;

          case 5:
          Order(name,mo,add,"Classic Sushi",300.00);
          break;

          case 6:
          Order(name,mo,add,"Oreo-Brownie Shake",150.00);
          break;

          case 7:
          Order(name,mo,add,"Mint Mojito",150.00);
          break;

          case 8:
          Order(name,mo,add,"Blueberry Cheesecake",180.00);
          break;

          case 9:
          Order(name,mo,add,"Chocolate Pancake",200.00);
          break;

          case 10:
          Order(name,mo,add,"Kulfi Delight",150.00);
          break;

          case 11:
          break;

          default:
          System.out.println("Invalid choice.Enter again:");
        }
    }while(menu!=11);
    }
    void Order(String name,long mo,String add,String item,double price)
    {
        System.out.println("Enter "+item+"Quantity");
        int quantity=sc.nextInt();
         insertOrder(name,mo,add,item,price,quantity);
    }
    void insertOrder(String name, long mo, String add, String item, double price, int quantity)
    {
        String sql="Insert into customer(Name,Mobile_Number,Address,Item,Price,Quantity) values (?,?,?,?,?,?)";

        try(PreparedStatement preparedStatement = Con.prepareStatement(sql))
        {
        
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, mo);
            preparedStatement.setString(3, add);
            preparedStatement.setString(4, item);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, quantity);

            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) 
            {
                System.out.println("Order placed successfully.");
            } else
            {
                System.out.println("Error placing order.");
            }
        
        }catch(Exception e)
    {
        System.out.println(e);
    }
    }
    void displayOrder()
    {
        sc.nextLine();
        System.out.println("Enter your name to display your order:");
        String name1=sc.nextLine();
        
        String sql1="Select * from customer where Name=?";
        try (PreparedStatement ps= Con.prepareStatement(sql1)) {
            ps.setString(1, name1);

            try (ResultSet resultSet =ps.executeQuery()) {
                boolean hasOrders = false;
            
                while (resultSet.next()) 
                {
                    hasOrders = true;

                    String name = resultSet.getString("Name");
                    long mobile = resultSet.getLong("Mobile_Number");
                    String address = resultSet.getString("Address");
                    String dish = resultSet.getString("Item");
                    double price = resultSet.getDouble("Price");
                    int quantity = resultSet.getInt("Quantity");

                    System.out.println("--------------------");
                    System.out.println("Name: " + name);
                    System.out.println("Mobile: " + mobile);
                    System.out.println("Address: " + address);
                    System.out.println("Dish: " + dish);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("--------------------");
                }
                 if(!hasOrders) {
                    System.out.println("No order found with the given ID.");
                }
            }
        }catch (Exception e) 
        {
        System.out.println(e);
        } 
    }
    void billing()
    {
       sc.nextLine();
       System.out.println("Enter your name to get your bill:");
       String name2=sc.nextLine();
       String sql2="Select Name, Mobile_Number, Address, Item, Price, Quantity from customer where Name=?";
        try (PreparedStatement ps= Con.prepareStatement(sql2)) {
            ps.setString(1, name2);

            try (ResultSet resultSet =ps.executeQuery()) {
                while(resultSet.next()) {
                    
                    String name = resultSet.getString("Name");
                    long mobile = resultSet.getLong("Mobile_Number");
                    String address = resultSet.getString("Address");
                    String dish = resultSet.getString("Item");
                    double price = resultSet.getDouble("Price");
                    int quantity = resultSet.getInt("Quantity");

                    System.out.println("--------------------");
                    System.out.println("Name: " + name);
                    System.out.println("Mobile: " + mobile);
                    System.out.println("Address: " + address);
                    System.out.println("Dish: " + dish);
                    System.out.println("Price: " + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("--------------------");
                }
                  String sql3="Select Sum(Price) as tot from customer where Name=? ";
                  try (PreparedStatement prs= Con.prepareStatement(sql3)) {
                  prs.setString(1, name2);

                  try(ResultSet rs=prs.executeQuery())
                   {
                     if (rs.next()) 
                     {
                       double tot=rs.getDouble("tot");
                       double gst=0.1*tot;
                       double finalbill=tot+gst;
                       System.out.println("Total:    "+ tot);
                       System.out.println("Your final bill including 10% gst is:  "+finalbill);
                       System.out.println("-------------------------------------------------");
                       
                     }
                     else {
                         System.out.println("No such name is found");   
                     }
                  } catch (Exception e) {
                    System.out.println(e);
                  }
            }
        }catch (Exception e) 
        {
        System.out.println(e);
        } 
    }catch(Exception e)
    {
        System.out.println(e);
    }
}
void finalise()
{
    System.out.println("Do you want to order again?");
    System.out.println("Enter 1 if you want to order again");
    System.out.println("Enter 2 if you want to exit");
    int ch=sc.nextInt();
    if(ch==1)
    {
        order();
    }
    else if(ch==2)
    {
         sc.nextLine();
        System.out.println("Enter name:");
        String na=sc.nextLine();
        String  sql4="Delete from customer where Name=?";
        try 
                       ( PreparedStatement prs1= Con.prepareStatement(sql4)){
                       prs1.setString(1, na);
                       
                         int rowsAffected = prs1.executeUpdate();

            if (rowsAffected > 0) 
            {
                System.out.println("Ordered successfully.");
            } else
            {
                System.out.println("Error placing order.");
            }
            } catch (Exception e) {
                        System.out.println(e);
                       }
                        System.out.println("You will receive your order shortly.Enjoy your food.");
                       System.out.println("------------------------------------------------------"); 
                     
        return;
    }
   else
   {
    System.out.println("Invalid choice.Enter again");
    finalise();
   }
}
void update()
{
    sc.nextLine();
    System.out.println("Enter your name");
    String name11=sc.nextLine();
    
   
        sc.nextLine();
    System.out.println("Enter the dish name you want:");
    String Item=sc.nextLine();
    String  sql5="Update customer set Item=Item where Name=?";
        try 
                       ( PreparedStatement prs2= Con.prepareStatement(sql5)){
                       prs2.setString(1, name11);

                       int rows=prs2.executeUpdate();
                       if(rows>0)
                       {
                        System.out.println("Updated successfully");
                       }
                       else{
                        System.out.println("Error");
                       }
/*                         try(ResultSet rs=prs2.executeQuery())
                   {
                     if (rs.next()) 
                     {
                     String dish1 = rs.getString("Item");
                        
                     System.out.println("--------------------");
                    System.out.println("Dish: " + dish1);
                    System.out.println("--------------------");
                     }
                     else {
                         System.out.println("No such name is found");   
                     }
                  } catch (Exception e) {
                    System.out.println(e);
                  }
 */                      

    }catch(Exception e)
    {
        System.out.println(e);
    }
}
    
}

class main11
{
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");    
        Connection Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/food_deliver","root","");
        Online_Food_Delivery o1=new Online_Food_Delivery(Con);
        Scanner sc=new Scanner(System.in);
        while(true){
        System.out.println("Enter your choice:");
        System.out.println("1. Order Food");
        System.out.println("2. Display your Order list");
        System.out.println("3. Update your your order");
        System.out.println("4. Display the bill");
        System.out.println("5. Finalise the order");
        System.out.println("6. Exit");

        int choice=sc.nextInt();
        switch(choice)
        {
          case 1:
          o1.order();
          break;
          
          case 2:
          o1.displayOrder();
          break;
        
          case 3:
          o1.update();
          break;

          case 4:
          o1.billing();
          break;

          case 5:
          o1.finalise();
          break;

          case 6:
          System.out.println("Exiting...");
          System.exit(0);
          break;

          default:
          System.out.println("Invalid choice.Please enter again.");
          break;

        }
       } 
    }
}