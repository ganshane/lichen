package creeper.core.models;

public class CreeperDbScript {
	
	private String packageName;
	
	private boolean searchSubPackages;
	
	public CreeperDbScript(){};
	public CreeperDbScript(String packageName,boolean searchSubPackages){
		this.packageName = packageName;
		this.searchSubPackages = searchSubPackages;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isSearchSubPackages() {
		return searchSubPackages;
	}
	public void setSearchSubPackages(boolean searchSubPackages) {
		this.searchSubPackages = searchSubPackages;
	};

}
