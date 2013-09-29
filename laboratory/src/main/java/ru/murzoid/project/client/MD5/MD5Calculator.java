/*
 * MD5Calculator.java
 *
 * Created on 27 ������� 2005 �., 21:24
 */

package ru.murzoid.project.client.MD5;


/**
 * ���� ����� ������������ ��� ������� MD5 ���������.
 * @author �������� ��������
 */
public class MD5Calculator {
    
    private MD5MessageReader reader = null;
    
    //MD5 �����
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 0;
    
    private long[] T;
    
    /** ������� ���������� ������ <code>MD5Calculator</code> */
    public MD5Calculator() {
        reader = new MD5MessageReader();
        calculateT();
    }
    
    /**
     * ��������� �������� MD5 ���������
     * @throws java.io.IOException ��� ������������� ������ ������ � ������
     * @return �������� MD5 ��������� � ���� ������ (��� <CODE>String</CODE>)
     */
    public String calculate(String pass) {
        
        initializeMDBuffer();

        int[] X = null;
        int AA, BB, CC, DD;
        X = reader.getStringToMD5(pass);
            AA = A;
            BB = B;
            CC = C;
            DD = D;
            
            A = FF(A, B, C, D, X[ 0],  7,  1);
            D = FF(D, A, B, C, X[ 1], 12,  2);
            C = FF(C, D, A, B, X[ 2], 17,  3);
            B = FF(B, C, D, A, X[ 3], 22,  4);
            A = FF(A, B, C, D, X[ 4],  7,  5);
            D = FF(D, A, B, C, X[ 5], 12,  6);
            C = FF(C, D, A, B, X[ 6], 17,  7);
            B = FF(B, C, D, A, X[ 7], 22,  8);
            A = FF(A, B, C, D, X[ 8],  7,  9);
            D = FF(D, A, B, C, X[ 9], 12, 10);
            C = FF(C, D, A, B, X[10], 17, 11);
            B = FF(B, C, D, A, X[11], 22, 12);
            A = FF(A, B, C, D, X[12],  7, 13);
            D = FF(D, A, B, C, X[13], 12, 14);
            C = FF(C, D, A, B, X[14], 17, 15);
            B = FF(B, C, D, A, X[15], 22, 16);

            A = GG(A, B, C, D, X[ 1],  5, 17);
            D = GG(D, A, B, C, X[ 6],  9, 18);
            C = GG(C, D, A, B, X[11], 14, 19);
            B = GG(B, C, D, A, X[ 0], 20, 20);
            A = GG(A, B, C, D, X[ 5],  5, 21);
            D = GG(D, A, B, C, X[10],  9, 22);
            C = GG(C, D, A, B, X[15], 14, 23);
            B = GG(B, C, D, A, X[ 4], 20, 24);
            A = GG(A, B, C, D, X[ 9],  5, 25);
            D = GG(D, A, B, C, X[14],  9, 26);
            C = GG(C, D, A, B, X[ 3], 14, 27);
            B = GG(B, C, D, A, X[ 8], 20, 28);
            A = GG(A, B, C, D, X[13],  5, 29);
            D = GG(D, A, B, C, X[ 2],  9, 30);
            C = GG(C, D, A, B, X[ 7], 14, 31);
            B = GG(B, C, D, A, X[12], 20, 32);
  
            A = HH(A, B, C, D, X[ 5],  4, 33);
            D = HH(D, A, B, C, X[ 8], 11, 34);
            C = HH(C, D, A, B, X[11], 16, 35);
            B = HH(B, C, D, A, X[14], 23, 36);
            A = HH(A, B, C, D, X[ 1],  4, 37);
            D = HH(D, A, B, C, X[ 4], 11, 38);
            C = HH(C, D, A, B, X[ 7], 16, 39);
            B = HH(B, C, D, A, X[10], 23, 40);
            A = HH(A, B, C, D, X[13],  4, 41);
            D = HH(D, A, B, C, X[ 0], 11, 42);
            C = HH(C, D, A, B, X[ 3], 16, 43);
            B = HH(B, C, D, A, X[ 6], 23, 44);
            A = HH(A, B, C, D, X[ 9],  4, 45);
            D = HH(D, A, B, C, X[12], 11, 46);
            C = HH(C, D, A, B, X[15], 16, 47);
            B = HH(B, C, D, A, X[ 2], 23, 48);
            
            A = II(A, B, C, D, X[ 0],  6, 49);
            D = II(D, A, B, C, X[ 7], 10, 50);
            C = II(C, D, A, B, X[14], 15, 51);
            B = II(B, C, D, A, X[ 5], 21, 52);
            A = II(A, B, C, D, X[12],  6, 53);
            D = II(D, A, B, C, X[ 3], 10, 54);
            C = II(C, D, A, B, X[10], 15, 55);
            B = II(B, C, D, A, X[ 1], 21, 56);
            A = II(A, B, C, D, X[ 8],  6, 57);
            D = II(D, A, B, C, X[15], 10, 58);
            C = II(C, D, A, B, X[ 6], 15, 59);
            B = II(B, C, D, A, X[13], 21, 60);
            A = II(A, B, C, D, X[ 4],  6, 61);
            D = II(D, A, B, C, X[11], 10, 62);
            C = II(C, D, A, B, X[ 2], 15, 63);
            B = II(B, C, D, A, X[ 9], 21, 64);

            A = A + AA;
            B = B + BB;
            C = C + CC;
            D = D + DD;
            
        A = MD5MessageReader.convertInt(A);
        B = MD5MessageReader.convertInt(B);
        C = MD5MessageReader.convertInt(C);
        D = MD5MessageReader.convertInt(D);
        String md5sum = new String(addZeros(Integer.toHexString(A), 8) +
                addZeros(Integer.toHexString(B), 8) +
                addZeros(Integer.toHexString(C), 8) +
                addZeros(Integer.toHexString(D), 8));
        return md5sum;
    }
    
    /*���������� ������� initializeMDBuffer, F, G, H, I, FF, GG, HH, II,
      calculateT ������� � RFC1321. ��� ������������ �� ������������� ������
      ������� MD5 �����.*/
    private void initializeMDBuffer() {
        A = B = C = D = 0;
        A = A | 0x67452301;
        B = B | 0xefcdab89;
        C = C | 0x98badcfe;
        D = D | 0x10325476;
    }
    
    private int F(int X, int Y, int Z) {
        return ((X&Y) | ((~X)&Z));
    }

    private int G(int X, int Y, int Z) {
        return ((X&Z) | (Y&(~Z)));
    }

    private int H(int X, int Y, int Z) {
        return (X^Y^Z);
    }

    private int I(int X, int Y, int Z) {
        return (Y^(X | (~Z)));
    }
    
    private int FF(int a, int b, int c, int d, int x, int s, int i) {
        int res;
        res = F(b, c, d);
        res = res + x;
        res = res + (int)T[i-1];
        res = res + a;
        res = rotateLeft(res, s);
        res = res + b;
        return res;
    }
    
    private int GG(int a, int b, int c, int d, int x, int s, int i) {
        int res;
        res = a + G(b, c, d) + x + (int)T[i-1];
        res = rotateLeft(res, s);
        return (res + b);
    }
    
    private int HH(int a, int b, int c, int d, int x, int s, int i) {
        int res;
        res = a + H(b, c, d) + x + (int)T[i-1];
        res = rotateLeft(res, s);
        return (res + b);
    }

    private int II(int a, int b, int c, int d, int x, int s, int i) {
        int res;
        res = a + I(b, c, d) + x + (int)T[i-1];
        res = rotateLeft(res, s);
        return (res + b);
    }

    /*��������� ����������� ����� ��������� ����� (val) ����� �� �����
      ���, ��������� � numb*/
    private int rotateLeft(int val, int numb) {
        return ((val << numb) | (val >>> (32 - numb)));
    }
    
    private void calculateT() {
        T = new long[64];
        for(int i = 0; i < T.length; i++)
            T[i] = (long)(4294967296l * Math.abs(Math.sin(i+1)));
    }
    
    /*���� ����� ��������� ���� ����� ������, ����� ���������� ����
     ����� ����� ������ length*/
    private String addZeros(String val, int length) {
        StringBuffer temp = new StringBuffer(val);
        while(temp.length() < length)
            temp.insert(0, "0");
        return temp.toString();
    }
}
