package conexao;



public class PathBuilder {
	private String path;
	
	public PathBuilder(String path) {
		super();
		this.path = this.getClass().getClassLoader().getResource(path).toString();
		//this.path = this.getClass().getClassLoader().getResourceAsStream(path);
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
