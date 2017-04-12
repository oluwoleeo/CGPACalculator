public class CgpaCalcV3
 {
  int array1[];
  int array2[];
  int pNum, pDen;

  public CgpaCalcV3(int a1[], int a2[], int a, int b)
   {
    array1 = a1;
	array2 = a2;
	pNum = a;
	pDen = b;
   }
   
  public int calcDen()
   {
    int sum = 0;
	
	for (int x: array1)
	 sum += x;
	 
	return sum;
   }
   
  public int calcNum()
   {
    int sum = 0;
	
	for (int i = 0; i< array1.length; ++i)
	 sum += array1[i] * array2[i];
	 
	return sum;
   }
   
  public double calcSgpa()
   {
    double sgpa = (double)calcNum() / (double)calcDen();
	
	return sgpa;
   }
   
  public double calcCgpa()
   {
    double cgpa = (double)((pNum + calcNum()))/ (double)(pDen + calcDen());
	
	return cgpa;
   }
   
  public int getNum()
   {
    pNum+= calcNum();
	return pNum;
   }
   
  public int getDen()
   {
    pDen += calcDen();
	return pDen;
   }
 }