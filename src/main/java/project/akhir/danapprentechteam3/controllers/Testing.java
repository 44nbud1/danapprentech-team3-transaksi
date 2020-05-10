package project.akhir.danapprentechteam3.controllers;

import java.util.Timer;
import java.util.TimerTask;

public class Testing {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new App(), 0, 1000);
    }
}

class App extends TimerTask {

    int countdown = 5;

    public void run() {


        while (countdown != 0)
        {
            countdown = countdown - 1;
            System.out.println(countdown);
        }
        countdown = 0;
        System.out.println(countdown);
    }

}