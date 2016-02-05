/*
 *
 *  * Copyright 2016 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */
package org.arbiter.deeplearning4j.layers;

import org.arbiter.optimize.parameter.FixedValue;
import org.arbiter.optimize.api.ParameterSpace;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.nd4j.linalg.convolution.Convolution;

import java.util.List;

public class ConvolutionLayerSpace extends FeedForwardLayerSpace<ConvolutionLayer> {

    protected ParameterSpace<Convolution.Type> convolutionType;
    protected ParameterSpace<int[]> kernelSize;
    protected ParameterSpace<int[]> stride;
    protected ParameterSpace<int[]> padding;

    private ConvolutionLayerSpace(Builder builder){
        super(builder);
        this.convolutionType = builder.convolutionType;
        this.kernelSize = builder.kernelSize;
        this.stride = builder.stride;
        this.padding = builder.padding;
    }
    
    @Override
    public List<ParameterSpace> collectLeaves(){
        List<ParameterSpace> list = super.collectLeaves();
        if(kernelSize != null) list.addAll(kernelSize.collectLeaves());
        if(stride != null) list.addAll(stride.collectLeaves());
        if(padding != null) list.addAll(padding.collectLeaves());
        return list;
    }

    @Override
    public ConvolutionLayer getValue(double[] values) {
        ConvolutionLayer.Builder b = new ConvolutionLayer.Builder();
        setLayerOptionsBuilder(b,values);
        return b.build();
    }

    protected void setLayerOptionsBuilder(ConvolutionLayer.Builder builder, double[] values){
        super.setLayerOptionsBuilder(builder,values);
        if(convolutionType != null) builder.convolutionType(convolutionType.getValue(values));
        if(kernelSize != null) builder.kernelSize(kernelSize.getValue(values));
        if(stride != null) builder.stride(stride.getValue(values));
        if(padding != null) builder.padding(padding.getValue(values));
    }

    @Override
    public String toString(){
        return toString(", ");
    }

    @Override
    public String toString(String delim){
        StringBuilder sb = new StringBuilder("ConvolutionLayerSpace(");
        if(convolutionType != null) sb.append("poolingType: ").append(convolutionType).append(delim);
        if(kernelSize != null) sb.append("kernelSize: ").append(kernelSize).append(delim);
        if(stride != null) sb.append("stride: ").append(stride).append(delim);
        if(padding != null) sb.append("padding: ").append(padding).append(delim);
        sb.append(super.toString(delim)).append(")");
        return sb.toString();
    }


    public static class Builder extends FeedForwardLayerSpace.Builder<Builder>{

        protected ParameterSpace<Convolution.Type> convolutionType;
        protected ParameterSpace<int[]> kernelSize;
        protected ParameterSpace<int[]> stride;
        protected ParameterSpace<int[]> padding;

        public Builder convolutionType(Convolution.Type convolutionType){
            return convolutionType(new FixedValue<>(convolutionType));
        }

        public Builder convolutionType(ParameterSpace<Convolution.Type> convolutionType ){
            this.convolutionType = convolutionType;
            return this;
        }

        public Builder kernelSize(int... kernelSize){
            return kernelSize(new FixedValue<>(kernelSize));
        }

        public Builder kernelSize(ParameterSpace<int[]> kernelSize){
            this.kernelSize = kernelSize;
            return this;
        }

        public Builder stride(int... stride){
            return stride(new FixedValue<>(stride));
        }

        public Builder stride(ParameterSpace<int[]> stride){
            this.stride = stride;
            return this;
        }

        public Builder padding(int... padding){
            return padding(new FixedValue<>(padding));
        }

        public Builder padding(ParameterSpace<int[]> padding){
            this.padding = padding;
            return this;
        }

        public ConvolutionLayerSpace build(){
            return new ConvolutionLayerSpace(this);
        }

    }

}
