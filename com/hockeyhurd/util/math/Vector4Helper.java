package com.hockeyhurd.util.math;

public class Vector4Helper<N> {

	private N x, y, z;
	private int sideHit;
	
	public Vector4Helper() {
		this((N) (Number) 0, (N) (Number) 0, (N) (Number) 0, -1);
	}
	
	public Vector4Helper(N x, N y, N z) {
		this(x, y, z, -1);
	}
	
	public Vector4Helper(N x, N y, N z, int sideHit) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.sideHit = sideHit;
	}
	
	public boolean hasSideHit() {
		return this.sideHit > -1 ? true : false;
	}
	
	public void setX(N x) {
		this.x = x;
	}
	
	public void setY(N y) {
		this.y = y;
	}
	
	public void setZ(N z) {
		this.z = z;
	}
	
	public void setSideHit(int sideHit) {
		this.sideHit = sideHit;
	}
	
	public N getX() {
		return this.x;
	}
	
	public N getY() {
		return this.y;
	}
	
	public N getZ() {
		return this.z;
	}
	
	public int getSideHit() {
		return this.sideHit;
	}
	
	public Vector4Helper<N> add(Vector4Helper<N> vec, boolean changeSideHit) {
		float xx = ((Number)this.getX()).floatValue() + ((Number) vec.getX()).floatValue();
		float yy = ((Number)this.getY()).floatValue() + ((Number) vec.getY()).floatValue();
		float zz = ((Number)this.getZ()).floatValue() + ((Number) vec.getZ()).floatValue();
		
		setX((N) (Number) xx);
		setY((N) (Number) yy);
		setZ((N) (Number) zz);
		
		return this;
	}
	
	public Vector4Helper<N> subtract(Vector4Helper<N> vec, boolean changeSideHit) {
		float xx = ((Number)this.getX()).floatValue() - ((Number) vec.getX()).floatValue();
		float yy = ((Number)this.getY()).floatValue() - ((Number) vec.getY()).floatValue();
		float zz = ((Number)this.getZ()).floatValue() - ((Number) vec.getZ()).floatValue();
		
		setX((N) (Number) xx);
		setY((N) (Number) yy);
		setZ((N) (Number) zz);
		
		return this;
	}
	
	public Vector4Helper getVector4i() {
		int xx = ((Number)this.x).intValue();
		int yy = ((Number)this.y).intValue();
		int zz = ((Number)this.z).intValue();
		return new Vector4Helper<Integer>(xx, yy, zz);
	}
	
	public Vector4Helper copy() {
		return this;
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vector4Helper)) return false;
		Vector4Helper vec = (Vector4Helper) object;
		if (vec.getX() == this.getX() && vec.y == this.getY() && vec.getZ() == this.getZ()) return true;
		return false;
	}
	
}
