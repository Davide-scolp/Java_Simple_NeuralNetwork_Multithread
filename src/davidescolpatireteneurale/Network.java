/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidescolpatireteneurale;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author studente
 */
public class Network {
    private int forwardedData;
    private int processedOutputs;
    private LinkedList<InputNeuron> list;
    private long startTime;
    private Random rnd;
    
    private ReentrantLock lock;
    private Semaphore allDataArrived;
    private Condition attesaInputNeurons;
    
    public Network(){
        this.list = new LinkedList<>();
        this.startTime = System.currentTimeMillis();
        this.forwardedData = 0;
        this.processedOutputs = 0;
        this.rnd = new Random();
        this.lock = new ReentrantLock();
        this.allDataArrived = new Semaphore(0);
        this.attesaInputNeurons = this.lock.newCondition();
    }
    
    public void forwardData(InputNeuron in,double valore){
        System.out.println("["+getCurrentTime()+"]"+"L'"+in.getName()
                           +" invia un dato di valore: "+valore);
        this.lock.lock();
        try{
            this.list.add(in);
            this.forwardedData++;
            if(this.forwardedData == 64){
                System.out.println("["+getCurrentTime()+"]"+"L'"+in.getName()
                                   +" è l'ultimo ad inviare il"
                                   + " risultato, sveglio gli outputNeurons");
                this.allDataArrived.release(4);
                this.forwardedData = 0;
            }
            this.attesaInputNeurons.await();
        }catch(InterruptedException e){
            System.out.println(e);
        }
        finally{
            this.lock.unlock();
        }
    }
    
    public void getComputedAverage(OutputNeuron on) throws InterruptedException{
        double media = 0;
        double sommaTot = 0;
        double risultato = 0;
        int timeToSleep = this.rnd.nextInt(191)+10;
        InputNeuron current = null;
        this.allDataArrived.acquire();
        this.lock.lock();
        try{
            for (int i = 0; i < this.list.size(); i++){
                current = this.list.get(i);
                sommaTot += current.getRisultato();
            }
            
            media = sommaTot / this.list.size();
            
        }finally{
            this.lock.unlock();
        }
        
        try{
            Thread.sleep(timeToSleep);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        this.lock.lock();
        try{
            risultato = (1 / (1 + Math.exp(-media)));
            System.out.println("["+getCurrentTime()+"]"+on.getName()+" Risultato funzione sigmoidea: "+risultato);
            this.processedOutputs++;
                if (this.processedOutputs == 4){
                    System.out.println("["+getCurrentTime()+"]"+"L'"+on.getName()+" è l'ultimo a processare il risultato.");
                    this.list.removeAll(list);
                    this.processedOutputs = 0;
                    this.attesaInputNeurons.signalAll();
                }
        }finally{
            this.lock.unlock();
        }
    } 
    
    public long getCurrentTime(){
        return System.currentTimeMillis()-this.startTime;
    }
}
