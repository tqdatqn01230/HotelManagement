package controlls;

import java.util.HashMap;
import java.util.List;

import models.Hotel;


 public interface HotelManager {

     void add();

     void delete();

     void update();

     void checkExist(String id);
     void searchByName(String s);
     void searchById(String id);

     void showInfor(List<Hotel> hotels);

     void sort(); 

}
