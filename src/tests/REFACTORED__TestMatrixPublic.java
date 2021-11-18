package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import code.Matrix;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class REFACTORED__TestMatrixPublic {
	
	String grid0 = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
	String grid1 = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
	String grid2 = "8,8;1;2,4;5,3;0,4,1,4,3,0,7,7,5,6;0,1,1,3;4,4,3,1,3,1,4,4,0,7,7,0,7,0,0,7;0,2,28,4,0,30,5,5,5";
	String grid3 = "6,6;2;2,4;2,2;0,4,1,4,3,0,4,2;0,1,1,3;4,4,3,1,3,1,4,4;0,0,94,1,2,38,4,1,76,4,0,80";
	String grid4 = "7,7;3;0,0;0,6;0,3,0,4,2,3,4,5,6,6,5,4;0,2,2,3,4,3;2,0,0,5,0,5,2,0;1,0,83,2,5,38,6,4,66,2,6,20";
	String grid5 = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
	String grid6 = "6,6;2;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3;0,2;1,3,4,2,4,2,1,3;0,0,2,0,4,2,4,0,2,4,4,98,1,1,98";
	String grid7 = "7,7;4;3,3;0,2;0,1,1,0,1,1,1,2,2,0,2,2,2,4,2,6,1,4;5,5,5,0;5,1,2,5,2,5,5,1;0,0,98,3,2,98,4,4,98,0,3,98,0,4,98,0,5,98,5,4,98";
	String grid8 = "7,7;4;5,3;2,5;0,0,0,2,0,6,2,3,5,1;1,1,2,6,6,0;4,0,1,6,1,6,4,0;0,4,33,1,4,1,4,3,11,5,4,2,5,5,69,3,1,95";
	String grid9 = "7,7;3;4,3;3,2;1,0,1,1,1,3,2,5,5,3,5,5;4,2,2,6,6,4,6,2,6,4,4,4;1,4,6,4,6,4,1,4,4,0,4,6,4,6,4,0,6,0,0,6,0,6,6,0;0,2,98,1,5,26,3,3,70,6,3,90,6,5,32";
	String grid10 = "6,6;1;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3,1,5;0,2;1,3,4,2,4,2,1,3;0,5,90,1,2,92,4,4,2,5,5,1,1,1,98";
	String grid11 = "9,9;2;8,0;3,5;0,1,0,3,1,0,1,1,1,2,0,7,1,8,3,8,6,1,6,5;0,6,2,8;8,1,4,5,4,5,8,1;0,0,95,0,2,98,0,8,94,2,5,13,2,6,39";

	
	@Test(timeout = 10000)
	public void testa0() throws Exception {
		String solution = Matrix.solve(grid0, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}

	@Test(timeout = 10000)
	public void testa1() throws Exception {
		String solution = Matrix.solve(grid1, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}

	@Test(timeout = 10000)
	public void testa2() throws Exception {
		String solution = Matrix.solve(grid2, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}

	@Test(timeout = 10000)
	public void testa3() throws Exception {
		String solution = Matrix.solve(grid3, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}


	@Test(timeout = 120000)
	public void testa4() throws Exception {
		String solution = Matrix.solve(grid4, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}

	@Test(timeout = 60000)
	public void testa5() throws Exception {
		String solution = Matrix.solve(grid5, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}

	@Test(timeout = 70000)
	public void testa6() throws Exception {
		String solution = Matrix.solve(grid6, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid6, solution));
	}

	@Test(timeout = 90000)
	public void testa7() throws Exception {
		String solution = Matrix.solve(grid7, "BF", false);
		assertTrue("The output actions do not lead to a goal state.", solution.equals("No Solution"));
	}

	@Test(timeout = 180000)
	public void testa8() throws Exception {
		String solution = Matrix.solve(grid8, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid8, solution));
	}

	@Test(timeout = 240000)
	public void testa9() throws Exception {
		String solution = Matrix.solve(grid9, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid9, solution));
	}

	@Test(timeout = 180000)
	public void testa10() throws Exception {
		String solution = Matrix.solve(grid10, "BF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
	}

	@Test(timeout = 10000)
	public void testb0() throws Exception {
		String solution = Matrix.solve(grid0, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 10000)
	public void testb1() throws Exception {
		String solution = Matrix.solve(grid1, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 10000)
	public void testb2() throws Exception {
		String solution = Matrix.solve(grid2, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 10000)
	public void testb3() throws Exception {
		String solution = Matrix.solve(grid3, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 60000)
	public void testb4() throws Exception {
		String solution = Matrix.solve(grid4, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 60000)
	public void testb5() throws Exception {
		String solution = Matrix.solve(grid5, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}
	
	@Test(timeout = 60000)
	public void testb6() throws Exception {
		String solution = Matrix.solve(grid6, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid6, solution));
	}
	
	@Test(timeout = 60000)
	public void testb7() throws Exception {
		String solution = Matrix.solve(grid7, "DF", false);
		assertTrue("The output actions do not lead to a goal state.", solution.equals("No Solution"));
	}
	
	@Test(timeout = 60000)
	public void testb8() throws Exception {
		String solution = Matrix.solve(grid8, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.",applyPlan(grid8, solution));
	}
	
	@Test(timeout = 60000)
	public void testb9() throws Exception {
		String solution = Matrix.solve(grid9, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid9, solution));
	}
	
	@Test(timeout = 60000)
	public void testb10() throws Exception {
		String solution = Matrix.solve(grid10, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
	}
	
	@Test(timeout = 60000)
	public void testb11() throws Exception {
		String solution = Matrix.solve(grid11, "DF", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid11, solution));
	}
	
	@Test(timeout = 120000)
	public void testc0() throws Exception {
		String solution = Matrix.solve(grid0, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 70000)
	public void testc1() throws Exception {
		String solution = Matrix.solve(grid1, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 70000)
	public void testc2() throws Exception {
		String solution = Matrix.solve(grid2, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 120000)
	public void testc3() throws Exception {
		String solution = Matrix.solve(grid3, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 500000)
	public void testc4() throws Exception {
		String solution = Matrix.solve(grid4, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 70000)
	public void testc5() throws Exception {
		String solution = Matrix.solve(grid5, "UC", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}
	
	
	@Test(timeout = 10000)
	public void testd0() throws Exception {
		String solution = Matrix.solve(grid0, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}

	
	@Test(timeout = 10000)
	public void testd1() throws Exception {
		String solution = Matrix.solve(grid1, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 10000)
	public void testd2() throws Exception {
		String solution = Matrix.solve(grid2, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 10000)
	public void testd3() throws Exception {
		String solution = Matrix.solve(grid3, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 120000)
	public void testd4() throws Exception {
		String solution = Matrix.solve(grid4, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 60000)
	public void testd5() throws Exception {
		String solution = Matrix.solve(grid5, "ID", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}
	

	@Test(timeout = 120000)
	public void teste0() throws Exception {
		String solution = Matrix.solve(grid0, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 70000)
	public void teste1() throws Exception {
		String solution = Matrix.solve(grid1, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 70000)
	public void teste2() throws Exception {
		String solution = Matrix.solve(grid2, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 70000)
	public void teste3() throws Exception {
		String solution = Matrix.solve(grid3, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	

	@Test(timeout = 400000)
	public void teste4() throws Exception {
		String solution = Matrix.solve(grid4, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 70000)
	public void teste5() throws Exception {
		String solution = Matrix.solve(grid5, "GR1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}
	
	
	@Test(timeout = 120000)
	public void testf0() throws Exception {
		String solution = Matrix.solve(grid0, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 70000)
	public void testf1() throws Exception {
		String solution = Matrix.solve(grid1, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 70000)
	public void testf2() throws Exception {
		String solution = Matrix.solve(grid2, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}

	@Test(timeout = 70000)
	public void testf3() throws Exception {
		String solution = Matrix.solve(grid3, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 400000)
	public void testf4() throws Exception {
		String solution = Matrix.solve(grid4, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 70000)
	public void testf5() throws Exception {
		String solution = Matrix.solve(grid5, "GR2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}

	@Test(timeout = 70000)
	public void testg0() throws Exception {
		String solution = Matrix.solve(grid0, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 70000)
	public void testg1() throws Exception {
		String solution = Matrix.solve(grid1, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 70000)
	public void testg2() throws Exception {
		String solution = Matrix.solve(grid2, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 70000)
	public void testg3() throws Exception {
		String solution = Matrix.solve(grid3, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 300000)
	public void testg4() throws Exception {
		String solution = Matrix.solve(grid4, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid4, solution));
	}
	
	@Test(timeout = 70000)
	public void testg5() throws Exception {
		String solution = Matrix.solve(grid5, "AS1", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}
	
	
	@Test(timeout = 70000)
	public void testh0() throws Exception {
		String solution = Matrix.solve(grid0, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid0, solution));
	}
	
	@Test(timeout = 70000)
	public void testh1() throws Exception {
		String solution = Matrix.solve(grid1, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid1, solution));
	}
	
	@Test(timeout = 70000)
	public void testh2() throws Exception {
		String solution = Matrix.solve(grid2, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid2, solution));
	}
	
	@Test(timeout = 70000)
	public void testh3() throws Exception {
		String solution = Matrix.solve(grid3, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid3, solution));
	}
	
	@Test(timeout = 70000)
	public void testh5() throws Exception {
		String solution = Matrix.solve(grid5, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid5, solution));
	}

	@Test(timeout = 1000000)
	public void testh7() throws Exception {
		String solution = Matrix.solve(grid7, "AS2", false);
		assertTrue("The output actions do not lead to a goal state.",  solution.equals("No Solution"));
	}
	
	@Test(timeout = 300000)
	public void testh10() throws Exception {
		String solution = Matrix.solve(grid10, "AS2", false);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
	}

	
	 static class TH {
			int m;
			int n;
			int telephoneX;
			int telephoneY;
			int neoX;
			int neoY;
			int capacity;
			int currentlyCarriedCount;
			int neoDamage;
			ArrayList<String> agents;
			ArrayList<String> mutated;
			ArrayList<String> pills;
			HashMap<String,String> pads;
			HashMap<String,Integer> hostages;
			HashMap<String,Integer> carried;
			ArrayList<String> deadLocations;
			int countKilled;
			boolean isNeoDead;

			public TH(int m, int n, int telephoneX, int telephoneY, int neoX, int neoY, int capacity,
					  ArrayList<String> agents, ArrayList<String> pills,
					  HashMap<String, String> pads, HashMap<String, Integer> hostages) {
				this.m = m;
				this.n = n;
				this.telephoneX = telephoneX;
				this.telephoneY = telephoneY;
				this.neoX = neoX;
				this.neoY = neoY;
				this.capacity = capacity;
				this.currentlyCarriedCount = 0;
				this.neoDamage = 0;
				this.agents = agents;
				this.mutated = new ArrayList<String>();
				this.pills = pills;
				this.pads = pads;
				this.hostages = hostages;
				this.carried = new HashMap<String,Integer>();
				this.deadLocations = new ArrayList<String>();
				this.countKilled = 0;
				this.isNeoDead = false;
			}

			public String formatXYstring(int x, int y) {
				return x+","+y;
			}
			
			public boolean isInsideGrid(int x, int y) {
				return x <0 || x>m || y<0 || y>n ? false: true;
			}
			
			public ArrayList<String> getValidNextLocation(int x, int y) {
				ArrayList<String> result = new ArrayList<String>();
				if(isInsideGrid(x-1,y))
					result.add((x-1)+","+y);
				if(isInsideGrid(x+1,y))
					result.add((x+1)+","+y);
				if(isInsideGrid(x,y-1))
					result.add(x+","+(y-1));
				if(isInsideGrid(x,y+1))
					result.add(x+","+(y+1));
				return result;
			}

			public boolean isAgenAndMutatedtFreeNextCycle(int x, int y) {
				String ln = formatXYstring(x,y);
				if(this.agents.contains(ln) || (this.hostages.containsKey(ln) && this.hostages.get(ln)>97))
					return false;
				return true;	
			}
			
			public boolean applyUp() {
				if(!isAgenAndMutatedtFreeNextCycle(neoX -1, neoY)) return false;
				if(neoX - 1 >= 0)
					neoX--;
				timeStepUpdates();
				return true;
			}
			
			public boolean applyDown() {
				if(!isAgenAndMutatedtFreeNextCycle(neoX +1, neoY)) return false;
				if(neoX + 1 < this.m)
					neoX++;
				timeStepUpdates();
				return true;
					
			}
			
			public boolean applyLeft() {
				if(!isAgenAndMutatedtFreeNextCycle(neoX, neoY -1)) return false;
				if(neoY - 1 >= 0)
					neoY--;
				timeStepUpdates();
				return true;

			}
			
			public boolean applyRight() {
				if(!isAgenAndMutatedtFreeNextCycle(neoX, neoY +1)) return false;
				if(neoY + 1 < this.n)
					neoY++;
				timeStepUpdates();
				return true;
			}
			
			public boolean applyKill() {
				ArrayList<String> locationsToKill = getValidNextLocation(neoX, neoY);
				if(locationsToKill.size()>0) {
					if(hostages.containsKey(formatXYstring(neoX, neoY)))
						if(hostages.get(formatXYstring(neoX, neoY))>97)
							return false;
					for(String led:locationsToKill) {
						if(agents.contains(led)) {
							agents.remove(led);
							this.countKilled++;
						}
						if(mutated.contains(led)) {
							mutated.remove(led);
							this.countKilled++;
						}
					}
					neoDamage +=20;
					if(neoDamage == 100)
						isNeoDead = true;
					
					timeStepUpdates();
					return true;
				}
				return false;
				
			}
			
			public boolean applyCarry() {
				String floyd = formatXYstring(neoX, neoY);
				if(currentlyCarriedCount <= capacity) {
					if(hostages.containsKey(floyd)) {
						currentlyCarriedCount++;
						carried.put(floyd, hostages.get(floyd));
						hostages.remove(floyd);
						timeStepUpdates();
						return true;
					}
				}
				
				return false;
			}
			
			public boolean applyDrop() {
				if(neoX == telephoneX && neoY == telephoneY && currentlyCarriedCount >0) {
					currentlyCarriedCount = 0;
					carried.clear();
					timeStepUpdates();
					return true;
				}
				return false;
			}
			
			public boolean applyFly() {
				String floyd = formatXYstring(neoX, neoY);
				if(this.pads.containsKey(floyd)) {
					String tofloyd = this.pads.get(floyd);
					String [] s = tofloyd.split(",");
					neoX = Integer.parseInt(s[0]);
					neoY = Integer.parseInt(s[1]);
					timeStepUpdates();
					return true;
				}
				return false;
				
			}
			
			public boolean applyTakePill() {
				if(this.pills.contains(formatXYstring(neoX, neoY))) {
					neoDamage = (neoDamage -20 <0) ? 0 : neoDamage -20;
					for(String abc: hostages.keySet()) {
						int beatles = hostages.get(abc)-20 <0? 0 : hostages.get(abc)-20;
						hostages.put(abc,beatles);
					}
					//TODO: the next part is added by Eslam for checking if the test cases would pass.
					for(String abc: carried.keySet()) {
						int beatles = carried.get(abc)<100?carried.get(abc)-20 <0? 0 : carried.get(abc)-20:100;
						carried.put(abc,beatles);
					}
					this.pills.remove(formatXYstring(neoX, neoY));
					return true;
				}
				return false;
				
			}
			
			public void timeStepUpdates() {
				
				HashMap<String,Integer> newHostages = new HashMap<String,Integer>();
				
				for(String abc: carried.keySet()) {
					int updatedDamage = carried.get(abc)+2;
					if(updatedDamage >= 100)  {
						this.assureInDeadLocations(abc);
						carried.put(abc,100);
					}
					else 
						carried.put(abc,updatedDamage);
				  }
				
				for(String abc: hostages.keySet()) {
					int updatedDamage = hostages.get(abc)+2;
					if(updatedDamage >= 100) {
						this.assureInDeadLocations(abc);
						mutated.add(abc);
					}
					else 
						newHostages.put(abc,updatedDamage);
				}
				this.hostages = newHostages;
			}
			
			public void assureInDeadLocations(String abc) {
				if(!this.deadLocations.contains(abc))
					this.deadLocations.add(abc);
			}
			
			public boolean goal() {
				return !this.isNeoDead && this.neoDamage <100 && this.hostages.size() == 0
						&& this.mutated.size() == 0 && this.neoX == this.telephoneX
						&& this.neoY == this.telephoneY;
			}
	 }

			
			public static boolean applyPlan(String grid, String solution) {
				String[] solutionArray  = solution.split(";");
				String plan = solutionArray[0];
				int countDeaths = Integer.parseInt(solutionArray[1]);
				int countKilled = Integer.parseInt(solutionArray[2]);
				
				plan.replace(" ", "");
				plan.replace("\n", "");
				plan.replace("\r", "");
				plan.replace("\n\r", "");
				plan.replace("\t", "");

				String[] actions = plan.split(",");
				
				String[] gridArray=  grid.split(";");
				String[] dimensions = gridArray[0].split(",");
				int m = Integer.parseInt(dimensions[0]);
				int n = Integer.parseInt(dimensions[1]);
				
				int capacity = Integer.parseInt(gridArray[1]);
				
				String[] neo = gridArray[2].split(",");
				int x00 = Integer.parseInt(neo[0]);
				int x01 = Integer.parseInt(neo[1]);
				
				String[] booth = gridArray[3].split(",");
				int x10 = Integer.parseInt(booth[0]);
				int x11 = Integer.parseInt(booth[1]);
				
				String[] ag = gridArray[4].split(",");
				ArrayList<String> xyz = new ArrayList<String>();
				for(int i = 0;i< ag.length -1; i+=2) {
					xyz.add(ag[i]+","+ag[i+1]);
				}
				
				String[] pl = gridArray[5].split(",");
				ArrayList<String> m4 = new ArrayList<String>();
				for(int i = 0;i< pl.length -1; i+=2) {
					m4.add(pl[i]+","+pl[i+1]);
				}

				String[] pas = gridArray[6].split(",");
				HashMap<String,String> m5 = new HashMap<String,String>();
				for(int i = 0;i< pas.length -3; i+=4) {
					m5.put(pas[i]+","+pas[i+1],pas[i+2]+","+pas[i+3]);
				}
				
				String[] hstg = gridArray[7].split(",");
				HashMap<String,Integer> m7 = new HashMap<String,Integer>();
				for(int i = 0;i< hstg.length -2; i+=3) {
					m7.put(hstg[i]+","+hstg[i+1],Integer.parseInt(hstg[i+2]));
				}
				
				TH s = new TH(m,n,x10,x11,x00, x01,capacity, xyz,m4,m5,m7);
				boolean linkin = true;
				
				for (int i = 0; i < actions.length; i++) {
				
					switch (actions[i]) {
					case "up":
						linkin = s.applyUp();
						break;
					case "down":
						linkin = s.applyDown();
						break;
					case "right":
						linkin = s.applyRight();
						break;
					case "left":
						linkin = s.applyLeft();
						break;
					case "carry":
						linkin = s.applyCarry();
						break;
					case "drop":
						linkin = s.applyDrop();
						break;
					case "fly":
						linkin = s.applyFly();
						break;
					case "takePill":
						linkin = s.applyTakePill();
						break;
					case "kill":
						linkin = s.applyKill();
						break;
					default: linkin = false; break;
								
					}

					if(!linkin)
						return false;		
					
						
						
				}
				return s.goal() && s.countKilled == countKilled && s.deadLocations.size() == countDeaths;
			}
}
