/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controlls.I_Menu;
import controlls.*;
import controlls.Utilities;


public class main implements I_Menu {


    int choice;

    public static void main(String[] args) {
        main m = new main();
        m.showMenu();
    }

    @Override
    public void showMenu() {
        HotelManager hotel = new HotelManagerImpl();

        do {
            Menu menu = getMenu();

            choice = menu.getChoice();
            System.out.println("------------------------------");

            switch (choice) {
                case 1:
                    hotel.add();
                    break;

                case 2:
                    String id = Utilities.getUserInputString("^H\\d{2}$","Enter hotel's Id (format: Hxx) : ", "Hotel's ID must follow format Hxx (0 <= x <=9) !",  null);
                    hotel.checkExist(id);
                    break;
                case 3:
                    hotel.update();
                    break;

                case 4:
                    hotel.delete();
                    break;

                case 5:
                    int choice1;
                    do {
                        Menu menu1 = new Menu("5. Search ");
                        menu1.addNewOptiont("       5.1. Search by Hotel_id");
                        menu1.addNewOptiont("       5.2. Search by Hotel_name");
                        menu1.addNewOptiont("       5.3. Exit");
                        menu1.printMenu();

                        choice1 = menu1.getChoice();

                        switch (choice1) {
                            case 1:
                                String idSearch =Utilities.getUserInputString("^H\\d{2}$","Enter hotel's Id (format: Hxx) : ", "Hotel's ID must follow format Hxx (0 <= x <=9) !",  null);
                                hotel.searchById(idSearch);
                                break;

                            case 2:
                                String name = Utilities.getString("Enter hotel's Name  : ");
                                hotel.searchByName(name);
                                break;
                        }
                    } while (choice1 != 3);
                    break;

                case 6:
                    hotel.sort();
                    break;
            }
        } while (choice != 7);

    }

    private static Menu getMenu() {
        Menu menu = new Menu("<----------- MainMenu ------------>");
        menu.addNewOptiont("1. Add new hotel");
        menu.addNewOptiont("2. Check exist hotel by id");
        menu.addNewOptiont("3. Update hotel information");
        menu.addNewOptiont("4. Delete hotel");
        menu.addNewOptiont("5. Search hotel (by id or name)");
        menu.addNewOptiont("6. Display hotel list (descending by Hotel_Name).");
        menu.addNewOptiont("7. Exit.");
        menu.printMenu();
        return menu;
    }

}
