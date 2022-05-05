import java.util.*;

public class AndOrGraph {

	ArrayList<Node>	nodes = new ArrayList<Node>();		// AND/OR
	Node			current;							// 
	String			solution;							// 
	
	public void setRoot(String st){
		if(!nodes.isEmpty()){
			System.out.println("Delete the current graph");
			nodes.clear();
		}
		current = new Node(0, st, 0, 0);
		nodes.add(current);
	}
	
	public void setSolution(String st){
		solution = st;
	}
	
	// idx
	public void deleteNode(int idx){
		// 
		nodes.get(nodes.get(idx).getFrom()).removeTo(idx);
		nodes.remove(idx);
	}
	
	
	public void setNodeState(Node nd){
		if(nd.isUnknown()){
			if(nd.isNodeLeaf()){
				
				if(nd.getStr().equals(solution)){
					nd.setSolved();
					System.out.println("\"" + solution +"\" is found");
				}else{
					nd.setUnsolved();
				}
			}else if(nd.isNodeAND()){
				int i;
				
				for(i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isUnsolved()){
						nd.setUnsolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setSolved();
			}else if(nd.isNodeOR()){
				int i;
				
				for( i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isSolved()){
						nd.setSolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setUnsolved();
			}
		}
	}
	
	
	public void printNodes(){
		System.out.println("Print Nodes(current idx = " + current.getIdx() + ")");
		for(int i=0; i<nodes.size(); i++){
			nodes.get(i).printStatus();
		}
		System.out.println("End");
	}
	
	
	public void search(){
		
		while(nodes.get(0).isUnknown()){
			
			while(current.isUnknown()){
				try{
					printNodes();
					
					current = current.generateNextNode(nodes.size());
					System.out.println("Generated node: ");
					current.printStatus();
					System.out.println();
					nodes.add(current);
				}catch(RuntimeException e){
					System.out.println("Error");
				}
				
				setNodeState(current);
			}
			System.out.println("Return to parent node:" + current.getFrom());
			
			current = nodes.get(current.getFrom());
			setNodeState(current);
			
			
			/*
			if(current.isSolved()){
				current = nodes.get(current.getFrom());
				SetNodeState(current);
			}else{
				int idx = current.getIdx();
				current = nodes.get(current.getFrom());
				nodes.remove(idx);
				current.removeTo(idx);
				SetNodeState(current);
			}
			*/
		}
		System.out.println("End of search \ nSolution graph:");
		printNodes();
		
		if(!nodes.isEmpty() && nodes.get(0).isSolved()){
			System.out.println("Successful search \ nSolution graph nodes obtained");
		}else{
			System.out.println("Exploration failed");
		}		
	}
	
	// 解グラフから文字列を取得するメソッド
	public String getSolution(){
		String result = "";
		
		if(nodes.isEmpty() || !nodes.get(0).isSolved()){
			System.out.println("This graph is unsolved");
			return result;
		}
		
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).isNodeLeaf() && nodes.get(i).isSolved()){
				result += nodes.get(i).getStr();
			}
		}
		return result;
	}
}

