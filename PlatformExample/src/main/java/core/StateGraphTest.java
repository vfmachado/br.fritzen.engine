package core;

import java.util.Scanner;

public class StateGraphTest {

	public static void main(String[] args) {
		
		
		/*
		
			 A --- E
		   / 
		I -
		   \ 
			 B
		
		I -> A
		I -> B
		
		A -> E
		A -> I
		
		E -> A
		
		B -> I
				
		 */
		
		StateNode initial = new StateNode("Node 0 - Iddle");
		StateNode midA = new StateNode("Node 1 - Middle A");
		StateNode midB = new StateNode("Node 2 - Middle B");
		StateNode end = new StateNode("Node 3 - End");
		
		initial.addPath(midA, "A");
		initial.addPath(midB, "B");
		
		midA.addPath(end, "E");
		midA.addPath(initial, "I");
		
		end.addPath(midA, "A");
		
		midB.addPath(initial, "I");
		
		
		StateGraph graph = new StateGraph();
		
		graph.setInitial(initial);
		
		Scanner in = new Scanner(System.in);
		
		do {
		
			System.out.println("\n\n " + graph.getCurrentNode());
			
			graph.update(10);
			
			System.out.println("Current available paths: " + graph.listPaths());
		
			System.out.print("Trigger (-1 to close)? ");
			
			String trigger = in.next();
			
			if (trigger.equals("-1")) break;
			
			graph.trigger(trigger);
		
		} while (true);
		
		in.close();
	}
	
}
