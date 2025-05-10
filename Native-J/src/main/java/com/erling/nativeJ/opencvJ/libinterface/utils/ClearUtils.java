package com.erling.nativeJ.opencvJ.libinterface.utils;

import com.erling.nativeJ.opencvJ.struct.ImageStruct;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface ClearUtils  extends Library {
    int ClearMat(Pointer mat);
    int ClearImageStruct(ImageStruct imageStruct);

    int ClearEdgeDetector(Pointer edgeDetector);

    int ClearCoreImage(Pointer coreImage);
}
