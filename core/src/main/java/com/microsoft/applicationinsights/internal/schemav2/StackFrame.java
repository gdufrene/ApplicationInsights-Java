/*
* ApplicationInsights-Java
* Copyright (c) Microsoft Corporation
* All rights reserved.
*
* MIT License
* Permission is hereby granted, free of charge, to any person obtaining a copy of this
* software and associated documentation files (the ""Software""), to deal in the Software
* without restriction, including without limitation the rights to use, copy, modify, merge,
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit
* persons to whom the Software is furnished to do so, subject to the following conditions:
* The above copyright notice and this permission notice shall be included in all copies or
* substantial portions of the Software.
* THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
* PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
* FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
* OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
* DEALINGS IN THE SOFTWARE.
*/
/*
 * Generated from StackFrame.bond (https://github.com/Microsoft/bond)
*/
package com.microsoft.applicationinsights.internal.schemav2;

import java.io.IOException;

import com.microsoft.applicationinsights.common.Preconditions;
import com.microsoft.applicationinsights.telemetry.JsonSerializable;
import com.microsoft.applicationinsights.telemetry.JsonTelemetryDataSerializer;

/**
 * Data contract class StackFrame.
 */
public class StackFrame
    implements JsonSerializable
{
    /**
     * Backing field for property Level.
     */
    private int level;

    /**
     * Backing field for property Method.
     */
    private String method;

    /**
     * Backing field for property Assembly.
     */
    private String assembly;

    /**
     * Backing field for property FileName.
     */
    private String fileName;

    /**
     * Backing field for property Line.
     */
    private int line;

    /**
     * Initializes a new instance of the StackFrame class.
     */
    public StackFrame()
    {
        this.InitializeFields();
    }

    /**
     * Gets the Level property.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Sets the Level property.
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the Method property.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Sets the Method property.
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Gets the Assembly property.
     */
    public String getAssembly() {
        return this.assembly;
    }

    /**
     * Sets the Assembly property.
     */
    public void setAssembly(String value) {
        this.assembly = value;
    }

    /**
     * Gets the FileName property.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Sets the FileName property.
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the Line property.
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Sets the Line property.
     */
    public void setLine(int value) {
        this.line = value;
    }


    /**
     * Serializes the beginning of this object to the passed in writer.
     * @param writer The writer to serialize this object to.
     */
    @Override
    public void serialize(JsonTelemetryDataSerializer writer) throws IOException
    {
        Preconditions.checkNotNull(writer, "writer must be a non-null value");
        this.serializeContent(writer);
    }

    /**
     * Serializes the beginning of this object to the passed in writer.
     * @param writer The writer to serialize this object to.
     */
    protected void serializeContent(JsonTelemetryDataSerializer writer) throws IOException
    {
        writer.write("level", level);

        writer.writeRequired("method", method, 1024);

        writer.write("assembly", assembly, 1024);
        writer.write("fileName", fileName, 1024);
        writer.write("line", line);
    }

    /**
     * Optionally initializes fields for the current context.
     */
    protected void InitializeFields() {

    }
}
