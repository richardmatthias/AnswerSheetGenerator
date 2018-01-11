import java.io.*;
import java.util.Scanner;
public class SheetGen{
    public static String problemSignal="begin{problem}";
    public static String problemContSignal="[ (cont'd) ]";
    public static String problemZeroSignal="Question 0";
    public static String endSignal="end{problem}";
    public static String itemSignal="\\item";
    public static String construct="\\documentclass{article}\n" + 
			"\\usepackage[utf8]{inputenc}\n" + 
			"\\usepackage{amsmath}\n" + 
			"\\usepackage{amsfonts}\n" + 
			"\\usepackage{enumerate}\n" + 
			"\\usepackage{amssymb}\n"+
            "\\title{}\n" + 
			"\\author{}\n" + 
			"\n" + 
			"\\begin{document}\n" + 
			"\n" + 
			"\\maketitle\n"; 
    public static String enumerate="\\begin{enumerate}[(a)]\n";
    public static String itemizeEnd="end{problem}";
    public static String enumerateEnd="\\end{enumerate}\n";
    public static String documentEnd="\\end{document}\n";
    public static void main(String[] args) throws IOException{
        Scanner scanner=null;
        Scanner userScanner=new Scanner(System.in);
        BufferedWriter writer=null;
        try{
            System.out.println("Please input the source code file name:");
            String fileName=userScanner.nextLine();
            System.out.println("Please input the output source code file name:");
            String outputFileName=userScanner.nextLine();
            scanner=new Scanner(new BufferedReader(new FileReader(fileName)));
            writer=new BufferedWriter(new FileWriter(outputFileName));
            writer.write(construct);
            int questionCount=0;
            int subQuestionCount=0;
            String nextLine;
            String nextItem;
            while(scanner.hasNextLine()){
                nextLine=scanner.nextLine(); 
                if(nextLine.contains(problemSignal)){
                    //Increments the question counter and write it out unless the question is specified as zero 
                    writer.write("\\section{Question "+(nextLine.contains(problemZeroSignal)?questionCount:++questionCount)+"}\n");
                    subQuestionCount=0;
                    do{
                        nextItem=scanner.nextLine();
                        if(nextItem.contains(itemSignal)){
                            if(subQuestionCount==0)
                                writer.write(enumerate);
                            subQuestionCount++;
                            writer.write("\\item\n");
                        }
                    }while(!nextItem.contains(itemizeEnd));
                    if(subQuestionCount!=0)
                        writer.write(enumerateEnd);
                }
            }
            writer.write(documentEnd);
        }
        finally{
            if(scanner!=null){
                scanner.close();
            }
            if(userScanner!=null){
                userScanner.close();
            }
            if(writer!=null){
                writer.close();
            }
        }
    }
}
