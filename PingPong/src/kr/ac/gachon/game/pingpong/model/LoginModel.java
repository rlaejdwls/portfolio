package kr.ac.gachon.game.pingpong.model;

public class LoginModel {
	private String memId;
	private String memPwd;
	
	public LoginModel(String memId, String memPwd) {
		super();
		this.memId = memId;
		this.memPwd = memPwd;
	}

	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	@Override
	public String toString() {
		return "LoginModel [memId=" + memId + ", memPwd=" + memPwd + "]";
	}
}
