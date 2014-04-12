package com.am.bt.order;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import javax.persistence.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

public class ManageOrder {
   private static SessionFactory factory; 
 
   /* Method to CREATE an Order in the database */
   public Integer addOrder(String fname, String lname, int amount){
      Session session = factory.openSession();
      Transaction tx = null;
      Integer OrderID = null;
      try{
         tx = session.beginTransaction();
         Order order = new Order(fname, lname, amount);
         OrderID = (Integer) session.save(Order); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
      return OrderID;
   }
   /* Method to  READ all the Orders */
   public void listOrders( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List Orders = session.createQuery("FROM Order").list(); 
         for (Iterator iterator = 
                           Orders.iterator(); iterator.hasNext();){
            OrderHib order = (OrderHib) iterator.next(); 
          
         }
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to UPDATE salary for an Order */
   public void updateOrder(Integer OrderID, int salary ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Order order = 
                    (Order)session.get(Order.class, OrderID); 
         order.setSalary( salary );
		 session.update(order); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to DELETE an Order from the records */
   public void deleteOrder(Integer OrderID){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Order order = 
                   (Order)session.get(Order.class, OrderID); 
         session.delete(Order); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
}