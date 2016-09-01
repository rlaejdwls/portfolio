package kr.ac.gachon.game.pingpong.model;

public class RankModel {
	private String rank;
	private String recMemId;
	private String recWins;
	private boolean isPlayer;
	
	public RankModel() {
		super();
	}
	public RankModel(String rank, String recMemId, String recWins,
			boolean isPlayer) {
		super();
		this.rank = rank;
		this.recMemId = recMemId;
		this.recWins = recWins;
		this.isPlayer = isPlayer;
	}

	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getRecMemId() {
		return recMemId;
	}
	public void setRecMemId(String recMemId) {
		this.recMemId = recMemId;
	}
	public String getRecWins() {
		return recWins;
	}
	public void setRecWins(String recWins) {
		this.recWins = recWins;
	}
	public boolean isPlayer() {
		return isPlayer;
	}
	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	
	@Override
	public String toString() {
		return "RankModel [rank=" + rank + ", recMemId=" + recMemId
				+ ", recWins=" + recWins + ", isPlayer=" + isPlayer + "]";
	}
}
