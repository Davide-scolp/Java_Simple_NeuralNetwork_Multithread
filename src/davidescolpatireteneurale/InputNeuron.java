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
public class InputNeuron extends Thread{
    private Network myNetwork;
    private Random rnd;
    private double risultato;
    public InputNeuron(Network n,String nome){
        super(nome);
        this.myNetwork = n;
        this.rnd = new Random();
    }
    
    @Override
    public void run(){
        for (int i = 0; i < 10; i++){
            int timeToSleep = this.rnd.nextInt(291)+10;
            try{
                Thread.sleep(timeToSleep);
            }catch(InterruptedException e){
                System.out.println(e);
            }
            double dato = this.rnd.nextDouble(3)-1;
            this.risultato = Math.max(0,dato);
            this.myNetwork.forwardData(this,risultato);
        }
    }
    
    public Double getRisultato(){
        return this.risultato;
    }
}
