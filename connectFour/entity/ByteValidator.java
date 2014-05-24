/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectFour.entity;

import java.util.Random;
import java.util.Arrays;

public class ByteValidator
{
    private byte[] mybytes = new byte[1024];
    private final int hostbytes = 2;

    public ByteValidator(byte[] data)
    {
        mybytes = data;
    }

    public ByteValidator()
    {
        new Random().nextBytes(mybytes);
    }

    public void setHostPart(byte[] newhostpart)
    {
        for(int i=0; i<hostbytes;i++) {
            mybytes[i] = newhostpart[i];
        }
    }

    public byte[] getPacket()
    {
        return mybytes;
    }

    public byte[] getHostPart()
    {
        return getHostPart(mybytes);
    }

    public byte[] getHostPart(byte[] data)
    {
        byte[] retVal = new byte[hostbytes];

        for(int i=0; i<hostbytes; i++) {
            retVal[i] = data[i];
        }

        return retVal;
    }

    private void print()
    {
        for(int i=0;i<mybytes.length;i++) {
            System.out.print("["+mybytes[i]+"]");
        }
        System.out.println();
    }

    private byte[] getSum()
    {
        return getSum(mybytes);
    }

    private byte[] getSum(byte[] data)
    {
        byte[] retVal = new byte[data.length - hostbytes];

        for(int i=hostbytes;i<data.length;i++) {
            retVal[i-hostbytes] = data[i];
        }

        return retVal;
    }

    public boolean isFromTheSameHost(byte[] data)
    {
        return Arrays.equals(getHostPart(),getHostPart(data)); 
    }

    public boolean containsTheSameSum(byte[] data)
    {
        return Arrays.equals(getSum(),getSum(data));
    }
}

