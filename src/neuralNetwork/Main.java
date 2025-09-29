/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package neuralNetwork;

/**
 *
 * @author Davide_Scolpati
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Network network = new Network();
        InputNeuron inputneurons[] = new InputNeuron[64];
        OutputNeuron outputneurons[] = new OutputNeuron[4];
        
        for (int i = 0; i < inputneurons.length; i++)
            inputneurons[i] = new InputNeuron(network,"InputNeuron_"+i);
        
        for (int i = 0; i < outputneurons.length; i++)
            outputneurons[i] = new OutputNeuron(network,"OutputNeuron"+i);
        
        for (int i = 0; i < outputneurons.length; i++)
            outputneurons[i].start();
        
        for (int i = 0; i < inputneurons.length; i++)
            inputneurons[i].start();
        
        try{
            for (int i = 0; i < inputneurons.length; i++)
                inputneurons[i].join();
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        for (int i = 0; i < outputneurons.length; i++)
            outputneurons[i].interrupt();
        
        try{
            for (int i = 0; i < outputneurons.length; i++)
                outputneurons[i].join();
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        System.out.println("FINE SIMULAZIONE.");
        
    }
    
}
