package MyPractice;

public class LinearSearching {
	public static int[] numbersToSort = {1, 10,12,8, 15,5, 2, 0};
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		selectionSort(numbersToSort);
		while(true);
		
		

	}
	
	
	public static int[] selectionSort( int[] numberArray )
	{
		int i,j,temp;
		for(i=0;i<numberArray.length-1;i++)
		{
			for(j=i+1;j<numberArray.length;j++)
			{
				if(numberArray[i]>numberArray[j])
				{
					temp=numberArray[i];
					numberArray[i]=numberArray[j];
					numberArray[j]=temp;
				
				}
			}
		}
		return numberArray;
	}	
	

}