package sortingProject;

public class Object 
{
	private int size;
	private String image_url;
	
	public Object(int size, String image_url)
	{
		this.size = size;
		this.image_url = image_url;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}
