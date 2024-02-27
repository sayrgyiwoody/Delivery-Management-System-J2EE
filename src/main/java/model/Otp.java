package model;

import java.io.Serializable;

public class Otp implements Serializable {
    private static final long serialVersionUID = 1L;
    
private int otp;
private String email;


public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public int getOtp() {
	return otp;
}
public void setOtp(int otp) {
	this.otp = otp;
}








}
