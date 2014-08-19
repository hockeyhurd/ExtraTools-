package com.hockeyhurd.util.math;

public class Vector3iHelper {

	private int x, y, z, sideHit;
	
	public Vector3iHelper() {
		this(0, 0, 0, -1);
	}
	
	public Vector3iHelper(int x, int y, int z) {
		this(x, y, z, -1);
	}
	
	public Vector3iHelper(int x, int y, int z, int sideHit) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.sideHit = sideHit;
	}
	
	public boolean hasSideHit() {
		return this.sideHit > -1 ? true : false;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public void setSideHit(int sideHit) {
		this.sideHit = sideHit;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public int getSideHit() {
		return this.sideHit;
	}
	
	public Vector3iHelper add(Vector3iHelper vector) {
		return add(vector, false);
	}
	
	public Vector3iHelper add(Vector3iHelper vector, boolean changeSideHit) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		this.sideHit = changeSideHit ? vector.sideHit : this.sideHit;
		return this;
	}
	
	public Vector3iHelper subtract(Vector3iHelper vector) {
		return subtract(vector, false);
	}
	
	public Vector3iHelper subtract(Vector3iHelper vector, boolean changeSideHit) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		this.sideHit = changeSideHit ? vector.sideHit : this.sideHit;
		return this;
	}
	
	public Vector3iHelper getVector3I(Vector3iHelper vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.sideHit = vec.sideHit;
		return this;
	}
	
	public Vector3iHelper copy() {
		return this;
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vector3iHelper)) return false;
		Vector3iHelper vec = (Vector3iHelper) object;
		if (vec.getX() == this.getX() && vec.y == this.getY()) return true;
		return false;
	}
	
}
