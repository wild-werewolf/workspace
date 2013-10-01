package ru.murzoid.controlled.bipedwalking.body;

import java.awt.Color;

public enum EnumFoot
{
	RIGHT(Color.RED),
	LEFT(Color.GREEN);
	Color color;
	private EnumFoot(Color color) {
		this.color=color;
	}
	
	public Color getColor(){
		return color;
	}
}
