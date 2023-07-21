package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class main_misc {
	
	
	public static final int MAX = 1_000_000;
	
	public static void main(String[] args) {
		
	
	List<Integer> arr1 = new ArrayList<>(Arrays.asList(1,0,1,1,1,1,0,0,1));
	
	int[] v1 = letters_numbers(arr1);
	
	for(int i = 0;i < v1.length;i++){
		System.out.println(v1[i]);
	}
	
	System.out.println("------------------------");
	
	List<Integer> arr2 = new ArrayList<>(Arrays.asList(1,0,1,1,0,0,1,1));
	
	int[] v2 = letters_numbers(arr2);
	
	for(int i = 0;i < v2.length;i++){
		System.out.println(v2[i]);
	}
	
	
		
	}
	
	private static int[] ident_guess(List<Integer> arr){
		
		int[] guess = {-1,-1,0,0};//strt and end index,number of 1s and number of 0s
		
		//find if the number of 1s bigger than 0s or otherwise,the guess is the sub array that contains all the 
		//the 1s or 0s depending on wich one is in less number
		
		int num_0 = 0;
		int num_1 = 0;
		
		for(int i = 0;i < arr.size();i++){
			if(arr.get(i) == 1){
				num_1++;
			}
			else {
				num_0++;
			}
		}
		int x = 0;
		if(num_0 == num_1){
			return guess;
		}
		if(num_0 > num_1) {
			x = 1;
		}
		
		//finding the guess
		
		for(int i = 0;i < arr.size();i++) {
			
			int z = arr.get(i);
			if(guess[0] == -1 && z == x) {
				guess[0] = i;
			}
			if(z == x){
				guess[1] = i; 
			}
			
		}
		if(x == 1) {
			guess[2] = num_1;
			guess[3] = guess[1]-guess[0]+1-num_1;
		}
		else {
			guess[3] = num_0;
			guess[2] = guess[1]-guess[0]+1-num_0;
		}
		return guess;
	}
	
	private static int[] find_inner(List<Integer> arr,int[] v,int x) {
		
		/*Given that x is the element with the smallest quantity in arr,and strt and end are the bound of guess
		 * and there is less x´s than the other element is that sub array find the solution
		 */
		
		//find a sub array of the sub array bounder by strt and end with the property
		int[] sub_arr1 = {v[0],v[1]};
		int[] sub_arr2 = {v[0],v[1]};
		int num_1 = v[2];
		int num_0 = v[3];
		int a = num_1;
		int b = num_0;
		//testing with the end fixed
		int len1 = -1;
		for(int i = v[0];i < v[1];i++){
			if(num_1 == num_0) {
				//I want the bigger,so the earlier the loops stops the better
				sub_arr1[0] = i;
				len1 = sub_arr1[1] - i +1;
				break;
			}
			
			int z = arr.get(i);
			
			if(z == 1){
				num_1--;
			}
			if(z == 0) {
				num_0--;
			}
		}
		num_1 = a;
		num_0 = b;
		//now with the strt fixed
		int len2 = -1;
		for(int i = v[1];i > v[0];i--){
			
			if(num_1 == num_0){
				
				sub_arr2[1] = i;
				len2 = i - sub_arr2[0] +1;
				break;
			}
			
			int z = arr.get(i);
			
			if(z == 1){
				num_1--;
			}
			if(z == 0) {
				num_0--;
			}
			
		}
		
		if(len1 >= len2) {
			return sub_arr1;
		}
		return sub_arr2;
	}
	
	private static int[] find_outer(List<Integer> arr,int[] v,int x) {
		
		int[] sub_arr = {-1,-1};
		
		int size = arr.size();
		
		int diff = (int)Math.sqrt(Math.pow(v[2] - v[3],2));//absolute value
	
		//from end -->
		for(int i = v[1];i < arr.size();i++){
			
			if(arr.get(i) != x) {
				diff--;
				if(diff == 0){
					sub_arr[1] = i;
					sub_arr[0] = v[0];
					return sub_arr;
				}

			}
		}
		
		//from <-- strt
		for(int i = v[0];i > 0;i--){
			
			if(arr.get(i) != x) {
				diff--;
				if(diff == 0){
					sub_arr[0] = i;
					sub_arr[1] = v[1];
					return sub_arr;
				}

			}
			
		}
		return sub_arr;
	}
	
	private static int[] letters_numbers(List<Integer> arr){
		
		int sub_arr[] = {0,arr.size()-1};
		
		int[] v = ident_guess(arr);
		
		if(v[0] == -1) {
			//the original array is the answer
			return sub_arr;
		}
		if(v[2] == v[3]) {
			sub_arr[0] = v[0];
			sub_arr[1] = v[1];
			return v;
		}
		if(v[2] > v[3]) {
			//number_1 > number_0
			
			if(arr.get(v[0]) == 0){
				// x = 0 and
				return find_inner(arr,v,0);
			}
			else {
				return find_outer(arr,v,1);
			}
			
		}
		else {
			if(arr.get(v[0]) == 1) {
				return find_inner(arr,v,1);
			}
			else {
				return find_outer(arr,v,0);
			}
		}
		
		
	}


}
