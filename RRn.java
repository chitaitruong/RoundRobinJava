/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rrn;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
class Process {
    int bt;
    int arr;
    int total_wt;

    public Process() {
    }
    public Process(Process a) {
        this.arr=a.arr;
        this.bt=a.bt;
    }
    public Process(int bt, int arr) {
        this.bt = bt;
        this.arr = arr;
    }
}
public class RRn {
    
    /**
     * @param args the command line arguments
     */
    public static void updateQueue(Queue<Integer> readyQueue,Process arr[],int n,int t)
    {
        for (int i=0;i<n;i++)
        {
            if (arr[i].arr==t && arr[i].bt>0)
            {
                readyQueue.add(i);
            }
        }
    }
    public static void roundRobin(Process arr[],int n,int q,int cs)
    {
        String thuTu="";
        int t=arr[0].arr;
        Process re_arr[]=new Process[n];
        Queue<Integer> readyQueue=new LinkedList<>();
        for (int i=0;i<n;i++)
        {
            re_arr[i]=new Process(arr[i]);
        }
        while (true)
        {
            updateQueue(readyQueue,re_arr,n,t);
            if (readyQueue.isEmpty())
            {
                break;
            } else 
            {
                int tam=readyQueue.poll();
                if (re_arr[tam].bt>q)
                {
                    //thuc hien tien trinh
                    re_arr[tam].bt-=q;
                    re_arr[tam].total_wt+=(t-re_arr[tam].arr);
                    re_arr[tam].arr=t+q;
                    int ti=t+1;
                    t=t+q+cs;
                    thuTu+="p"+tam+"->";
                    for (;ti<t;ti++)
                    {
                        updateQueue(readyQueue,re_arr,n,ti);//nếu thời gian đến của tiến trình nào bằng với thời gian kết thúc của tiến trình đang 
                                                            //thực hiện mà vẫn còn burst time thì sẽ cho tiến trình còn burst time vào hàng đợi trước.
                    }
                } 
                else
                {
                    re_arr[tam].total_wt+=(t-re_arr[tam].arr);
                    re_arr[tam].arr=t+re_arr[tam].bt;
                    int ti=t+1;
                    t+=(re_arr[tam].bt+cs);
                    re_arr[tam].bt=0;
                    thuTu+="p"+tam+"->";
                    for (;ti<t;ti++)
                    {
                        updateQueue(readyQueue,re_arr,n,ti);
                    }
                }    
            }
        }
        System.out.println("STT     Thoi gian den       Burst time      Waiting time              Finish       Turn around time");
        float total_tat=0;
        float total_wt=0;
        for (int i=0;i<n;i++)
        {
            total_tat+=re_arr[i].total_wt+arr[i].bt;
            total_wt+=re_arr[i].total_wt;
            System.out.println(i+1+"\t\t"+arr[i].arr+"\t\t"+arr[i].bt+"\t\t"+re_arr[i].total_wt+"\t\t\t"+re_arr[i].arr+"\t\t"+(re_arr[i].total_wt+arr[i].bt));
        }
        System.out.println("Trung binh thoi gian cho: "+(total_wt)/n);
        System.out.println("Trung binh thoi gian quay vong: "+(total_tat)/n);
        System.out.print("Thu tu duyet: "+thuTu);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        int n;
        int q;//quantum time
        int cs;//context-switch time
        System.out.print("Nhap so luong tien trinh: ");
        n=sc.nextInt();
        System.out.print("Nhap quantum time: ");
        q=sc.nextInt();
        System.out.print("Nhap thoi gian chuyen ngu canh: ");
        cs=sc.nextInt();
        Process arr[]=new Process[n];
        for (int i=0;i<n;i++)
        {
            arr[i]=new Process();
            System.out.print("Nhap thoi gian den(>= tien trinh truoc) va burst time cho tien trinh "+i+":");
            arr[i].arr=sc.nextInt();
            arr[i].bt=sc.nextInt();
        }
        roundRobin(arr,n,q,cs);// quan trong
    }
    
}
