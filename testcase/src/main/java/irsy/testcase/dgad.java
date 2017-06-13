package irsy.testcase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class dgad {
	public static void main(String[] args) {
		int n=90;
		decompose(n);
	}

	 private static void decompose(int n){

	        System.out.print(n+"=");

	        for(int i=2;i<n+1;i++){

	            while(n%i==0 && n!=i){

	                n/=i;

	                System.out.print(i+"*");

	            }

	            if(n==i){

	                System.out.println(i);

	                break;

	            }

	        }

	    }
	
}
