package com.erling.utilJ.image;


import com.sun.jna.ptr.IntByReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Data
@Value
@Getter
@Setter
public class ImageData<IntByReference> {
    IntByReference outWidth;
    IntByReference outHeight;
    IntByReference outSiz;
}
