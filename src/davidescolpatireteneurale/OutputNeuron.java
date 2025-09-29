/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidescolpatireteneurale;

import java.util.Random;

/**
 *
 * @author studente
 */
public class OutputNeuron extends Thread{
    private Network myNetwork;
    private Random rnd;
    
    public OutputNeuron(Network n, String nome){
        super(nome);
        this.myNetwork = n;
        this.rnd = new Random();
    }
    
    @Override
    public void run(){
        boolean isRunning = true;
        while(isRunning){
            try{
                this.myNetwork.getComputedAverage(this);
            }catch(InterruptedException e){
                System.out.println("Interrupt ricevuto, "+this.getName()+ " termina.");
                isRunning = false;
            }
        }
    }
}
