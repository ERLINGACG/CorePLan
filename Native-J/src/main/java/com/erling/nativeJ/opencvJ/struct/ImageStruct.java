package com.erling.nativeJ.opencvJ.struct;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.Getter;

@Getter
@Structure.FieldOrder({"width", "height","channels", "size", "data"})
public class ImageStruct extends Structure {
    public int width;
    public int height;
    public int channels;
    public long size;
    public Pointer data;

    public byte[] getBytes() {
        return data.getByteArray(0L, (int) size);
    }
}
