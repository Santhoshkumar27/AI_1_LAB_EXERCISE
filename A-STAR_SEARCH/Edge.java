/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package A_star;

/**
 *
 * @author ganesh
 */
public class Edge 
{
    private double cost;
    private Node targetNode;
    
    public Edge (Node targetNode, double cost)
    {
        this.cost = cost;
        this.targetNode = targetNode;
    }
    
    public double getCost()
    {
        return cost;
    }
    
    public void setCost (double cost)
    {
        this.cost = cost;
    }
    
    public Node getTargetNode()
    {
        return targetNode;
    }
    
    public void setTargetNode(Node targetNode)
    {
        this.targetNode = targetNode;
    }
}
