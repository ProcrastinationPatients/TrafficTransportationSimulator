package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.utils.RandomUtils;


public class Vehicle {

	private int id ; 		  // ID
	private String color ;    //颜色
	private int Cur_Spd;      //当前速度
	private int Cur_Loc;      //当前位置
	private int Cur_line;     //当前线路
	private int MAX_Speed;    //最大速度

	private boolean isStop;
	private int goal_line;   //目标线路

	private Lane on_Link;    //当前所在的Lane

	public Vehicle(Lane lane){
		this.on_Link = lane ;
	}

	public void setSpeed(int Cur_Sped , int MAX_Speed){
		this.Cur_Spd = Cur_Sped ;
		this.MAX_Speed = MAX_Speed ;
	}

	//速度变化规则
	public int Speed_From_VDR() {
		if (this.Cur_Spd < this.MAX_Speed) {
			this.Cur_Spd++;
		}
		int safety_distance = on_Link.getSafetyDistanceByID(this.Cur_Loc);
		this.Cur_Spd = (safety_distance < this.Cur_Spd) ? (safety_distance) : (this.Cur_Spd);
		if(this.Cur_Spd < 0){
			this.Cur_Spd = 0 ;
		}
		return this.Cur_Spd;
	}

	//终于开始能动了啦
	public int move_Next_Location() {

		FunctionalObject fo = on_Link.getParent() ;
		if(fo instanceof Link){

			if (this.Cur_Spd + this.Cur_Loc >= on_Link.getLength()) {
				on_Link.changeToNextContainer(this);
				return 1 ;
			}
			Vehicle nextVehicle = on_Link.getNextVehicle(this);
			if(nextVehicle != null) {
				if (this.Cur_Spd > nextVehicle.getCur_Spd()) {
					if (this.Cur_line != 2 && this.Cur_line != 3) {
						fo.changeLine(this);
						return 2;
					}
				}
			}
//			if(this.Cur_Spd == 0){
//				fo.changeLine(this) ;
//			}

			on_Link.updateVehicle(this);
			return 3;
		}else if(fo instanceof Cross){
			if (this.Cur_Spd + this.Cur_Loc >= on_Link.getLength()) {
				on_Link.changeToNextContainer(this);
				this.updateGoalLine();
				this.checkRoad();
				return 1 ;
			}
			on_Link.updateVehicle(this);
			return 3 ;
		}

		return 3 ;
	}

	public void updateGoalLine(){
		this.setGoal_line(RandomUtils.getNewLine(this.Cur_line)); ;
	}

	public boolean isOnRoad(){
		if(this.Cur_line == this.goal_line)
			return true ;
		else
			return false ;
	}

	public void checkRoad(){
		if(!isOnRoad())
			on_Link.getParent().toGoalLine(this);
	}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public int getCur_Spd() { return Cur_Spd; }

	public void setCur_Spd(int cur_Spd) { Cur_Spd = cur_Spd; }

	public int getCur_Loc() { return Cur_Loc; }

	public void setCur_Loc(int cur_Loc) { Cur_Loc = cur_Loc; }

	public int getCur_line() { return Cur_line; }

	public void setCur_line(int cur_line) { Cur_line = cur_line; }

	public int getGoal_line() { return goal_line; }

	public void setGoal_line(int goal_line) { this.goal_line = goal_line; }

	public String getColor() { return color; }

	public void setColor(String color) { this.color = color; }

	public void setOn_Link(Lane lane){
		this.on_Link = lane ;
	}
	public Lane getOn_Link(){
		return this.on_Link ;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
}
