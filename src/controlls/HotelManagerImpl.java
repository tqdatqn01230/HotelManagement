/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.Hotel;

public class HotelManagerImpl implements HotelManager {

    Map<String, Hotel> hotelMap = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    @Override
    public void add() {
        int getchoice;

        do {

            String ID;
            do {
                ID = Utilities.getUserInputString("^H\\d{2}$", "Enter hotel's Id (format: Hxx) : ", "Hotel's ID must follow format Hxx (0 <= x <=9) !", null);
                if (hotelMap.containsKey(ID)) {
                    System.out.println("Hotel's ID cannot be duplicate. Please enter another one !");
                } else {
                    break;
                }
            } while (true);
            String name = Utilities.getString("Enter Hotel's name: ");

            int available_room = Utilities.getInt("Enter available room (int): ", 0, 100000);
            String address = Utilities.getString("Enter Hotel's address: ");
            String phone = Utilities.getPhone("Enter Hotel's hotline(9 numbers): ");
            String rating = Utilities.getString("Enter Hotel's rating: ");
            Hotel hotel = new Hotel(ID, name, available_room, address, phone, rating);

            hotelMap = loadHotel();
            hotelMap.put(ID, hotel);

            saveHotel(hotelMap);

            getchoice = Utilities.getInt("Do you want to add more product ? (yes: 1 , no: 0)", 0, 2);
            if (getchoice == 0) {
                System.out.println("----------Back to main menu!!!-------------");
                break;
            }
        } while (true);
        for (Hotel object : hotelMap.values()) {
            System.out.println(object);
        }

    }

    @Override
    public void delete() {
        hotelMap = loadHotel();
        String ID;
        do {
            ID = Utilities.getUserInputString("^H\\d{2}$", "Enter hotel's Id (format: Hxx) : ", "Hotel's ID must follow format Hxx (0 <= x <=9) !", null);
            if (!hotelMap.containsKey(ID)) {
                System.out.println("Hotel 's ID does not exits !");
            } else {
                break;
            }
        } while (true);   // id phai ton tai moi nhan
        int getChoice = Utilities.getInt("Do you want to delete this hotel? (Yes:1 , No:0)", 0, 2);
        if (getChoice == 0) {
            System.out.println("Delete failed!!!");

        }

        hotelMap.remove(ID);
        saveHotel(hotelMap);
        System.out.println("Delete success!!!");

    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Hotel's id_Hotel:");
        String hotelId = scanner.nextLine();

        Hotel hotelToUpdate = findHotelById(hotelId);
        if (hotelToUpdate == null) {
            System.out.println("Hotel does not exist");
            return;
        }

        System.out.println("Enter new information for the hotel (press Enter to keep the old information):");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("New hotel name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            hotelToUpdate.setName(newName);
        }

        System.out.print("Available Rooms: ");
        String newAvailableRoomsStr = scanner.nextLine();
        if (!newAvailableRoomsStr.isEmpty()) {
            try {
                int newAvailableRooms = Integer.parseInt(newAvailableRoomsStr);
                hotelToUpdate.setAvailable_room(newAvailableRooms);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for available rooms. Please enter a valid number.");
            }
        }

        System.out.print("New Address: ");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) {
            hotelToUpdate.setAddress(newAddress);
        }

        System.out.print("New Phone: ");
        String newPhone = scanner.nextLine();
        if (!newPhone.isEmpty()) {
            if (newPhone.matches("\\d{9}")) {
                hotelToUpdate.setPhone(newPhone);
            } else {
                System.out.println("Invalid phone number. Please enter exactly 9 digits.");

            }
        }

        System.out.print("New Rating: ");
        String newRating = scanner.nextLine();
        if (!newRating.isEmpty()) {
            hotelToUpdate.setRating(newRating);
        }
        hotelMap = loadHotel();
        hotelMap.remove(hotelToUpdate.getId());
        hotelMap.put(hotelToUpdate.getId(), hotelToUpdate);
        saveHotel(hotelMap);
        System.out.println("Hotel information updated successfully:");
        System.out.println(hotelToUpdate);

    }

    private Hotel findHotelById(String id) {
        return hotelMap.values().stream()
                .filter(hotel -> hotel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void checkExist(String id) {

        if (hotelMap.containsKey(id)) {
            System.out.println("Exist Hotel");
        } else {
            System.out.println("No Hotel Found!!");
        }
    }

    @Override
    public void searchByName(String name) {
        List<Hotel> searchedHotels = hotelMap.values()
                .stream()
                .filter(hotel -> hotel.getName().toLowerCase().contains(name.toLowerCase().trim()))
                .collect(Collectors.toList());
        if (searchedHotels.isEmpty()) {
            System.out.println("No Hotel Found!!");
        } else {
            showInfor(searchedHotels);
        }
    }

    @Override
    public void searchById(String id) {
        Hotel hotel = hotelMap.get(id);
        if (hotel != null) {
            showInfor(Collections.singletonList(hotel));
        } else {
            System.out.println("Not found hotel!");
        }
    }

    @Override
    public void showInfor(List<Hotel> hotels) {

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        System.out.println("#######################################################################################################################");
        System.out.println("#Hotel_ID  #Hotel_Name         #Hotel_Room_Available   #Hotel_Address        #Hotel_Phone          #Hotel Rating      # ");
        System.out.println("#######################################################################################################################");

        for (Hotel hotel : hotels) {
            String id = String.format("%-10s", hotel.getId());
            String name = String.format("%-18s", hotel.getName());
            String room = String.format("%-22s", hotel.getAvailable_room());
            String address = String.format("%-20s", hotel.getAddress());
            String phone = String.format("%-20s", hotel.getPhone());
            String rating = String.format("%-17s", hotel.getRating());

            System.out.println("#" + id + "# " + name + "# " + room + "# " + address + "# " + phone + "# " + rating + "# ");
        }

        System.out.println("#######################################################################################################################");
        System.out.println("#                                                                                               TOTAL: " + hotels.size() + " hotel type[s]#");
        System.out.println("#######################################################################################################################");

        System.out.println();
        System.out.println();

    }

    @Override
    public void sort() {
        hotelMap = loadHotel();
        List<Hotel> list = hotelMap.values().stream()
                .sorted((hotel1, hotel2) -> hotel2.getName().compareTo(hotel1.getName()))
                .collect(Collectors.toList());
        showInfor(list);
    }


    public void saveHotel(Map<String, Hotel> hotelMap) {
        ArrayList<Hotel> hotelList = new ArrayList<>(hotelMap.values());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Hotel.dat"));
            String line;
            for (Hotel hotel : hotelList) {
                line = hotel.toString() + "\n";
                bw.write(line);
            }
            bw.close();
            System.out.println("Saved Successfully!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Map<String, Hotel> loadHotel() {
        HashMap<String, Hotel> hotelMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Hotel.dat"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                String name = parts[1].trim();
                int room = Integer.parseInt(parts[2].trim());
                String address = parts[3].trim();
                String phone = parts[4].trim();
                String rating = parts[5].trim();
                Hotel hotel = new Hotel(id, name, room, address, phone, rating);
                hotelMap.put(id, hotel);

            }
            br.close();
        } catch (Exception e) {
        }
        return hotelMap;
    }

}
