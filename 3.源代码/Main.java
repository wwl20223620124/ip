/*
 *@Author：wwl
 *Date：2025年1月9日16:07:20
 * 打包时间：2025年1月29日20:52:08
 */
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        /* 初步计划
        1.先得到输入的字符(输入函数)
        2.把的到的字符串转换成int类型的数组()
        3.用32-子网掩码=反掩码位数
        4.写一个把一个255以内的整数转换成一个8位二进制字符串的方法 zhuanString
        5.分别把利用方法把 ip地址 子网掩码  反向掩码位数转换成 8位字符串
        6.利用if对比，（ip地址与子网掩码对比），得到网段得二进制
         (好像直接遍历ip地址的子网掩码位为1的即可)
         7.再写一个8位二进制数(String)转换成int的方法 zhuanInt
         8.写输出方法
         */
        do{
            zhuTi();
        }while (true);

    }
    public static void zhuTi(){
        //为了方便运行循环把主体放在这里
        String input =  A.input();//接收用户输入
//        input="192.168.1.1/24";
        int[] arr= A.chuShiHua(input);//192 168 1 1 30 (int)

        String[]  arr_=A.zhuanStringArr(arr);//转ip地址 int[](十进制) ->String[](二进制)8位
        //ip二进制
        String ip=A.pingJie(arr_);//把4条8位字符串，拼接成一条32位的长串

        //子网掩码
        String yanMa=A.yanMa(arr[4]);//通过输入掩码十进制数，得到二进制掩码

        //32-子网掩码数=反掩码
        String fanYanMa=A.fanYanMa(32-arr[4]);//通过输入反掩码十进制数，得到二进制反掩码

        //网段(二进制，好像所有String类型的都是二进制的)
        String wangDuan=A.wangDuan(ip,yanMa);//二进制网段

        //广播地址(二进制)
        String guangBoDiZhi=A.guangBoDiZhi(ip,yanMa);


        //输出
        System.out.println("\t\t========  二进制  ========");
        System.out.print("ip  地址：\t");
        A.shuChu(ip);
        System.out.print("子网掩码：\t");
        A.shuChu(yanMa);
        System.out.print("网络地址：\t");
        A.shuChu(wangDuan);
        System.out.print("广播地址：\t");
        A.shuChu(guangBoDiZhi);
        System.out.print("反向掩码：\t");
        A.shuChu(fanYanMa);
        System.out.println("\t\t========  十进制  ========");
        System.out.println("ip  地址：\t"+input);
        System.out.println("掩码位数：\t"+arr[4]+"位");
        //网络位全默认，主机位全0
        System.out.println("网络地址：\t"+A.zhuan(wangDuan));
        //除去网络地址和广播地址
        System.out.println("主机地址：\t"+A.zhuJiDiZhi(wangDuan,guangBoDiZhi));
        System.out.println("可用主机数：\t"+A.keYong(32-arr[4])+"个");
        //网络位全默认，主机位全1
        System.out.println("广播地址：\t"+A.zhuan(guangBoDiZhi));
        System.out.println("反掩码位数：\t"+(32-arr[4])+"位");
        System.out.println("反向掩码：\t"+A.zhuan(fanYanMa));
        System.out.println("OSPF宣告内容："+A.zhuan(wangDuan)+" \t"+A.zhuan(fanYanMa));
        System.out.println();
        System.out.print("输入 'y' 或 'Y' 继续运行程序，其他任意输入将结束程序：");
        Scanner scanner = new Scanner(System.in);
        String input_ = scanner.nextLine();

        if (input_.equalsIgnoreCase("y")) {
            System.out.println("继续运行程序...");
            // 在这里添加你想要执行的代码
        } else {
            System.out.println("程序结束。");
            System.exit(0); // 结束程序
        }

//        scanner.close();
    }
}
class A{
    //    1.先得到输入的字符(输入函数)
    public static String input(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入ip地址和子网掩码,例如：192.168.1.1/24");
        System.out.print("请输入：");
        String str = scanner.next();
        return str;
    }
    //    2.把的到的十进制字符串转换成int类型的数组 String -> String[]- >int []
    //  (String)(192.168.1.1/32) ->int[] 192 168 1 1 32
    public static int[] chuShiHua(String str){
        //先把把String拆成数组
        String[] arrString = new String[5];//存储分割的(字符串)Ip、子网掩码
        int[] arrInt = new int[arrString.length];//存储分割的(int)Ip、子网掩码
        char[] arrChar =str.toCharArray();//把传入的字符串分割成字符数组
        for(int i=0,j=0;i< arrString.length;i++){
            arrString[i]="";//置空
            for( ;j< arrChar.length;j++){
                if((arrChar[j]=='.')||(arrChar[j]=='/')){
                    j++;//跳过该符号
                    break;
                }
                arrString[i]+=arrChar[j];
            }
            arrInt[i]=Integer.parseInt(arrString[i]);//把String[] -> int[]
        }
        return arrInt;
    }
    //    3.用32-子网掩码=反掩码位数
//4.写一个把一个255以内的整数转换成一个8位二进制字符串的方法 zhuanString
    public static String zhuanString(int n){
        String str="";
        while (n>0){
            int a1=n%2;//取余2得到位数
            str=a1+str;//拼接在前面，int+String 等于String
            n/=2;//除2更新被除数
        }
        //补0，在前面补0，凑足8位
        while (str.length()<8){
            str="0"+str;
        }
        return str;
    }
    // 5.分别把利用方法把 ip地址 子网掩码  反向掩码位数转换成 8位字符串
    //5.1转ip地址 int[](十进制) ->String[](二进制)8位
    public static String[] zhuanStringArr(int[] arrInt){
        String[] arrString =new String[4];
        for(int i=0;i< arrString.length;i++){
            arrString[i]=zhuanString(arrInt[i]);
        }
        return arrString;
    }
    //把一个字符数组String [4]拼接成一个32位的字符串(不加空格)
    public static String pingJie(String[] arr){
        String str="";
        for(int i=0;i<arr.length;i++){
            str+=arr[i];
        }
        return str;
    }
    //把一个32位的字符串每8位拆解成一个子串，并且放到对应数组的位置，最后返回数组
    public static String[] fenGe(String str){
        String[] arr =new String[4];
        for(int i=0,q=0;i<4;i++){
            arr[i]="";//将字符串置空，防止出现null
            for(int j=0;j<8;j++,q++){
                arr[i]+=str.charAt(q);
            }
        }
        return arr;
    }
    //5.3转子网掩码 int(十进制) -String 32位(二进制)
    public static String yanMa(int n){
        String str="";
        for(int i=0;i<n;i++){
            str="1"+str;
        }
        while (str.length()<32){
            str=str+"0";
        }
        return str;
    }
    //5.4 反向子网掩码  int(十进制) ->String 32位(二进制)
    public static String fanYanMa(int n){
        String str="";
        for(int i=0;i<n;i++){
            str+="1";
        }
        while (str.length()<32){
            str="0"+str;
        }
        return str;
    }
    //     6.利用if对比，（ip地址与子网掩码对比），得到网段得二进制
//            (好像直接遍历ip地址的子网掩码位为1的即可)(我这里用了对比)
    public static String wangDuan(String ip,String yanMa){
        //直接逻辑与即可求出网段
        // 1&1=1 1&0=0  0&1=0 0&0=0

        char[] arr1 =ip.toCharArray();
        char[] arr2 =yanMa.toCharArray();
        String str ="";
        for(int i=0;i<32;i++){
            if(arr2[i]=='1'){
                str+=arr1[i];
            }else{
                str+="0";
            }
        }
        return str;
    }
    //6.1新功能计算广播地址guangBoDiZhi
    public static String guangBoDiZhi(String ip,String yanMa){
        char[] arr1 =ip.toCharArray();
        char[] arr2 =yanMa.toCharArray();
        String str=new String("");
        for(int i=0;i<32;i++){
            if(arr2[i]=='1'){
                str+=arr1[i];
            }else{
                str+="1";
            }
        }
        return str;
    }
    //还要计算出主机地址，就是中间那段，可以要如何实现
    //从 网络地址 +1 到 广播地址 -1，即
    //传入主机地址和网络地址（二进制），输出转换好的字符串
    public static String zhuJiDiZhi(String wangLuoDiZhi,String guangBoDiZhi){
        char[] arr1 =wangLuoDiZhi.toCharArray();
        char[] arr2 = guangBoDiZhi.toCharArray();
        //先实现网络地址加1
        for(int i=31;i>=0;i--){
            if(arr1[i]=='0'){
                arr1[i]='1';
                break;
            }
        }
        //再实现广播地址减1
        for(int i=31;i>=0;i--){
            if(arr2[i]=='1'){
                arr2[i]='0';
                break;
            }
        }
        String str1=new String(arr1);//网络地址（32位）
        String str2=new String(arr2);//广播地址（32位）

        String str =new String();//返回值
        str=A.zhuan(str1)+"~"+A.zhuan(str2);
        return str;
    }

    //7.再写一个8位二进制数(String)转换成int的方法 zhuanInt
    public static int zhuanInt(String str){
        int t=0;
        char[] arr =str.toCharArray();
        for(int i=0;i<8;i++){
            if(arr[i]=='1'){
                t+=Math.pow(2,7-i);
            }
        }
        return t;
    }
    //调用zhuanInt实现：输入一个32位(二进制)字符串，返回十进制字符串
    public static String zhuan(String str){
        String[] arr=fenGe(str);//先分割成4个子串
        String s="";
        for(int i=0;i<arr.length;i++){
            s+= Integer.toString(zhuanInt(arr[i]));
            //调用zhuannInt把8位二进制字符串，转成十进制int
            //再利用 Integer.toString()方法把int 转成String
            if(i!=(arr.length)-1){
                s+=".";//如果不是末尾就加一个点，用于分隔
            }
        }
        return s;
    }
    //给定一个String[] arr 输出它
    public static void print_(String arr[]){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    //输入一个32位的字符串，在其中调用fenGe()和print_输出
    public static void shuChu(String arr){

        print_(fenGe(arr));
    }

    //我想再加一个，可用主机数,需要传入反掩码
    public static int keYong(int n){
        //计算方法，2^主机位数-2，主机位数好像就是反掩码位数
        return ((int)(Math.pow(2,n))-2);
    }

}