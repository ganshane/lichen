package creeper.core.models;

public class CreeperDataBaseMigrationScript {
	
	private String packageName;
	
	private boolean searchSubPackages;
	
	public CreeperDataBaseMigrationScript(){};
	public CreeperDataBaseMigrationScript(String packageName,boolean searchSubPackages){
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
