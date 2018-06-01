package org.nd4j.linalg.api.ops.impl.transforms;

import org.nd4j.autodiff.samediff.SDVariable;
import org.nd4j.autodiff.samediff.SameDiff;
import org.nd4j.imports.NoOpNameFoundException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.BaseTransformOp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by susaneraly on 3/28/18.
 */
public class Reciprocal extends BaseTransformOp {

    public Reciprocal(SameDiff sameDiff, SDVariable in, boolean inPlace) {
        super(sameDiff, in, inPlace);
    }

    public Reciprocal() {
    }

    public Reciprocal(INDArray x, INDArray y, INDArray z, long n) {
        super(x, y, z, n);
    }

    public Reciprocal(INDArray x, INDArray y) {
        super(x, y, x, x.lengthLong());
    }

    public Reciprocal(INDArray x, INDArray y, INDArray z) {
        super(x, y, z, x.lengthLong());
    }

    @Override
    public int opNum() {
        return 94;
    }

    @Override
    public String opName() {
        return "Reciprocal";
    }

    @Override
    public String onnxName() {
        throw new NoOpNameFoundException("No  onnx opName found for " + opName());
    }

    @Override
    public String tensorflowName() {
        return "Reciprocal";
    }

    @Override
    public void init(INDArray x, INDArray y, INDArray z, long n) {
        super.init(x, y, z, n);
    }


    @Override
    public List<SDVariable> doDiff(List<SDVariable> i_v1) {
        // -1/(x^2)
        SDVariable g = f().pow(arg(), 2).rdiv(-1).mul(i_v1.get(0));
        return Collections.singletonList(g);
    }
}
