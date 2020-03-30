
public class Testing {

	public static void main(String[] args) {
		
		int [] arr = {1, 9, 9, 1, 5, 12, 12};
		
		int result = arr[0];
		
		for (int i = 1; i < arr.length; i++) {
			result = result ^ arr[i];
		}
		
		System.out.println(result);
	}

}
