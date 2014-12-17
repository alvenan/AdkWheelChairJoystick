package br.edu.ufam.engcomp.wheelchair.utils;

public enum EnumDirection {
    UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT"), CENTER("CENTER");

    private String direction;

    private EnumDirection(String direction) {
	this.direction = direction;
    }
}
