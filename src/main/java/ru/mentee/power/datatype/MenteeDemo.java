package ru.mentee.power.datatype;

public class MenteeDemo {
    public static void main(String[] args) {
        Mentee mentee1 = new Mentee("John", "Wick", 30, 3.7f, 3);
        Mentee mentee2 = new Mentee("Albert", "Enstein", 50, 5f, 4);
        Mentee mentee3 = new Mentee("Lionel", "Messi", 39, 4.2f, 2);

        mentee1.displayInfo();
        System.out.println();
        mentee2.displayInfo();
        System.out.println();
        mentee3.displayInfo();
        System.out.println();

        System.out.println(mentee1.getFirstName()+" "+mentee1.getLastName()+" - "+
                (mentee1.isExcellent() ? "Отличник" : "Не отличник"));
        System.out.println(mentee2.getFirstName()+" "+mentee2.getLastName()+" - "+
                (mentee2.isExcellent() ? "Отличник" : "Не отличник"));
        System.out.println(mentee3.getFirstName()+" "+mentee3.getLastName()+" - "+
                (mentee3.isExcellent() ? "Отличник" : "Не отличник"));

        System.out.println();
        mentee3.advanceToNextLevel();
        mentee3.displayInfo();
    }
}
